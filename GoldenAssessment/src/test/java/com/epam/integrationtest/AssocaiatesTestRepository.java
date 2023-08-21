package com.epam.integrationtest;

import com.epam.entity.Associates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssocaiatesTestRepository extends JpaRepository<Associates,Integer> {
}
