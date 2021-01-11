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
 * Intervention controller.
 * @Route("/api", name="api_")
 */
class InterventionController extends AbstractFOSRestController
{
    /**
     * Lists all unresolved Incidents.
     * @Rest\Get("/intervention/unresolved/list")
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
     * Lists all unresolved Incidents.
     * @Rest\Post("/intervention/resolve/{id}")
     *
     * @return Response
     */
    public function getResolveInterventionAction($id)
    {
        $repository = $this->getDoctrine()->getRepository(Intervention::class);
        $intervention = $repository->findOneBy(['id' => $id]);
        if(!$intervention) {
            return $this->handleView($this->view(['error' => 'unknown intervention']), Response::HTTP_BAD_REQUEST);
        }
        $intervention->setResolvedAt(new \DateTime());
        $this->getDoctrine()->getManager()->persist($intervention);
        $this->getDoctrine()->getManager()->flush($intervention);
        return $this->handleView($this->view(['status' => 'ok']), Response::HTTP_CREATED);
    }
}
