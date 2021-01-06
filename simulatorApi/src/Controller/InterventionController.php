<?php
namespace App\Controller;
use App\Entity\City;
use App\Entity\Intervention;
use App\Form\CityType;
use App\Form\InterventionType;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Incident;

/**
 * Intervention controller.
 * @Route("/api", name="api_")
 */
class InterventionController extends AbstractFOSRestController
{
    /**
     * Lists all interventions.
     * @Rest\Get("/interventions/list")
     *
     * @return Response
     */
    public function getInterventionAction()
    {
        $repository = $this->getDoctrine()->getRepository(Intervention::class);
        $interventions = $repository->findall();
        return $this->handleView($this->view($interventions));
    }

    /**
     * Lists all unresolved interventions.
     * @Rest\Get("/interventions/unresolved/list")
     *
     * @return Response
     */
    public function getUnresolvedInterventionAction()
    {
        $repository = $this->getDoctrine()->getRepository(Intervention::class);
        $interventions = $repository->findBy(['resolvedAt' => null]);
        return $this->handleView($this->view($interventions));
    }

    /**
     * Create intervention.
     * @Rest\Post("/interventions/create")
     *
     * @return Response
     */
    public function postInterventionAction(Request $request)
    {
        $intervention = new Intervention();
        $form = $this->createForm(InterventionType::class, $intervention);
        $data = json_decode($request->getContent(), true);
        $form->submit($data);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($intervention);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view($form->getErrors()));
    }
}
