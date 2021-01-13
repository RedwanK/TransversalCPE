<?php
namespace App\Controller;

use App\Entity\Incident;
use App\Entity\Intervention;
use App\Entity\Location;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;

/**
 * Incident controller.
 * @Route("/api", name="api_")
 */
class IncidentController extends AbstractFOSRestController
{

    /**
     * Create Object.
     * @Rest\Post("/{type}/new")
     *
     * @return Response
     */
    public function postCreateIncidentAndLocationAction(Request $request, $type)
    {
        $em = $this->getDoctrine()->getManager();
        $data = json_decode($request->getContent(), true);
        $location = $em->getRepository(Location::class)->findOneBy(['latitude' => $data['x'], 'longitude' => $data['y']]);

        if(!$location) {
            $location = new Location();
            $location->setLatitude($data['x']);
            $location->setLongitude($data['y']);
        }

        $incident = $em->getRepository(Incident::class)->findOneBy(['location' => $location, 'resolved_at' => null]);
        if(!$incident) {
            if($data["v"] > 0) {
                $incident= new Incident();
                $incident->setCodeIncident("x:".$data['x'].";y:".$data['y'].";v:".$data['v']."#");
                $incident->setLocation($location);
                $incident->setIntensity(floatval($data['v']));
                $incident->setType($type);
            }
        } else {
            if(floatval($data['v']) == 0) {
                $incident->setResolvedAt(new \DateTime());
            } else {
                $incident->setIntensity(floatval($data['v']));
                $incident->setCodeIncident("x:".$data['x'].";y:".$data['y'].";v:".$data['v']."#");
                if($incident->getIntensity() <= 0 ) {
                    $incident->setResolvedAt(new \DateTime());
                }
            }
        }

        $em->persist($incident);
        $em->persist($location);
        $em->flush();
        return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
    }

    /**
     * Lists all unresolved Incidents.
     * @Rest\Get("/incidents/unresolved/list")
     *
     * @return Response
     */
    public function getUnresolvedIncidentAction()
    {
        $repository = $this->getDoctrine()->getRepository(Incident::class);
        $incidents = $repository->findBy(['resolved_at' => null]);
        return $this->handleView($this->view($incidents));
    }

    /**
     * Lists all resolved Incidents.
     * @Rest\Get("/incidents/resolved/list")
     *
     * @return Response
     */
    public function getResolvedIncidentAction()
    {
        $repository = $this->getDoctrine()->getRepository(Incident::class);
        $incidents = $repository->findAll();
        $resolvedIncidents = [];
        foreach($incidents as $incident) {
            if ($incident->getResolvedAt() !== null) {
                $resolvedIncidents[] = $incident;
            }
        }
        return $this->handleView($this->view($resolvedIncidents));
    }

    /**
     * Lists all Incidents with no interventions.
     * @Rest\Get("/incidents/no-intervention/list")
     *
     * @return Response
     */
    public function getIncidentWithNoInterventionAction()
    {
        $repository = $this->getDoctrine()->getRepository(Incident::class);
        $interventionRepository = $this->getDoctrine()->getRepository(Intervention::class);
        $incidents = [];
        foreach ($repository->findAll() as $incident) {
            $intervention = $interventionRepository->findOneBy(['incident' => $incident, "resolvedAt" => null]);
            if(!$intervention) {
                $incidents[] = $incident;
            }
        }
        return $this->handleView($this->view($incidents));
    }
}
