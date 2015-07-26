package org.candy.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class DefinedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String friendlyName;
	private String physicalName;
    private boolean deleted=false;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="defined_entity_id", referencedColumnName="id")
    private List<DefinedEntityField> fields;

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

    public List<DefinedEntityField> getFields() {
        return fields;
    }

    public void setFields(List<DefinedEntityField> fields) {
        this.fields = fields;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}