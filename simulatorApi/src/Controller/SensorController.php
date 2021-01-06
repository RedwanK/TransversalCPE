<?php
namespace App\Controller;
use App\Entity\City;
use App\Entity\Sensor;
use App\Form\CityType;
use App\Form\SensorType;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;

/**
 * Sensor controller.
 * @Route("/api", name="api_")
 */
class SensorController extends AbstractFOSRestController
{
    /**
     * Lists all Sensors.
     * @Rest\Get("/sensors/list")
     *
     * @return Response
     */
    public function getSensorAction()
    {
        $repository = $this->getDoctrine()->getRepository(Sensor::class);
        $sensors = $repository->findall();
        return $this->handleView($this->view($sensors));
    }

    /**
     * Create Sensor.
     * @Rest\Post("/sensors/create")
     *
     * @return Response
     */
    public function postSensorAction(Request $request)
    {
        $sensor = new Sensor();
        $form = $this->createForm(SensorType::class, $sensor);
        $data = json_decode($request->getContent(), true);
        $form->submit($data);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($sensor);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view($form->getErrors()));
    }
}
