<?php
namespace App\Entity;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping\OneToOne;

/**
 * @ORM\Entity
 * @ORM\Table(name="intervention")
 */
class Intervention {
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="float", nullable=false)
     *
     */
    private $coefficient;

    /**
     * @ORM\Column(type="integer", nullable=false)
     */
    private $numberVehicles;

    /**
     * @ORM\Column(type="integer", nullable=false)
     */
    private $numberAgents;

    /**
     * @ORM\Column(type="datetime", nullable=true)
     */
    private $resolvedAt;

    /**
     * @ORM\ManyToOne (targetEntity="Team")
     * @ORM\JoinColumn(name="team_id", referencedColumnName="id")
     */
    private $team;

    /**
     * @OneToOne(targetEntity="Incident")
     * @ORM\JoinColumn(name="incident_id", referencedColumnName="id")
     */
    private $incident;

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getCoefficient()
    {
        return $this->coefficient;
    }

    /**
     * @param mixed $coefficient
     * @return job
     */
    public function setCoefficient($coefficient)
    {
        $this->coefficient = $coefficient;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getNumberVehicles()
    {
        return $this->numberVehicles;
    }

    /**
     * @param mixed $numberVehicles
     * @return Intervention
     */
    public function setNumberVehicles($numberVehicles)
    {
        $this->numberVehicles = $numberVehicles;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getNumberAgents()
    {
        return $this->numberAgents;
    }

    /**
     * @param mixed $numberAgents
     * @return Intervention
     */
    public function setNumberAgents($numberAgents)
    {
        $this->numberAgents = $numberAgents;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getResolvedAt()
    {
        return $this->resolvedAt;
    }

    /**
     * @param mixed $resolvedAt
     * @return Intervention
     */
    public function setResolvedAt($resolvedAt)
    {
        $this->resolvedAt = $resolvedAt;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getTeam()
    {
        return $this->team;
    }

    /**
     * @param mixed $team
     * @return Intervention
     */
    public function setTeam($team)
    {
        $this->team = $team;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getIncident()
    {
        return $this->incident;
    }

    /**
     * @param mixed $incident
     * @return Intervention
     */
    public function setIncident($incident)
    {
        $this->incident = $incident;
        return $this;
    }
}
