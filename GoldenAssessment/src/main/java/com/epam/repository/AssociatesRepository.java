package com.epam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.entity.Associates;

import jakarta.transaction.Transactional;
@Repository
public interface AssociatesRepository extends CrudRepository<Associates, Integer>{
	@Transactional
	List<Associates> findByGender(String gender);
}
