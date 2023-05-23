package com.OCAL.MedHead.repository;

import org.springframework.data.repository.CrudRepository;
import com.OCAL.MedHead.model.BedOccupation;
import org.springframework.stereotype.Repository;
@Repository
public interface BedOccupationRepository extends CrudRepository<BedOccupation,Long>{

}
