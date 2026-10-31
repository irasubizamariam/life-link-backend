
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

    public BloodRequest saveBloodRequest(BloodRequest bloodRequest) {
        return bloodRequestRepository.save(bloodRequest);
    }

    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }

    public Optional<BloodRequest> getBloodRequestById(UUID id) {
        return bloodRequestRepository.findById(id);
    }

    public Optional<BloodRequest> updateBloodRequest(UUID id, BloodRequest newRequest) {
        Optional<BloodRequest> existingRequest =
                bloodRequestRepository.findById(id);

        if (existingRequest.isPresent()) {
            BloodRequest request = existingRequest.get();

            request.setRequestDate(newRequest.getRequestDate());
            request.setBloodGroupNeeded(newRequest.getBloodGroupNeeded());
            request.setUnitsNeeded(newRequest.getUnitsNeeded());
            request.setRecipient(newRequest.getRecipient());
            request.setDonor(newRequest.getDonor());

            return Optional.of(bloodRequestRepository.save(request));
        }

        return Optional.empty();
    }

    public boolean deleteBloodRequest(UUID id) {
        Optional<BloodRequest> request =
                bloodRequestRepository.findById(id);

        if (request.isPresent()) {
            bloodRequestRepository.delete(request.get());
            return true;
        }

        return false;
    }

    public List<BloodRequest> getBloodRequestsByStatus(RequestStatus status) {
        return bloodRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    public List<BloodRequest> getBloodRequestsBetweenDates(
            LocalDate start, LocalDate end) {

        return bloodRequestRepository
                .findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    public List<Recipient> getRecipientsByDonorId(UUID donorId) {
        return bloodRequestRepository.findRecipientsByDonorId(donorId);
    }

    public String changeBloodRequestStatus(UUID id, RequestStatus newStatus) {

        Optional<BloodRequest> optionalRequest =
                bloodRequestRepository.findById(id);

        if (optionalRequest.isEmpty()) {
            return "The blood request with that id does not exist";
        }

        BloodRequest request = optionalRequest.get();
        RequestStatus currentStatus = request.getStatus();

        boolean allowed = false;

        if (currentStatus == RequestStatus.OPEN) {
            allowed = newStatus == RequestStatus.MATCHED
                    || newStatus == RequestStatus.DECLINED;

        } else if (currentStatus == RequestStatus.MATCHED) {
            allowed = newStatus == RequestStatus.DONATED
                    || newStatus == RequestStatus.DECLINED;
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus
                    + " to " + newStatus;
        }

        request.setStatus(newStatus);
        bloodRequestRepository.save(request);

        return "Blood request status updated successfully";
    }
}
