<?php
namespace App\Controller;
use App\Entity\City;
use App\Entity\Intervention;
use App\Form\CityType;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use GuzzleHttp\Client;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;

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
        $client = new Client();
        $response = $client->get('http://emergency-api.local/api/intervention/unresolved/list');
        $body = $response->getBody();
        $json = "";
        while (!$body->eof()) {
            $json .= $body->read(1024);
        }

        $interventions = json_decode($json);
        $doctrine = $this->getDoctrine();
        foreach($interventions as $intervention) {
            $incident = $doctrine->getRepository(Incident::class)->findOneBy(["codeIncident" => $intervention->incident->code_incident, "resolved_at" => null]);
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
        $doctrine->getManager()->flush();
        return $this->handleView($this->view(['status' => "ok"], Response::HTTP_CREATED));
    }
}
