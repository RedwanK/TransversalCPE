<?php
namespace App\Controller;

use App\Entity\Intervention;
use App\Entity\Team;
use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;

/**
 * Object controller.
 * @Route("/api", name="api_")
 */
class TeamController extends AbstractFOSRestController
{
    /**
     * Lists all objects.
     * @Rest\Get("/team/unresolved/list")
     *
     * @return Response
     */
    public function getTeamAction()
    {
        $repository = $this->getDoctrine()->getRepository(Team::class);
        $interventionsRepository = $this->getDoctrine()->getRepository(Intervention::class);
        $teams = $repository->findAll();
        $freeTeams = [];
        foreach($teams as $team) {
            $intervention = $interventionsRepository->findBy(['team' => $team, 'resolvedAt' => null]);
            if(!$intervention || $intervention == []) {
                $freeTeams[] = $team;
            }
        }
        return $this->handleView($this->view($freeTeams));
    }
}
