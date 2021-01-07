<?php
namespace App\Controller;

use FOS\RestBundle\Controller\AbstractFOSRestController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Routing\Annotation\Route;

/**
 * Object controller.
 * @Route("/api", name="api_")
 */
class ObjectController extends AbstractFOSRestController
{
    /**
     * Lists all objects.
     * @Rest\Get("/{object}/list")
     *
     * @return Response
     */
    public function getObjectsAction($object)
    {
        $object = preg_replace_callback("/(?:^|_)([a-z])/", function($matches) {
            return strtoupper($matches[1]);
        }, $object);
        $classname = "App\Entity\\".ucfirst($object);
        $repository = $this->getDoctrine()->getRepository($classname);
        $object = $repository->findall();
        return $this->handleView($this->view($object));
    }

    /**
     * Create Object.
     * @Rest\Post("/{object}/create")
     *
     * @return Response
     */
    public function postObjectAction(Request $request, $object)
    {
        $objectStr = preg_replace_callback("/(?:^|_)([a-z])/", function($matches) {
            return strtoupper($matches[1]);
        }, $object);
        $classname = "App\Entity\\".ucfirst($objectStr);
        $object = new $classname();
        $classtype = "App\Form\\".ucfirst($objectStr)."Type";
        $form = $this->createForm($classtype, $object);
        $data = json_decode($request->getContent(), true);
        $form->submit($data);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($object);
            $em->flush();
            return $this->handleView($this->view(['status' => 'ok'], Response::HTTP_CREATED));
        }
        return $this->handleView($this->view($form->getErrors()));
    }
}
