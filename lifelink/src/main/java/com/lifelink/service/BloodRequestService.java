package com.lifelink.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.BloodRequest;
import com.lifelink.repository.BloodRequestRepository;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;

    public BloodRequestService(BloodRequestRepository bloodRequestRepository) {
        this.bloodRequestRepository = bloodRequestRepository;
    }

    public String saveBloodRequest(BloodRequest bloodRequest) {
        bloodRequestRepository.save(bloodRequest);
        return "Blood request saved successfully";
    }

    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }

    public Optional<BloodRequest> getBloodRequestById(UUID id) {
        return bloodRequestRepository.findById(id);
    }

    public String updateBloodRequest(UUID id, BloodRequest newBloodRequest) {

        Optional<BloodRequest> existing =
                bloodRequestRepository.findById(id);

        if (existing.isEmpty()) {
            return "The blood request with that id does not exist";
        }

        BloodRequest bloodRequest = existing.get();

        bloodRequest.setRequestDate(newBloodRequest.getRequestDate());
        bloodRequest.setBloodGroupNeeded(newBloodRequest.getBloodGroupNeeded());
        bloodRequest.setUnitsNeeded(newBloodRequest.getUnitsNeeded());
        bloodRequest.setRecipient(newBloodRequest.getRecipient());
        bloodRequest.setDonor(newBloodRequest.getDonor());

        // Status is deliberately not updated here.
        bloodRequestRepository.save(bloodRequest);

        return "Blood request updated successfully";
    }

    public String deleteBloodRequest(UUID id) {

        if (!bloodRequestRepository.existsById(id)) {
            return "The blood request with that id does not exist";
        }

        bloodRequestRepository.deleteById(id);

        return "Blood request deleted successfully";
    }
}