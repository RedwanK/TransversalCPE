<?php
namespace App\Controller;
use App\Entity\City;
use App\Form\CityType;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;

/**
 * City controller.
 * @Route("/api", name="api_")
 */
class CityController extends AbstractFOSRestController
{
    /**
     * Lists all Cities.
     * @Rest\Get("/cities/list")
     *
     * @return Response
     */
    public function getCityAction()
    {
        $repository = $this->getDoctrine()->getRepository(City::class);
        $cities = $repository->findall();
        return $this->handleView($this->view($cities));
    }

    /**
     * Create City.
     * @Rest\Post("/cities/create")
     *
     * @return Response
     */
    public function postCityAction(Request $request)
    {
        $city = new City();
        $form = $this->createForm(CityType::class, $city);
        $data = json_decode($request->getContent(), true);
        $form->submit($data);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($city);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view($form->getErrors()));
    }
}
