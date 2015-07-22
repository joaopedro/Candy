package org.candy.controller;

import org.apache.commons.io.IOUtils;
import org.candy.services.DefinedEntityService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by jpedro on 16-06-2015.
 */
@RestController
public class MainController {
    @PersistenceContext
    EntityManager entityManager;

    protected Session getCurrentSession()  {
        return entityManager.unwrap(Session.class);
    }
    @Autowired
    private DefinedEntityService definedEntityService;

    @RequestMapping(value = "/api/{entity}")
    public @ResponseBody String getEntities(@PathVariable("entity") String entity){
        return definedEntityService.getAllEntities(entity);
    }

    @RequestMapping(value = "/api/{entity}/{id}")
    public @ResponseBody String getEntity(@PathVariable("entity") String entity,
                                            @PathVariable("id") String id){
        return definedEntityService.getEntity(entity, id);
    }

    @RequestMapping(value = "/api/{entity}/{id}", method = RequestMethod.DELETE)
    public @ResponseBody JsonResponse removeEntity(@PathVariable("entity") String entity,
                                            @PathVariable("id") String id){
        definedEntityService.removeEntity(entity, id);
        return new JsonResponse("OK","");
    }

    @RequestMapping(value = "/api/{entity}", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public @ResponseBody JsonResponse saveEntities(@PathVariable("entity") String entity, HttpServletRequest request) throws IOException {
        String jsonBody = IOUtils.toString(request.getInputStream());
        definedEntityService.saveEntity(entity, jsonBody);
        return new JsonResponse("OK","");
    }

    @RequestMapping(value = "/api/{entity}/{id}", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public @ResponseBody JsonResponse updateEntity(@PathVariable("entity") String entity,
               @PathVariable("id") String id, HttpServletRequest request) throws IOException {
        String jsonBody = IOUtils.toString(request.getInputStream());
        definedEntityService.updateEntity(entity, id, jsonBody);
        return new JsonResponse("OK","");
    }

}
