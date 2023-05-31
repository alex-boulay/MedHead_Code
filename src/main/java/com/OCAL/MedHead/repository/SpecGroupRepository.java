package com.ocal.medhead.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.SpecGroup;
@Repository
public interface SpecGroupRepository extends CrudRepository<SpecGroup,Long> {

}
