package com.lifelink.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifelink.entity.DonationSlot;
import com.lifelink.service.DonationSlotService;

@RestController
@RequestMapping("/api/donation-slots")
public class DonationSlotController {

    private final DonationSlotService donationSlotService;

    public DonationSlotController(DonationSlotService donationSlotService) {
        this.donationSlotService = donationSlotService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveDonationSlot(
            @RequestBody DonationSlot donationSlot) {

        String message = donationSlotService.saveDonationSlot(donationSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<DonationSlot> getAllDonationSlots() {
        return donationSlotService.getAllDonationSlots();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDonationSlotById(@PathVariable UUID id) {
        var donationSlot = donationSlotService.getDonationSlotById(id);

        if (donationSlot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donation slot with that id does not exist");
        }

        return ResponseEntity.ok(donationSlot.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDonationSlot(
            @PathVariable UUID id,
            @RequestBody DonationSlot donationSlot) {

        String message = donationSlotService.updateDonationSlot(id, donationSlot);

        if (message.equals("The donation slot with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDonationSlot(@PathVariable UUID id) {
        String message = donationSlotService.deleteDonationSlot(id);

        if (message.equals("The donation slot with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}