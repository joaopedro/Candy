package org.candy.dataaccess;

import org.candy.model.DefinedEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "entity", path = "entity")
public interface DefinedEntityRepository extends PagingAndSortingRepository<DefinedEntity, Long> {

	List<DefinedEntity> findByFriendlyName(@Param("name") String name);
}