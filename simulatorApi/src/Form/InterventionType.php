<?php

namespace App\Form;

use App\Entity\City;
use App\Entity\Incident;
use App\Entity\Intervention;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class InterventionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('coefficient', NumberType::class, [
                    'scale' => 6
                ])
                ->add('numberVehicles', IntegerType::class)
                ->add('numberAgents', IntegerType::class)
                ->add('resolvedAt', DateTimeType::class)
                ->add('incident', EntityType::class, [
                    'class' => Incident::class
                ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class'      => Intervention::class,
            'csrf_protection' => false,
        ]);
    }
}
