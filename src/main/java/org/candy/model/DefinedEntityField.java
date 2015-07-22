package org.candy.model;

import javax.persistence.*;

/**
 * Created by jpedro on 18-06-2015.
 */
@Entity
public class DefinedEntityField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String friendlyName;
    private String physicalName;
    private DefinedEntityFieldType type;
    private boolean fieldId;
    private boolean fieldIdentity;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    public DefinedEntityFieldType getType() {
        return type;
    }

    public void setType(DefinedEntityFieldType type) {
        this.type = type;
    }

    public boolean isFieldId() {
        return fieldId;
    }

    public void setFieldId(boolean fieldId) {
        this.fieldId = fieldId;
    }

    public boolean isFieldIdentity() {
        return fieldIdentity;
    }

    public void setFieldIdentity(boolean fieldIdentity) {
        this.fieldIdentity = fieldIdentity;
    }
}
