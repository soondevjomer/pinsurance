package com.soondevjomer.pinsurance.repository;

import com.soondevjomer.pinsurance.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Integer> {
}
