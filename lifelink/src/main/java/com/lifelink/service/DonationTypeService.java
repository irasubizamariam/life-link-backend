
package com.lifelink.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.DonationType;
import com.lifelink.repository.DonationTypeRepository;

@Service
public class DonationTypeService {

    private final DonationTypeRepository donationTypeRepository;

    public DonationTypeService(DonationTypeRepository donationTypeRepository) {
        this.donationTypeRepository = donationTypeRepository;
    }

    // Save a donation type
    public String saveDonationType(DonationType donationType) {
        donationTypeRepository.save(donationType);
        return "Donation type saved successfully";
    }

    // Get all donation types
    public List<DonationType> getAllDonationTypes() {
        return donationTypeRepository.findAll();
    }

    // Get one donation type by ID
    public Optional<DonationType> getDonationTypeById(UUID id) {
        return donationTypeRepository.findById(id);
    }

    // Update a donation type
    public String updateDonationType(UUID id, DonationType newDonationType) {

        Optional<DonationType> existingType =
                donationTypeRepository.findById(id);

        if (existingType.isEmpty()) {
            return "The donation type with that id does not exist";
        }

        DonationType donationType = existingType.get();
        donationType.setName(newDonationType.getName());

        donationTypeRepository.save(donationType);

        return "Donation type updated successfully";
    }

    // Delete a donation type
    public String deleteDonationType(UUID id) {

        if (!donationTypeRepository.existsById(id)) {
            return "The donation type with that id does not exist";
        }

        donationTypeRepository.deleteById(id);

        return "Donation type deleted successfully";
    }

    // C3: Find unused donation types
    public List<DonationType> getUnusedDonationTypes() {
        return donationTypeRepository.findUnusedDonationTypes();
    }
}
