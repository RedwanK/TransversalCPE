<?php

namespace App\Form;

use App\Entity\Agent;
use App\Entity\City;
use App\Entity\Job;
use App\Entity\Location;
use App\Entity\Team;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class AgentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('firstname', TextType::class)
                ->add('lastname', TextType::class)
                ->add('phoneNumber', TextType::class)
                ->add('street', TextType::class)
                ->add('zipcode', TextType::class)
                ->add('job', EntityType::class, [
                    'class' => Job::class,
                ])
                ->add('team', EntityType::class, [
                    'class' => Team::class,
                ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class'      => Agent::class,
            'csrf_protection' => false,
        ]);
    }
}
