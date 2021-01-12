<?php
namespace App\Controller;
use App\Entity\City;
use App\Entity\Intervention;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;
use App\Form\IncidentType;
/**
 * Incident controller.
 * @Route("/api", name="api_")
 */
class IncidentController extends AbstractFOSRestController
{
    /**
     * Lists all Incidents.
     * @Rest\Get("/incidents/list")
     *
     * @return Response
     */
    public function getIncidentAction()
    {
        $repository = $this->getDoctrine()->getRepository(Incident::class);
        $incidents = $repository->findall();
        return $this->handleView($this->view($incidents));
    }

    /**
     * Lists all Incidents with interventions.
     * @Rest\Get("/incidents/with-interventions/list")
     *
     * @return Response
     */
    public function getIncidentWithInterventionsAction()
    {
        $repository = $this->getDoctrine()->getRepository(Intervention::class);
        $incidents = [];
        foreach($repository->findBy(['resolvedAt' => null]) as $intervention) {
            if($intervention->getIncident()->getResolvedAt() === null) {
                $incidents[] = $intervention->getIncident();
            }
        }

        return $this->handleView($this->view($incidents));
    }

    /**
     * Lists all Incidents with no interventions.
     * @Rest\Get("/incidents/no-interventions/list")
     *
     * @return Response
     */
    public function getIncidentNoInterventionsAction()
    {
        $repository = $this->getDoctrine()->getRepository(Incident::class);
        $interventionRepository = $this->getDoctrine()->getRepository(Intervention::class);
        $incidents = [];
        foreach($repository->findBy(['resolved_at' => null]) as $incident) {
            if(!$interventionRepository->findOneBy(['incident' => $incident, 'resolvedAt' => null])){
                $incidents[] = $incident;
            }
        }

        return $this->handleView($this->view($incidents));
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
     * Create Incident.
     * @Rest\Post("/incidents/create")
     *
     * @return Response
     */
    public function postIncidentAction(Request $request)
    {
        $incident = new Incident();
        $form = $this->createForm(IncidentType::class, $incident);
        $data = json_decode($request->getContent(), true);
        $form->submit($data);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($incident);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view($form->getErrors()));
    }

    /**
     * Update Incident intensity.
     * @Rest\Post("/incidents/{id}/update")
     *
     * @return Response
     */
    public function postIncidentUpdateAction(Request $request, $id)
    {
        $incident = $this->getDoctrine()->getRepository(Incident::class)->findOneBy(['id' => $id]);
        if(!$incident) {
            return $this->handleView($this->view(['error' => 'unknown incident'], Response::HTTP_BAD_REQUEST));
        }
        $form = $this->createForm(IncidentType::class, $incident);
        $data = json_decode($request->getContent(), true);
        if (isset($data['intensity'])) {
            $incident->setIntensity($data['intensity']);
            $em = $this->getDoctrine()->getManager();
            $em->persist($incident);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view(['error' => "intensity not set in json body"]));
    }

    /**
     * Resolve an incident.
     * @Rest\Get("/incidents/resolve/{id}")
     *
     * @return Response
     */
    public function getResolveIncidentAction(Request $request, $id)
    {
        $repository = $this->getDoctrine()->getRepository(Incident::class);
        $incident = $repository->findOneBy(['id' => $id]);
        if(!$incident) {
            return $this->handleView($this->view(['status' => 'Unknown incident'], Response::HTTP_BAD_REQUEST));
        }
        $incident->setResolvedAt(new \DateTime());
        $em = $this->getDoctrine()->getManager();
        $em->persist($incident);
        $em->flush();
        return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
    }
}
