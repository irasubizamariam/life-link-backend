
package com.lifelink.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lifelink.entity.Donor;

public interface DonorRepository extends JpaRepository<Donor, UUID> {

    // C1: Find donors by donation type, ignoring uppercase and lowercase
    @Query("SELECT DISTINCT d FROM Donor d JOIN d.donationTypes dt " +
           "WHERE LOWER(dt.name) = LOWER(:typeName)")
    List<Donor> findDonorsByDonationType(
            @Param("typeName") String typeName);
}
