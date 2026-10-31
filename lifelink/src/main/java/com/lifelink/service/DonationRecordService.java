package com.lifelink.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.BloodRequest;
import com.lifelink.entity.DonationRecord;
import com.lifelink.enums.RequestStatus;
import com.lifelink.repository.BloodRequestRepository;
import com.lifelink.repository.DonationRecordRepository;

@Service
public class DonationRecordService {

    private final DonationRecordRepository donationRecordRepository;
    private final BloodRequestRepository bloodRequestRepository;

    public DonationRecordService(
            DonationRecordRepository donationRecordRepository,
            BloodRequestRepository bloodRequestRepository) {

        this.donationRecordRepository = donationRecordRepository;
        this.bloodRequestRepository = bloodRequestRepository;
    }

    // E1: Save a donation record
    public String saveDonationRecord(
            UUID bloodRequestId, DonationRecord donationRecord) {

        Optional<BloodRequest> requestResult =
                bloodRequestRepository.findById(bloodRequestId);

        if (requestResult.isEmpty()) {
            return "The blood request with that id does not exist";
        }

        BloodRequest bloodRequest = requestResult.get();

        if (bloodRequest.getStatus() != RequestStatus.DONATED) {
            return "Donation record can only be saved for a donated blood request";
        }

        donationRecord.setDonationDate(LocalDate.now());
        donationRecord.setBloodRequest(bloodRequest);

        donationRecordRepository.save(donationRecord);

        return "Donation record saved successfully";
    }

    // E2: Find donation records using recipient file number
    public List<DonationRecord> getRecordsByRecipientFileNumber(
            String fileNumber) {

        return donationRecordRepository
                .findRecordsByRecipientFileNumber(fileNumber);
    }

    // E3: Delete a donation record
    public String deleteDonationRecord(UUID id) {

        if (!donationRecordRepository.existsById(id)) {
            return "The donation record with that id does not exist";
        }

        donationRecordRepository.deleteById(id);

        return "Donation record deleted successfully";
    }
}