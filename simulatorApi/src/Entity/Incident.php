<?php
namespace App\Entity;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\JoinColumn;
use Doctrine\ORM\Mapping\ManyToOne;
use Gedmo\Timestampable\Traits\Timestampable;
use Symfony\Component\Validator\Constraints as Assert;
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
     * @ORM\Column(type="integer", nullable=false)
     * @Assert\NotBlank()
     *
     */
    private $latitude;

    /**
     * @ORM\Column(type="integer", nullable=false)
     * @Assert\NotBlank()
     *
     */
    private $longitude;

    /**
     * @ORM\Column(type="integer", nullable=false)
     * @Assert\NotBlank()
     *
     */
    private $intensity;

    /**
     * @ManyToOne(targetEntity="City")
     * @JoinColumn(name="city_id", referencedColumnName="id")
     */
    private $city;

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
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * @param mixed $latitude
     *
     * @return Incident
     */
    public function setLatitude($latitude)
    {
        $this->latitude = $latitude;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * @param mixed $longitude
     *
     * @return Incident
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;

        return $this;
    }
}
