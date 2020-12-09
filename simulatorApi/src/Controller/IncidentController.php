<?php
namespace App\Controller;
use App\Entity\City;
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
     * Lists all Movies.
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
}
