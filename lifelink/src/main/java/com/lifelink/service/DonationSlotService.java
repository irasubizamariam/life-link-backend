
package com.lifelink.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.DonationSlot;
import com.lifelink.repository.DonationSlotRepository;

@Service
public class DonationSlotService {

    private final DonationSlotRepository donationSlotRepository;

    public DonationSlotService(DonationSlotRepository donationSlotRepository) {
        this.donationSlotRepository = donationSlotRepository;
    }

    // Save a donation slot
    public String saveDonationSlot(DonationSlot donationSlot) {
        donationSlotRepository.save(donationSlot);
        return "Donation slot saved successfully";
    }

    // Get all donation slots
    public List<DonationSlot> getAllDonationSlots() {
        return donationSlotRepository.findAll();
    }

    // Get one donation slot by ID
    public Optional<DonationSlot> getDonationSlotById(UUID id) {
        return donationSlotRepository.findById(id);
    }

    // Update a donation slot
    public String updateDonationSlot(UUID id, DonationSlot newDonationSlot) {

        Optional<DonationSlot> existingSlot =
                donationSlotRepository.findById(id);

        if (existingSlot.isEmpty()) {
            return "The donation slot with that id does not exist";
        }

        DonationSlot donationSlot = existingSlot.get();

        donationSlot.setSlotNumber(newDonationSlot.getSlotNumber());
        donationSlot.setName(newDonationSlot.getName());

        donationSlotRepository.save(donationSlot);

        return "Donation slot updated successfully";
    }

    // Delete a donation slot
    public String deleteDonationSlot(UUID id) {

        if (!donationSlotRepository.existsById(id)) {
            return "The donation slot with that id does not exist";
        }

        donationSlotRepository.deleteById(id);

        return "Donation slot deleted successfully";
    }

    // C2: Get free donation slots
    public List<DonationSlot> getFreeDonationSlots() {
        return donationSlotRepository.findFreeDonationSlots();
    }
}
