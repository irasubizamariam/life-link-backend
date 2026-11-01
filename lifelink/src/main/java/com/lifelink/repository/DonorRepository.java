
package com.lifelink.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lifelink.entity.Donor;

public interface DonorRepository extends JpaRepository<Donor, UUID> {

    // C1: Find donors by donation type using partial and case-insensitive matching
    @Query("SELECT DISTINCT d FROM Donor d JOIN d.donationTypes dt " +
           "WHERE LOWER(dt.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Donor> findDonorsByDonationType(@Param("name") String name);

}
