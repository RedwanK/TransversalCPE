<?php
namespace App\Controller;
use App\Entity\City;
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

        dump($interventions);die;
        //return $this->handleView($this->view($cities));
    }
}
