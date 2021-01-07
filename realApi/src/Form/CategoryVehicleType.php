<?php

namespace App\Form;

use App\Entity\Agent;
use App\Entity\CategoryVehicle;
use App\Entity\City;
use App\Entity\Job;
use App\Entity\Location;
use App\Entity\Team;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CategoryVehicleType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('name', TextType::class)
            ->add('capacity', IntegerType::class)
            ->add('coefficient', NumberType::class, [
                'scale' => 6
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class'      => CategoryVehicle::class,
            'csrf_protection' => false,
        ]);
    }
}
