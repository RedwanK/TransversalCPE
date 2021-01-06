<?php
namespace App\Entity;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * @ORM\Entity
 * @ORM\Table(name="team")
 */
class Team {
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="text", length=100, nullable=false)
     * @Assert\NotBlank()
     *
     */
    private $name;

    /**
     * @ORM\OneToMany (targetEntity="Agent", mappedBy="team")
     */
    private $agents;

    /**
     * @ORM\OneToMany (targetEntity="Vehicle", mappedBy="team")
     */
    private $vehicles;

    public function __construct()
    {
        $this->agents = new ArrayCollection();
    }

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
    public function getName()
    {
        return $this->name;
    }

    /**
     * @param mixed $name
     * @return job
     */
    public function setName($name)
    {
        $this->name = $name;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getAgents()
    {
        return $this->agents;
    }

    /**
     * @param mixed $agents
     * @return Team
     */
    public function setAgents($agents)
    {
        $this->agents = $agents;
        return $this;
    }

    public function addAgent(Agent $agent) {
        if (!$this->agents->contains($agent)) {
            $this->agents->add($agent);
        }
    }

    public function removeAgent(Agent $agent)
    {
        $this->agents->removeElement($agent);
    }

    /**
     * @return mixed
     */
    public function getVehicles()
    {
        return $this->vehicles;
    }

    /**
     * @param mixed $vehicles
     * @return Team
     */
    public function setVehicles($vehicles)
    {
        $this->vehicles = $vehicles;
        return $this;
    }

    public function addVehicle(Vehicle $vehicle) {
        if (!$this->vehicles->contains($vehicle)) {
            $this->vehicles->add($vehicle);
        }
    }

    public function removeVehicle(Vehicle $vehicle)
    {
        $this->vehicles->removeElement($vehicle);
    }
}
