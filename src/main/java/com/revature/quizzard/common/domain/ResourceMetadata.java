package com.revature.quizzard.common.domain;

import com.revature.quizzard.user.AppUser;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;

/**
 * A model which is embedded into all Resource documents and is used to convey
 * system information.
 *
 * @author Wezley Singleton (GitHub: wsingleton)
 *
 */
@Embeddable
public class ResourceMetadata {

    /** The creator of this resource */
    @ManyToOne
    @JoinColumn(name = "resource_creator_id")
    private AppUser resourceCreator;

    /** The last modifier of this resource */
    @ManyToOne
    @JoinColumn(name = "last_modifier_id")
    private AppUser lastModifier;

    /** The owner of this resource */
    @ManyToOne
    @JoinColumn(name = "resource_owner_id")
    private AppUser resourceOwner;

    /** A bool representing whether or not this an active resource or not */
    @Column(name = "is_active")
    private boolean isActive;

    /** The datetime of resource creation */
    @Column(name = "resource_creation_datetime")
    private LocalDateTime resourceCreationDateTime;

    /** The datetime of the last modification to this resource */
    @Column(name = "last_modified_datetime")
    private LocalDateTime lastModifiedDateTime;

    public ResourceMetadata() {
        super();
    }

    /**
     * Creates a new ResourceMetadata instance where the provided creator id is used as the
     * last modifier id, and the resource creation time and last modified time is set to
     * the time of instantiation.
     *
     * @param creator the user that created a resource (also used as the last modifier id and owner)
     */
    public ResourceMetadata(AppUser creator) {
        this.resourceCreator = creator;
        this.resourceCreationDateTime = LocalDateTime.now();
        this.lastModifier = creator;
        this.lastModifiedDateTime = LocalDateTime.now();
        this.resourceOwner = creator;
        this.isActive = true;
    }

    /**
     * Creates a new ResourceMetadata instance where the provided creator id is used as the
     * last modifier id, and the resource creation time and last modified time is set to
     * the time of instantiation.
     *
     * @param creator the user that created a resource (also used as the last modifier id)
     * @param owner the owner of a resource
     */
    public ResourceMetadata(AppUser creator, AppUser owner) {
        this(creator);
        this.resourceOwner = owner;
    }

    /**
     * Creates a new ResourceMetadata instance where the provided creator id is used as the
     * last modifier id, and the resource creation time and last modified time is set to
     * the time of instantiation.
     *
     * @param creator the user that created a resource (also used as the last modifier id)
     * @param owner the owner of a resource
     * @param active bool representing whether or not this an active resource or not
     */
    public ResourceMetadata(AppUser creator, AppUser owner, boolean active) {
        this(creator, owner);
        this.isActive = active;
    }

    /**
     * Creates a new ResourceMetadata instance.
     *
     * @param creator the user that created a resource
     * @param creationDT datetime of resource creation
     * @param modifier the user that last modified this resource
     * @param modDT datetime of the last modification to this resource
     * @param owner the owner of a resource
     * @param active bool representing whether this an active resource or not
     */
    public ResourceMetadata(AppUser creator, LocalDateTime creationDT, AppUser modifier, LocalDateTime modDT, AppUser owner, boolean active) {
        this(creator,  owner, active);
        this.resourceCreationDateTime = creationDT;
        this.lastModifier = modifier;
        this.lastModifiedDateTime = modDT;
    }

    public AppUser getResourceCreator() {
        return resourceCreator;
    }

    public ResourceMetadata setResourceCreator(AppUser resourceCreator) {
        this.resourceCreator = resourceCreator;
        return this;
    }

    public AppUser getLastModifier() {
        return lastModifier;
    }

    public ResourceMetadata setLastModifier(AppUser lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }

    public AppUser getResourceOwner() {
        return resourceOwner;
    }

    public ResourceMetadata setResourceOwner(AppUser resourceOwner) {
        this.resourceOwner = resourceOwner;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ResourceMetadata setActive(boolean active) {
        isActive = active;
        return this;
    }

    public LocalDateTime getResourceCreationDateTime() {
        return resourceCreationDateTime;
    }

    public ResourceMetadata setResourceCreationDateTime(LocalDateTime resourceCreationDateTime) {
        this.resourceCreationDateTime = resourceCreationDateTime;
        return this;
    }

    public LocalDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public ResourceMetadata setLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
        return this;
    }

}
