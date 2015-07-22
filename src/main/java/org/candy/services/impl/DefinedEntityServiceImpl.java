package org.candy.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.candy.dataaccess.DMLDataService;
import org.candy.dataaccess.DefinedEntityRepository;
import org.candy.model.DefinedEntity;
import org.candy.model.DefinedEntityField;
import org.candy.services.DefinedEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by jpedro on 18-06-2015.
 */
@Service
public class DefinedEntityServiceImpl implements DefinedEntityService {
    private static final Logger log = LoggerFactory.getLogger(DefinedEntityServiceImpl.class);

    private final DefinedEntityRepository definedEntityRepository;
    private final DMLDataService dmlDataService;
    private final Gson gson;
    @Autowired
    public DefinedEntityServiceImpl(DefinedEntityRepository definedEntityRepository, DMLDataService dmlDataService) {
        this.definedEntityRepository = definedEntityRepository;
        this.dmlDataService = dmlDataService;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public String getAllEntities(String entityName){
        List<DefinedEntity> byFriendlyName = definedEntityRepository.findByFriendlyName(entityName);

        if(byFriendlyName==null || byFriendlyName.size()==0) throw new RuntimeException("Entity with name "+entityName+" does not exists!");
        if(byFriendlyName.size()>1) throw new RuntimeException("Entity with name "+entityName+" exists more than once!");

        List<Map<String, String>> all = dmlDataService.getAll(byFriendlyName.get(0));

        return gson.toJson(all);
    }

    @Override
    public void saveEntity(String entityName, String json) {
        List<DefinedEntity> byFriendlyName = definedEntityRepository.findByFriendlyName(entityName);

        if(byFriendlyName==null || byFriendlyName.size()==0) throw new RuntimeException("Entity with name "+entityName+" does not exists!");
        if(byFriendlyName.size()>1) throw new RuntimeException("Entity with name "+entityName+" exists more than once!");

        Map<String,String> fieldMap = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>(){}.getType());
        DefinedEntity definedEntity = byFriendlyName.get(0);
        Map<DefinedEntityField, String> typedFieldMap = new HashMap<>();

        for (String friendlyFieldName : fieldMap.keySet()) {
            Optional<DefinedEntityField> definedEntityField = definedEntity.getFields().stream().filter(o -> o.getFriendlyName().equals(friendlyFieldName)).findFirst();
            typedFieldMap.put(definedEntityField.get(), fieldMap.get(definedEntityField.get().getFriendlyName()));
        }

        dmlDataService.addEntityRow(definedEntity, typedFieldMap);

        log.info("Work in progress!!!"+json);
    }

    @Override
    public String getEntity(String entityName, String id) {
        List<DefinedEntity> byFriendlyName = definedEntityRepository.findByFriendlyName(entityName);

        if(byFriendlyName==null || byFriendlyName.size()==0) throw new RuntimeException("Entity with name "+entityName+" does not exists!");
        if(byFriendlyName.size()>1) throw new RuntimeException("Entity with name "+entityName+" exists more than once!");

        Map<String, String> entity = dmlDataService.findById(byFriendlyName.get(0), id);

        return gson.toJson(entity);
    }

    @Override
    public void removeEntity(String entityName, String id) {
        List<DefinedEntity> byFriendlyName = definedEntityRepository.findByFriendlyName(entityName);

        if(byFriendlyName==null || byFriendlyName.size()==0) throw new RuntimeException("Entity with name "+entityName+" does not exists!");
        if(byFriendlyName.size()>1) throw new RuntimeException("Entity with name "+entityName+" exists more than once!");

        dmlDataService.deleteById(byFriendlyName.get(0), id);

    }

    @Override
    public void updateEntity(String entityName, String id, String jsonBody) {
        List<DefinedEntity> byFriendlyName = definedEntityRepository.findByFriendlyName(entityName);

        if(byFriendlyName==null || byFriendlyName.size()==0) throw new RuntimeException("Entity with name "+entityName+" does not exists!");
        if(byFriendlyName.size()>1) throw new RuntimeException("Entity with name "+entityName+" exists more than once!");

        boolean exists = dmlDataService.exists(byFriendlyName.get(0), id);
        if(!exists) throw new RuntimeException("Entity with name "+entityName+" and Row with id"+id+" does not exists!");

        Map<String,String> fieldMap = new Gson().fromJson(jsonBody, new TypeToken<HashMap<String, String>>(){}.getType());
        DefinedEntity definedEntity = byFriendlyName.get(0);
        Map<DefinedEntityField, String> typedFieldMap = new HashMap<>();

        for (String friendlyFieldName : fieldMap.keySet()) {
            Optional<DefinedEntityField> definedEntityField = definedEntity.getFields().stream().filter(o -> o.getFriendlyName().equals(friendlyFieldName)).findFirst();
            typedFieldMap.put(definedEntityField.get(), fieldMap.get(definedEntityField.get().getFriendlyName()));
        }

        dmlDataService.updateEntityRow(definedEntity, id, typedFieldMap);

        log.info("Work in progress!!!"+jsonBody);
    }
}
