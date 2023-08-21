package com.epam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.entity.Batches;
@Repository
public interface BatchesRepository extends CrudRepository<Batches, Integer>{
    Batches findByNameAndPractice(String name,String practice);
}
