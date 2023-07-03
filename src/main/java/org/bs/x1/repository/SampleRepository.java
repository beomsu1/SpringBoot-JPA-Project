package org.bs.x1.repository;

import org.bs.x1.domain.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample,String>{
    
}
