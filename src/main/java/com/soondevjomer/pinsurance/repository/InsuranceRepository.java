package com.soondevjomer.pinsurance.repository;

import com.soondevjomer.pinsurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
}
