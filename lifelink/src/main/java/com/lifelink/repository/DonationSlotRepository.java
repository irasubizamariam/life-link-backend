
package com.lifelink.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifelink.entity.DonationSlot;

public interface DonationSlotRepository extends JpaRepository<DonationSlot, UUID> {

    // C2: Find donation slots that are not assigned to any donor
    @Query("SELECT s FROM DonationSlot s " +
           "WHERE NOT EXISTS " +
           "(SELECT d FROM Donor d WHERE d.donationSlot = s) " +
           "ORDER BY s.slotNumber ASC")
    List<DonationSlot> findFreeDonationSlots();
}
