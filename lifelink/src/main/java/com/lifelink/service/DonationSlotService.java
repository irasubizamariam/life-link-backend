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

    public String saveDonationSlot(DonationSlot donationSlot) {
        donationSlotRepository.save(donationSlot);
        return "Donation slot saved successfully";
    }

    public List<DonationSlot> getAllDonationSlots() {
        return donationSlotRepository.findAll();
    }

    public Optional<DonationSlot> getDonationSlotById(UUID id) {
        return donationSlotRepository.findById(id);
    }

    public String updateDonationSlot(UUID id, DonationSlot newDonationSlot) {

        Optional<DonationSlot> existing =
                donationSlotRepository.findById(id);

        if (existing.isEmpty()) {
            return "The donation slot with that id does not exist";
        }

        DonationSlot donationSlot = existing.get();

        donationSlot.setSlotNumber(newDonationSlot.getSlotNumber());
        donationSlot.setName(newDonationSlot.getName());

        donationSlotRepository.save(donationSlot);

        return "Donation slot updated successfully";
    }

    public String deleteDonationSlot(UUID id) {

        if (!donationSlotRepository.existsById(id)) {
            return "The donation slot with that id does not exist";
        }

        donationSlotRepository.deleteById(id);

        return "Donation slot deleted successfully";
    }
}