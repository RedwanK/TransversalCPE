<?php
namespace App\Controller;
use App\Entity\City;
use App\Entity\Intervention;
use App\Entity\Location;
use App\Form\CityType;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use GuzzleHttp\Client;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;
use Symfony\Component\Validator\Constraints\Date;

/**
 * City controller.
 * @Route("/api", name="api_")
 */
class EmergencyController extends AbstractFOSRestController
{
    /**
     * Lists all Cities.
     * @Rest\Get("/emergency/update")
     *
     * @return Response
     */
    public function getInterventionsAction()
    {
        if(($result = $this->getUnresolvedInterventions()) !== true) return $result;
        if(($result = $this->getResolvedIncidentsAction()) !== true) return $result;

        $doctrine = $this->getDoctrine();
        $doctrine->getManager()->flush();
        return $this->handleView($this->view(['status' => "ok"], Response::HTTP_CREATED));
    }

    public function getUnresolvedInterventions() {
        $client = new Client();
        $response = $client->get('http://emergency-api.local/api/intervention/unresolved/list');
        $body = $response->getBody();
        $json = "";
        while (!$body->eof()) {
            $json .= $body->read(1024);
        }

        $interventions = json_decode($json);
        $doctrine = $this->getDoctrine();
        $locationRepository = $doctrine->getRepository(Location::class);
        foreach($interventions as $intervention) {
            $location = $locationRepository->findOneBy(['latitude' => $intervention->incident->location->latitude, 'longitude' =>  $intervention->incident->location->longitude]);
            $incident = null;
            if($location) $incident = $doctrine->getRepository(Incident::class)->findOneBy(["location" => $location, "resolved_at" => null]);
            if (!$incident) {
                return $this->handleView($this->view(["error" => "unable to find corresponding incident"], Response::HTTP_BAD_REQUEST));
            }
            if($doctrine->getRepository(Intervention::class)->findBy(['incident' => $incident])) {
                continue;
            }
            $interventionObj = new Intervention();
            $interventionObj->setIncident($incident);
            $interventionObj->setCoefficient($intervention->coefficient);
            $interventionObj->setNumberAgents($intervention->number_agents);
            $interventionObj->setNumberVehicles($intervention->number_vehicles);

            $doctrine->getManager()->persist($interventionObj);
        }

        return true;
    }
    /**
     * Update datas with emergency api.*
     */
    public function getResolvedIncidentsAction()
    {
        $client = new Client();
        $response = $client->get('http://emergency-api.local/api/incidents/resolved/list');
        $body = $response->getBody();
        $json = "";
        while (!$body->eof()) {
            $json .= $body->read(1024);
        }

        $incidents = json_decode($json);
        $doctrine = $this->getDoctrine();
        $locationRepository = $doctrine->getRepository(Location::class);
        foreach($incidents as $incident) {
            $location = $locationRepository->findOneBy(['latitude' => $incident->location->latitude, 'longitude' =>  $incident->location->longitude]);
            $icd = $doctrine->getRepository(Incident::class)->findOneBy(["location" => $location, "resolved_at" => null]);
            if (!$icd) {
                continue;
                //return $this->handleView($this->view(["error" => "unable to find corresponding incident"], Response::HTTP_BAD_REQUEST));
            }

            if ($icd->getResolvedAt() == null) {
                $icd->setResolvedAt(new \DateTime());
                $icd->setIntensity($incident->intensity);
                $icd->setcodeIncident($incident->code_incident);

                $doctrine->getManager()->persist($icd);
            }
        }
        return true;
    }
}
