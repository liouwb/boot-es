package com.liouxb.boot.es.repository;


import com.liouxb.boot.es.domain.TestEs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liouwb
 */
public interface TestEsRepository extends JpaRepository<TestEs, Integer> {
}
