<?php

namespace App\Form;

use App\Entity\Agent;
use App\Entity\City;
use App\Entity\Team;
use App\Entity\Vehicle;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TeamType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('name', TextType::class)
                ->add('agents', EntityType::class, [
                    'class' => Agent::class,
                    'multiple' => true,
                ])
                /*->add('vehicles', EntityType::class, [
                    'class' => Vehicle::class,
                    'multiple' => true
                ])*/
                /*->add('agents', CollectionType::class, [
                    'entry_type' => AgentType::class,
                    'entry_options' => ['label' => false],
                    'allow_add' => true,
                ])*/
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class'      => Team::class,
            'csrf_protection' => false,
        ]);
    }
}
