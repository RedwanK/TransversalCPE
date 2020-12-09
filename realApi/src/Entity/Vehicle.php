<?php
namespace App\Entity;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * @ORM\Entity
 * @ORM\Table(name="movie")
 */
class Vehicle {
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=100)
     * @Assert\NotBlank()
     *
     */
    private $type;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotBlank()
     */
    private $fuel;
    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }
}
