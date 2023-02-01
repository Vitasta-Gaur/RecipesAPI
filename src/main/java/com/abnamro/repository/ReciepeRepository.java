package com.abnamro.repository;

import com.abnamro.entity.Reciepes;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReciepeRepository extends PagingAndSortingRepository<Reciepes,String>, JpaSpecificationExecutor<Reciepes> {
}
