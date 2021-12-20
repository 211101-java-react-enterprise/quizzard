package com.revature.quizzard.common.dtos;

import com.revature.quizzard.common.domain.ResourceMetadata;

import java.time.LocalDateTime;

public class ResourceMetadataResponse {

    /** The id of the creator of this resource */
    private String resourceCreatorId;

    /** The id of the last modifier of this resource */
    private String lastModifierId;

    /** The id of the owner of this resource */
    private String resourceOwnerId;

    /** A bool representing whether or not this an active resource or not */
    private boolean isActive;

    /** The datetime of resource creation */
    private String resourceCreationDateTime;

    /** The datetime of the last modification to this resource */
    private String lastModifiedDateTime;

    public String getResourceCreatorId() {
        return resourceCreatorId;
    }

    public void setResourceCreatorId(String resourceCreatorId) {
        this.resourceCreatorId = resourceCreatorId;
    }

    public String getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(String lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getResourceOwnerId() {
        return resourceOwnerId;
    }

    public void setResourceOwnerId(String resourceOwnerId) {
        this.resourceOwnerId = resourceOwnerId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getResourceCreationDateTime() {
        return resourceCreationDateTime;
    }

    public void setResourceCreationDateTime(String resourceCreationDateTime) {
        this.resourceCreationDateTime = resourceCreationDateTime;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public ResourceMetadataResponse(ResourceMetadata metadata) {
        this.resourceCreatorId = metadata.getResourceCreator().getId();
        this.lastModifierId = metadata.getLastModifier().getId();
        this.resourceOwnerId = metadata.getResourceOwner().getId();
        this.isActive = metadata.isActive();
        this.resourceCreationDateTime = metadata.getResourceCreationDateTime().toString();
        this.lastModifiedDateTime = metadata.getLastModifiedDateTime().toString();
    }

    @Override
    public String toString() {
        return "ResourceMetadataResponse{" +
                "resourceCreatorId='" + resourceCreatorId + '\'' +
                ", lastModifierId='" + lastModifierId + '\'' +
                ", resourceOwnerId='" + resourceOwnerId + '\'' +
                ", isActive=" + isActive +
                ", resourceCreationDateTime='" + resourceCreationDateTime + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                '}';
    }

}
