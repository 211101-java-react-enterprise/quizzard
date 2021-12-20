package com.revature.quizzard.common.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * An abstract representation of a Resource.
 *
 * @author Wezley Singleton (GitHub: wsingleton)
 *
 */
@MappedSuperclass
public abstract class Resource {

    /** A unique id string for this resource */
    @Id
    @Column(name = "resource_id")
    protected String id;

    /** System information related to this resource */
    @Embedded
    protected ResourceMetadata metadata;

    public Resource() {
        super();
    }

    public Resource(ResourceMetadata metadata) {
        this.metadata = metadata;
    }

    public Resource(String id, ResourceMetadata metadata) {
        this(metadata);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Resource setId(String id) {
        this.id = id;
        return this;
    }

    public ResourceMetadata getMetadata() {
        return metadata;
    }

    public Resource setMetadata(ResourceMetadata metadata) {
        this.metadata = metadata;
        return this;
    }

}
