<?php
namespace App\Entity;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\JoinColumn;
use Doctrine\ORM\Mapping\ManyToOne;
use Gedmo\Timestampable\Traits\Timestampable;
/**
 * @ORM\Entity
 * @ORM\Table(name="incident")
 */
class Incident {

    use Timestampable;

    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="datetime", nullable=true)
     *
     */
    private $resolved_at;

    /**
     * @ORM\Column(type="text", nullable=true)
     *
     */
    private $type;

    /**
     * @ORM\Column(type="float", nullable=false)
     *
     */
    private $intensity;

    /**
     * @ORM\Column(type="text", nullable=false)
     *
     */
    private $codeIncident;

    /**
     * @ManyToOne (targetEntity="Location")
     * @JoinColumn(name="location_id", referencedColumnName="id")
     */
    private $location;

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
    public function getIntensity()
    {
        return $this->intensity;
    }

    /**
     * @param mixed $intensity
     *
     * @return Incident
     */
    public function setIntensity($intensity)
    {
        $this->intensity = $intensity;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getResolvedAt()
    {
        return $this->resolved_at;
    }

    /**
     * @param mixed $resolved_at
     * @return Incident
     */
    public function setResolvedAt($resolved_at)
    {
        $this->resolved_at = $resolved_at;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getType()
    {
        return $this->type;
    }

    /**
     * @param mixed $type
     * @return Incident
     */
    public function setType($type)
    {
        $this->type = $type;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getCodeIncident()
    {
        return $this->codeIncident;
    }

    /**
     * @param mixed $codeIncident
     * @return Incident
     */
    public function setCodeIncident($codeIncident)
    {
        $this->codeIncident = $codeIncident;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getLocation()
    {
        return $this->location;
    }

    /**
     * @param mixed $location
     * @return Incident
     */
    public function setLocation($location)
    {
        $this->location = $location;
        return $this;
    }

}
