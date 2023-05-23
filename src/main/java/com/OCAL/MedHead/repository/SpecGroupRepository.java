package com.OCAL.MedHead.repository;

import org.springframework.data.repository.CrudRepository;

import com.OCAL.MedHead.model.SpecGroup;
import org.springframework.stereotype.Repository;
@Repository
public interface SpecGroupRepository extends CrudRepository<SpecGroup,Long> {

}
