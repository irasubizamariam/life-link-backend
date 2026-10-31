package com.lifelink.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifelink.entity.DonationSlot;

public interface DonationSlotRepository extends JpaRepository<DonationSlot, UUID> {

    // C2: Find free donation slots
    @Query("SELECT s FROM DonationSlot s " +
           "WHERE NOT EXISTS " +
           "(SELECT d FROM Donor d WHERE d.donationSlot = s) " +
           "ORDER BY s.slotNumber ASC")
    List<DonationSlot> findFreeDonationSlots();

    // D4: Find a donation slot using its slot number
    Optional<DonationSlot> findBySlotNumber(int slotNumber);
}