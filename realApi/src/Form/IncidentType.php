<?php

namespace App\Form;

use App\Entity\City;
use App\Entity\Location;
use App\Entity\Sensor;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\Incident;

class IncidentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('type', TextType::class)
                ->add('codeIncident', TextType::class)
                ->add('intensity', NumberType::class, [
                    'scale' => 6
                ])
                ->add('location', EntityType::class, [
                    'class' => Location::class,
                ])
                ->add('resolved_at', DateTimeType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class'      => Incident::class,
            'csrf_protection' => false,
        ]);
    }
}
