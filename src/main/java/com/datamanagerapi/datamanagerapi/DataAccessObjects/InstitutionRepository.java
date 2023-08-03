package com.datamanagerapi.datamanagerapi.DataAccessObjects;

import com.datamanagerapi.datamanagerapi.Models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
}
