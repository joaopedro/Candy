package org.candy.services;

import java.util.Map;

/**
 * Created by jpedro on 18-06-2015.
 */
public interface DefinedEntityService {
    String getAllEntities(String entityName);

    void saveEntity(String entityName, String json);

    String getEntity(String entity, String id);

    void removeEntity(String entityName, String json);

    void updateEntity(String entity, String id, String jsonBody);
}
