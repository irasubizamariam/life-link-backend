
package com.lifelink.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifelink.entity.DonationType;

public interface DonationTypeRepository extends JpaRepository<DonationType, UUID> {

    // C3: Find donation types that are not assigned to any donor
    @Query("SELECT dt FROM DonationType dt WHERE dt.donors IS EMPTY")
    List<DonationType> findUnusedDonationTypes();
}
