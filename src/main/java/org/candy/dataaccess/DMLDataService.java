package org.candy.dataaccess;

import org.candy.model.DefinedEntity;
import org.candy.model.DefinedEntityField;

import java.util.List;
import java.util.Map;

/**
 * Created by jpedro on 26-06-2015.
 */
public interface DMLDataService {
    List<Map<String, String>> getAll(DefinedEntity definedEntity);

    void addEntityRow(DefinedEntity definedEntity, Map<DefinedEntityField, String> typedFieldMap);

    Map<String,String> findById(DefinedEntity definedEntity, String id);

    void deleteById(DefinedEntity definedEntity, String id);

    boolean exists(DefinedEntity definedEntity, String id);

    void updateEntityRow(DefinedEntity definedEntity, String id, Map<DefinedEntityField, String> typedFieldMap);
}
