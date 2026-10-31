package com.lifelink.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifelink.entity.DonationRecord;
import com.lifelink.service.DonationRecordService;

@RestController
@RequestMapping("/api/donation-records")
public class DonationRecordController {

    private final DonationRecordService donationRecordService;

    public DonationRecordController(
            DonationRecordService donationRecordService) {

        this.donationRecordService = donationRecordService;
    }

    // E1: Save a donation record
    @PostMapping("/save")
    public ResponseEntity<String> saveDonationRecord(
            @RequestParam UUID bloodRequestId,
            @RequestBody DonationRecord donationRecord) {

        String message = donationRecordService.saveDonationRecord(
                bloodRequestId, donationRecord);

        if (message.equals("The blood request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals(
                "Donation record can only be saved for a donated blood request")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // E2: Find donation records using recipient file number
    @GetMapping("/recipient/{fileNumber}")
    public List<DonationRecord> getRecordsByRecipientFileNumber(
            @PathVariable String fileNumber) {

        return donationRecordService
                .getRecordsByRecipientFileNumber(fileNumber);
    }

    // E3: Delete a donation record
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDonationRecord(
            @PathVariable UUID id) {

        String message = donationRecordService.deleteDonationRecord(id);

        if (message.equals(
                "The donation record with that id does not exist")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}