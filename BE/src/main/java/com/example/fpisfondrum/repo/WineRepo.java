package com.example.fpisfondrum.repo;

import com.example.fpisfondrum.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WineRepo extends JpaRepository<Wine, Long>, JpaSpecificationExecutor<Wine> {
}
