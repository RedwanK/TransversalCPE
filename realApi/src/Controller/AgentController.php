<?php
namespace App\Controller;

use App\Entity\Agent;
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
class AgentController extends AbstractFOSRestController
{
    /**
     * Lists all objects.
     * @Rest\Get("/agent/{agentId}/update/team/{teamId}")
     *
     * @return Response
     */
    public function getUpdateAgentsAction($agentId, $teamId)
    {
        $repository = $this->getDoctrine()->getRepository(Team::class);
        $team = $repository->findOneBy(['id' => $teamId]);
        if (!$team) {
            return $this->handleView($this->view(['error' => "unkown team"]));
        }
        $agentRepository = $this->getDoctrine()->getRepository(Agent::class);
        $agent = $agentRepository->findOneBy(["id" => $agentId]);
        if(!$agent) {
            return $this->handleView($this->view(["error" => "unknown agent"]));
        }

        $agent->setTeam($team);
        $this->getDoctrine()->getManager()->persist($agent);
        $this->getDoctrine()->getManager()->flush();

        return $this->handleView($this->view(['status' => "ok"]));
    }
}
