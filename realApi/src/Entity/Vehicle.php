<?php
namespace App\Entity;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * @ORM\Entity
 * @ORM\Table(name="vehicle")
 */
class Vehicle {
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
    private $registrationNumber;

    /**
     * @ORM\Column(type="text", nullable=true)
     * @Assert\NotBlank()
     */
    private $fuel;

    /**
     * @ORM\ManyToOne (targetEntity="CategoryVehicle")
     * @ORM\JoinColumn(name="category_vehicle_id", referencedColumnName="id")
     */
    private $categoryVehicle;

    /**
     * @ORM\ManyToOne (targetEntity="Team", inversedBy="vehicles")
     * @ORM\JoinColumn(name="team_id", referencedColumnName="id")
     */
    private $team;

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
    public function getRegistrationNumber()
    {
        return $this->registrationNumber;
    }

    /**
     * @param mixed $registrationNumber
     * @return Vehicle
     */
    public function setRegistrationNumber($registrationNumber)
    {
        $this->registrationNumber = $registrationNumber;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getFuel()
    {
        return $this->fuel;
    }

    /**
     * @param mixed $fuel
     * @return Vehicle
     */
    public function setFuel($fuel)
    {
        $this->fuel = $fuel;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getCategoryVehicle()
    {
        return $this->categoryVehicle;
    }

    /**
     * @param mixed $categoryVehicle
     * @return Vehicle
     */
    public function setCategoryVehicle($categoryVehicle)
    {
        $this->categoryVehicle = $categoryVehicle;
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
     * @return Vehicle
     */
    public function setTeam($team)
    {
        $this->team = $team;
        return $this;
    }
}
