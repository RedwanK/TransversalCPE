<?php

namespace App\Form;

use App\Entity\City;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\Incident;

class IncidentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('latitude', NumberType::class, [
                    'scale' => 6
                ])
                ->add('longitude', NumberType::class, [
                    'scale' => 6
                ])
                ->add('intensity', NumberType::class, [
                    'scale' => 6
                ])
                ->add('city', EntityType::class, [
                    'class' => City::class,
                ])
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
