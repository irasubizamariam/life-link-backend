package com.lifelink.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.Donor;
import com.lifelink.repository.DonorRepository;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
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
}