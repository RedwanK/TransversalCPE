<?php

namespace App\Form;

use App\Entity\Agent;
use App\Entity\CategoryVehicle;
use App\Entity\City;
use App\Entity\Job;
use App\Entity\Location;
use App\Entity\Team;
use App\Entity\Vehicle;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class VehicleType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('registrationNumber', TextType::class)
                ->add('fuel', TextType::class)
                ->add('categoryVehicle', EntityType::class, [
                    'class' => CategoryVehicle::class,
                ])
                ->add('team', EntityType::class, [
                    'class' => Team::class,
                ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class'      => Vehicle::class,
            'csrf_protection' => false,
        ]);
    }
}
