package org.candy.dataaccess.impl;

import org.candy.dataaccess.DDLDataService;
import org.candy.model.DefinedEntity;
import org.candy.model.DefinedEntityField;
import org.candy.model.DefinedEntityFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by jpedro on 25-06-2015.
 */
@Repository
public class DDLDataServiceImpl implements DDLDataService {
    private static final Logger log = LoggerFactory.getLogger(DDLDataServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void createDefinedEntity(DefinedEntity definedEntity) {
        log.info("Creating tables");

        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(definedEntity.getPhysicalName()).append(" (");

        String fieldId = null;
        boolean fieldIdIdentity = false;
        for (DefinedEntityField definedEntityField : definedEntity.getFields()) {
            if(definedEntityField.getType().equals(DefinedEntityFieldType.NUMBER)){
                sb.append(definedEntityField.getPhysicalName());
                sb.append(" Numeric");
            }
            if(definedEntityField.getType().equals(DefinedEntityFieldType.STRING)){
                sb.append(definedEntityField.getPhysicalName());
                sb.append(" VARCHAR(100)");
            }
            if(definedEntityField.isFieldId()){
                if(fieldId!=null){
                    throw new RuntimeException("Entity can only have one primary key");
                }
                fieldId = definedEntityField.getPhysicalName();
                if(definedEntityField.isFieldIdentity() &&
                        definedEntityField.getType().equals(DefinedEntityFieldType.NUMBER)){
                    fieldIdIdentity=true;
                    sb.append(" IDENTITY ");
                }
            }
            sb.append(",");
        }

        sb.append("CNDY_ACTIVE_ char(1)");

        if(fieldId != null && !fieldIdIdentity){
            sb.append(", PRIMARY KEY (").append(fieldId).append(")");
        }

        sb.append(")");
        log.info(sb.toString());
        jdbcTemplate.execute(sb.toString());


    }
}
