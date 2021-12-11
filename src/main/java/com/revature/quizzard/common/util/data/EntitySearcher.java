package com.revature.quizzard.common.util.data;

import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class EntitySearcher {

    private final EntityManager entityManager;

    @Autowired
    public EntitySearcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Programmatic query builder that searches the data source for records with data
     * matching to the provided search criteria. Supports nested object queries.
     *
     * @param searchCriteria
     *      map of field name and value entries to be used as search criteria
     *
     * @param entityClass
     *      the entity class to be queried for
     *
     * @param <T>
*           the type to query for and return, as specified by the second method param
     *
     * @return
     *      a set of persistent entity objects matching the provided search criteria
     *
     * @throws InvalidRequestException
     *      if the provided search criteria map is null or empty
     *
     * @throws InvalidRequestException
     *      if the provided entity class is not annotated with javax.persistence.Entity
     *
     * @throws InvalidRequestException
     *      if a provided search criteria key does not match to a known field in the specified entity type
     */
    public <T> Set<T> searchForEntity(Map<String, String> searchCriteria, Class<T> entityClass) {

        if (searchCriteria == null || searchCriteria.isEmpty()) {
            throw new InvalidRequestException("Null or empty search criteria map provided.");
        }

        if (entityClass.getAnnotation(Entity.class) == null) {
            throw new InvalidRequestException("Provided class is not annotated as an Entity!");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        Predicate predicate = cb.conjunction();

        for(Map.Entry<String, String> searchEntry : searchCriteria.entrySet()) {

            String searchKey = searchEntry.getKey();
            String searchVal = searchEntry.getValue();

            try {
                if (searchKey.contains(".")) {
                    String[] searchKeyFrags = searchKey.split("\\.");
                    String nestedTypeName = searchKeyFrags[0];
                    String nestedTypeFieldName = searchKeyFrags[1];
                    Join joinType = root.join(nestedTypeName);
                    Field nestedTypeField = entityClass.getDeclaredField(nestedTypeName).getType().getDeclaredField(nestedTypeFieldName);
                    predicate = getPredicate(cb, predicate, searchVal, nestedTypeField, joinType.get(nestedTypeFieldName));
                } else {
                    Field searchField = entityClass.getDeclaredField(searchKey);
                    predicate = getPredicate(cb, predicate, searchVal, searchField, root.get(searchKey));
                }
            } catch (NoSuchFieldException e) {
                String msg = String.format("No attribute with name: %s found on entity: %s", searchKey, entityClass.getName());
                throw new InvalidRequestException(msg);
            }

        }

        query.where(predicate);

        return new HashSet<>(entityManager.createQuery(query).getResultList());
    }

    /**
     * Takes in a search field and value to add as a boolean expression to the provided query predicate.
     *
     * @param cb
     *      a CriteriaBuilder object used to chain query predicate expressions
     *
     * @param predicate
     *      the existing query predicate expression
     *
     * @param searchVal
     *      search criteria value
     *
     * @param nestedTypeField
     *      search criteria field object
     *
     * @param path
     *      a Path object used to specify what table to search on
     *
     * @return the provided predicate
     *
     * @throws InvalidRequestException
     *      if an illegal enum value is provided as a search criteria value
     *
     */
    private Predicate getPredicate(CriteriaBuilder cb, Predicate predicate, String searchVal, Field nestedTypeField, Path path) {
        if (nestedTypeField.getType().isEnum()) {
            try {
                Enum enumVal = Enum.valueOf((Class<Enum>) nestedTypeField.getType(), searchVal.toUpperCase());
                predicate = cb.and(predicate, cb.equal(path, enumVal));
            } catch (IllegalArgumentException e) {
                throw new InvalidRequestException("Invalid enum value provided! Provided: " + searchVal);
            }
        } else {
            predicate = cb.and(predicate, cb.equal(path, searchVal));
        }
        return predicate;
    }

}
