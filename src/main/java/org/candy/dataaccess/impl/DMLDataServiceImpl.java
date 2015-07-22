package org.candy.dataaccess.impl;

import org.candy.dataaccess.DMLDataService;
import org.candy.model.DefinedEntity;
import org.candy.model.DefinedEntityField;
import org.candy.model.DefinedEntityFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpedro on 26-06-2015.
 */
@Repository
public class DMLDataServiceImpl implements DMLDataService {
    private static final Logger log = LoggerFactory.getLogger(DMLDataServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, String>> getAll(DefinedEntity definedEntity) {
        log.info("Querying Table...");

        StringBuilder sb = new StringBuilder("select ");

        int index = 0;
        for (DefinedEntityField definedEntityField : definedEntity.getFields()) {
            sb.append(definedEntityField.getPhysicalName());
            if(index+1<definedEntity.getFields().size()){
                sb.append(",");
            }
            index++;
        }

        sb.append(" from ").append(definedEntity.getPhysicalName())
            .append(" where ").append(getActiveCondition());

        log.info(sb.toString());

        return jdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
            Map<String, String> result = new HashMap<>();
            for (DefinedEntityField definedEntityField : definedEntity.getFields()) {
                result.put(definedEntityField.getFriendlyName(), rs.getString(definedEntityField.getPhysicalName()));
            }

            return result;
        });

    }

    @Override
    public void addEntityRow(DefinedEntity definedEntity, Map<DefinedEntityField, String> typedFieldMap) {
        log.info("Creating record...");

        StringBuilder fieldsSb = new StringBuilder("(");
        StringBuilder valuesSb = new StringBuilder("(");

        for (DefinedEntityField definedEntityField : typedFieldMap.keySet()) {
            if(!definedEntityField.isFieldIdentity()){
                fieldsSb.append(definedEntityField.getPhysicalName());

                if(definedEntityField.getType().equals(DefinedEntityFieldType.STRING)) valuesSb.append("'");

                valuesSb.append(typedFieldMap.get(definedEntityField));

                if(definedEntityField.getType().equals(DefinedEntityFieldType.STRING)) valuesSb.append("'");

                fieldsSb.append(",");
                valuesSb.append(",");
            }
        }
        fieldsSb.append(getActiveField()).append(") ");
        valuesSb.append("'S') ");

        StringBuilder insertSb = new StringBuilder("insert into ").append(definedEntity.getPhysicalName())
                .append(fieldsSb).append(" values").append(valuesSb);

        log.info(insertSb.toString());

        if(jdbcTemplate.update(insertSb.toString())<0){
            throw new RuntimeException("Error insert value in "+definedEntity.getPhysicalName()+" : "+insertSb);
        }
    }

    @Override
    public Map<String, String> findById(DefinedEntity definedEntity, String id) {
        log.info("Querying Table...");

        StringBuilder sb = new StringBuilder("select ");

        int index = 0;
        String fieldId = null;
        for (DefinedEntityField definedEntityField : definedEntity.getFields()) {
            sb.append(definedEntityField.getPhysicalName());
            if(index+1<definedEntity.getFields().size()){
                sb.append(",");
            }
            if(definedEntityField.isFieldId()){
                fieldId=definedEntityField.getPhysicalName();
            }
            index++;
        }

        if(fieldId==null){
            throw new RuntimeException("Find By id only available if Entity has a id field. Entity DOES not have a id field!");
        }

        sb.append(" from ").append(definedEntity.getPhysicalName())
            .append(" where ").append(fieldId)
            .append(" = ").append(id)
            .append(" and ").append(getActiveCondition());


        log.info(sb.toString());

        List<Map<String, String>> resultList = jdbcTemplate.query(sb.toString(), (rs, rowNum) -> {
            Map<String, String> result = new HashMap<>();
            for (DefinedEntityField definedEntityField : definedEntity.getFields()) {
                result.put(definedEntityField.getFriendlyName(), rs.getString(definedEntityField.getPhysicalName()));
            }

            return result;
        });

        if(resultList!=null && resultList.size()>1){
            throw new RuntimeException("Expected 1 element but found "+resultList.size());
        }

        return resultList!=null&&resultList.size()>0?resultList.get(0):new HashMap<>();
    }

    @Override
    public void deleteById(DefinedEntity definedEntity, String id) {
        log.info("Querying Table...");

        StringBuilder sb = new StringBuilder("update ");

        String fieldId = getFieldId(definedEntity);

        if(fieldId==null){
            throw new RuntimeException("delete By id only available if Entity has a id field. Entity DOES not have a id field!");
        }

        sb.append(definedEntity.getPhysicalName()).append(" set ").append(getInActiveCondition())
                .append(" where ").append(fieldId).append(" = ")
                .append(id);


        log.info(sb.toString());

        if(jdbcTemplate.update(sb.toString())<0){
            throw new RuntimeException("Error logical delete row in "+definedEntity.getPhysicalName()+" : "+sb);
        }
    }

    private String getFieldId(DefinedEntity definedEntity) {
        String fieldId = null;
        for (DefinedEntityField definedEntityField : definedEntity.getFields()) {
            if(definedEntityField.isFieldId()){
                fieldId=definedEntityField.getPhysicalName();
            }
        }

        if(fieldId==null){
            throw new RuntimeException("Entity DOES not have a id field!");
        }

        return fieldId;
    }

    @Override
    public boolean exists(DefinedEntity definedEntity, String id) {

        String fieldId = getFieldId(definedEntity);

        log.info("Querying Table...");

        StringBuilder sb = new StringBuilder("SELECT count(*) FROM ");
        sb.append(definedEntity.getPhysicalName()).append(" where ")
            .append(fieldId).append("=?");

        int count = jdbcTemplate.queryForObject(
                sb.toString(), new Object[]{id}, Integer.class);

        if (count > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void updateEntityRow(DefinedEntity definedEntity, String id, Map<DefinedEntityField, String> typedFieldMap) {
        log.info("Creating record...");

        StringBuilder updateSb = new StringBuilder("update ").append(definedEntity.getPhysicalName())
                .append(" set ");

        int index = 0;
        for (DefinedEntityField definedEntityField : typedFieldMap.keySet()) {
            if(!definedEntityField.isFieldId()){
                updateSb.append(definedEntityField.getPhysicalName())
                    .append("=");

                if(definedEntityField.getType().equals(DefinedEntityFieldType.STRING)) updateSb.append("'");

                updateSb.append(typedFieldMap.get(definedEntityField));

                if(definedEntityField.getType().equals(DefinedEntityFieldType.STRING)) updateSb.append("'");

                if(index+1<typedFieldMap.keySet().size()){
                    updateSb.append(",");
                }
            }
            index++;
        }

        updateSb.append(" where ").append(getFieldId(definedEntity))
                .append("=").append(id);

        log.info(updateSb.toString());

        if(jdbcTemplate.update(updateSb.toString())<0){
            throw new RuntimeException("Error updating value in "+definedEntity.getPhysicalName()+" : "+updateSb);
        }
    }


    private String getActiveCondition(){
        return new StringBuilder(getActiveField()).append("='S'").toString();
    }

    private String getInActiveCondition(){
        return new StringBuilder(getActiveField()).append("='N'").toString();
    }

    private String getActiveField(){
        return "cndy_active_";
    }
}
