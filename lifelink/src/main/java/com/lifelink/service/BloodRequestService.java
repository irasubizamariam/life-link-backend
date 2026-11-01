
package com.lifelink.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifelink.entity.BloodRequest;
import com.lifelink.entity.Recipient;
import com.lifelink.enums.RequestStatus;
import com.lifelink.repository.BloodRequestRepository;
import com.lifelink.repository.DonationRecordRepository;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final DonationRecordRepository donationRecordRepository;

    public BloodRequestService(
            BloodRequestRepository bloodRequestRepository,
            DonationRecordRepository donationRecordRepository) {

        this.bloodRequestRepository = bloodRequestRepository;
        this.donationRecordRepository = donationRecordRepository;
    }

    // D1: Validate and save a blood request
    public String saveBloodRequest(BloodRequest bloodRequest) {

        if (bloodRequest.getRequestDate() == null) {
            return "Blood request date is required";
        }

        if (bloodRequest.getRequestDate().isBefore(LocalDate.now())) {
            return "Blood request date cannot be in the past";
        }

        if (bloodRequest.getDonor() != null
                && bloodRequestRepository
                .existsByDonor_IdAndRequestDateAndStatusNot(
                        bloodRequest.getDonor().getId(),
                        bloodRequest.getRequestDate(),
                        RequestStatus.DECLINED)) {

            return "Donor is already matched to a request on that date";
        }

        bloodRequest.setStatus(RequestStatus.OPEN);
        bloodRequestRepository.save(bloodRequest);

        return "Blood request saved successfully";
    }

    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestRepository.findAll();
    }

    public Optional<BloodRequest> getBloodRequestById(UUID id) {
        return bloodRequestRepository.findById(id);
    }

    public Optional<BloodRequest> updateBloodRequest(
            UUID id, BloodRequest newRequest) {

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

    // E3: Delete a blood request only if it has no donation records
    public String deleteBloodRequest(UUID id) {

        Optional<BloodRequest> request =
                bloodRequestRepository.findById(id);

        if (request.isEmpty()) {
            return "The blood request with that id does not exist";
        }

        if (donationRecordRepository.existsByBloodRequest_Id(id)) {
            return "That blood request has donation records, delete them first";
        }

        bloodRequestRepository.delete(request.get());

        return "Blood request deleted successfully";
    }

    // B2
    public List<BloodRequest> getBloodRequestsByStatus(
            RequestStatus status) {

        return bloodRequestRepository
                .findByStatusOrderByRequestDateAsc(status);
    }

    // B3
    public List<BloodRequest> getBloodRequestsBetweenDates(
            LocalDate start, LocalDate end) {

        return bloodRequestRepository
                .findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    // C4
    public List<Recipient> getRecipientsByDonorId(UUID donorId) {
        return bloodRequestRepository.findRecipientsByDonorId(donorId);
    }

    // D2: Change blood request status
    public String changeBloodRequestStatus(
            UUID id, RequestStatus newStatus) {

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

    // F1
    public List<Object[]> getRequestStatistics() {
        return bloodRequestRepository.countRequestsByStatus();
    }

    // F2
    @Transactional
    public String cancelRequestsByDonorAndDate(
            UUID donorId, LocalDate date) {

        int cancelled = bloodRequestRepository
                .cancelRequestsByDonorAndDate(donorId, date);

        return cancelled + " blood requests cancelled";
    }
}
