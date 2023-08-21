package com.epam.integrationtest;

import com.epam.entity.Batches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchesTestRepository extends JpaRepository<Batches,Integer> {
}
