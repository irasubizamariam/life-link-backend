
package com.lifelink.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.DonationSlot;
import com.lifelink.entity.DonationType;
import com.lifelink.entity.Donor;
import com.lifelink.repository.DonationSlotRepository;
import com.lifelink.repository.DonationTypeRepository;
import com.lifelink.repository.DonorRepository;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final DonationTypeRepository donationTypeRepository;
    private final DonationSlotRepository donationSlotRepository;

    public DonorService(
            DonorRepository donorRepository,
            DonationTypeRepository donationTypeRepository,
            DonationSlotRepository donationSlotRepository) {

        this.donorRepository = donorRepository;
        this.donationTypeRepository = donationTypeRepository;
        this.donationSlotRepository = donationSlotRepository;
    }

    public String saveDonor(Donor donor) {
        donorRepository.save(donor);
        return "Donor saved successfully";
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Optional<Donor> getDonorById(UUID id) {
        return donorRepository.findById(id);
    }

    public String updateDonor(UUID id, Donor newDonor) {

        Optional<Donor> existingDonor = donorRepository.findById(id);

        if (existingDonor.isEmpty()) {
            return "The donor with that id does not exist";
        }

        Donor donor = existingDonor.get();

        donor.setFirstName(newDonor.getFirstName());
        donor.setLastName(newDonor.getLastName());
        donor.setBloodGroup(newDonor.getBloodGroup());
        donor.setPhone(newDonor.getPhone());
        donor.setDonationSlot(newDonor.getDonationSlot());
        donor.setDonationTypes(newDonor.getDonationTypes());

        donorRepository.save(donor);

        return "Donor updated successfully";
    }

    public String deleteDonor(UUID id) {

        if (!donorRepository.existsById(id)) {
            return "The donor with that id does not exist";
        }

        donorRepository.deleteById(id);

        return "Donor deleted successfully";
    }

    // C1: Find donors using partial, case-insensitive donation type matching
    public List<Donor> getDonorsByDonationType(String name) {
        return donorRepository.findDonorsByDonationType(name);
    }

    // D3: Add a donation type to a donor
    public String addDonationTypeToDonor(UUID donorId, UUID donationTypeId) {

        Optional<Donor> donorResult = donorRepository.findById(donorId);

        if (donorResult.isEmpty()) {
            return "The donor with that id does not exist";
        }

        Optional<DonationType> typeResult =
                donationTypeRepository.findById(donationTypeId);

        if (typeResult.isEmpty()) {
            return "The donation type with that id does not exist";
        }

        Donor donor = donorResult.get();
        DonationType donationType = typeResult.get();

        boolean alreadyHasType = donor.getDonationTypes().stream()
                .anyMatch(type -> type.getId().equals(donationTypeId));

        if (alreadyHasType) {
            return "Donor already has this donation type";
        }

        donor.getDonationTypes().add(donationType);
        donorRepository.save(donor);

        return "Donation type added to the donor";
    }

    // D4: Assign a donor to a donation slot
    public String changeDonationSlot(UUID donorId, int slotNumber) {

        Optional<Donor> donorResult = donorRepository.findById(donorId);

        if (donorResult.isEmpty()) {
            return "The donor with that id does not exist";
        }

        Optional<DonationSlot> slotResult =
                donationSlotRepository.findBySlotNumber(slotNumber);

        if (slotResult.isEmpty()) {
            return "The donation slot with that number does not exist";
        }

        Donor donor = donorResult.get();
        DonationSlot slot = slotResult.get();

        List<Donor> allDonors = donorRepository.findAll();

        for (Donor otherDonor : allDonors) {

            if (otherDonor.getDonationSlot() != null
                    && otherDonor.getDonationSlot().getId().equals(slot.getId())
                    && !otherDonor.getId().equals(donorId)) {

                return "The donation slot is already taken";
            }
        }

        donor.setDonationSlot(slot);
        donorRepository.save(donor);

        return "Donation slot changed successfully";
    }
}
