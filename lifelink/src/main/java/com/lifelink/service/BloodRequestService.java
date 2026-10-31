
package com.lifelink.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.BloodRequest;
import com.lifelink.entity.Recipient;
import com.lifelink.enums.RequestStatus;
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
        Optional<BloodRequest> existingRequest =
                bloodRequestRepository.findById(id);

        if (existingRequest.isEmpty()) {
            return "The blood request with that id does not exist";
        }

        BloodRequest bloodRequest = existingRequest.get();

        bloodRequest.setRequestDate(newBloodRequest.getRequestDate());
        bloodRequest.setBloodGroupNeeded(newBloodRequest.getBloodGroupNeeded());
        bloodRequest.setUnitsNeeded(newBloodRequest.getUnitsNeeded());
        bloodRequest.setRecipient(newBloodRequest.getRecipient());
        bloodRequest.setDonor(newBloodRequest.getDonor());

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

    // B2
    public List<BloodRequest> getRequestsByStatus(RequestStatus status) {
        return bloodRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    // B3
    public List<BloodRequest> getRequestsBetweenDates(
            LocalDate start, LocalDate end) {
        return bloodRequestRepository
                .findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    // C4
    public List<Recipient> getRecipientsByDonorId(UUID donorId) {
        return bloodRequestRepository.findRecipientsByDonorId(donorId);
    }
}
