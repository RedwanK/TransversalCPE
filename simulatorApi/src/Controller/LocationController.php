<?php
namespace App\Controller;
use App\Entity\Location;
use App\Form\LocationType;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;

/**
 * Location controller.
 * @Route("/api", name="api_")
 */
class LocationController extends AbstractFOSRestController
{
    /**
     * Lists all Locations.
     * @Rest\Get("/locations/list")
     *
     * @return Response
     */
    public function getLocationAction()
    {
        $repository = $this->getDoctrine()->getRepository(Location::class);
        $locations = $repository->findall();
        return $this->handleView($this->view($locations));
    }

    /**
     * Create Location.
     * @Rest\Post("/locations/create")
     *
     * @return Response
     */
    public function postLocationAction(Request $request)
    {
        $location = new Location();
        $form = $this->createForm(LocationType::class, $location);
        $data = json_decode($request->getContent(), true);
        $form->submit($data);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($location);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view($form->getErrors()));
    }
}
