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

    public String saveDonationType(DonationType donationType) {
        donationTypeRepository.save(donationType);
        return "Donation type saved successfully";
    }

    public List<DonationType> getAllDonationTypes() {
        return donationTypeRepository.findAll();
    }

    public Optional<DonationType> getDonationTypeById(UUID id) {
        return donationTypeRepository.findById(id);
    }

    public String updateDonationType(UUID id, DonationType newDonationType) {

        Optional<DonationType> existing =
                donationTypeRepository.findById(id);

        if (existing.isEmpty()) {
            return "The donation type with that id does not exist";
        }

        DonationType donationType = existing.get();

        donationType.setName(newDonationType.getName());

        donationTypeRepository.save(donationType);

        return "Donation type updated successfully";
    }

    public String deleteDonationType(UUID id) {

        if (!donationTypeRepository.existsById(id)) {
            return "The donation type with that id does not exist";
        }

        donationTypeRepository.deleteById(id);

        return "Donation type deleted successfully";
    }
}