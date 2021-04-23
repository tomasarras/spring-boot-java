package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
