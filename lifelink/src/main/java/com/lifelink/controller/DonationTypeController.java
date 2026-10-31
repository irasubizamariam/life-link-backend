
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

import com.lifelink.entity.DonationType;
import com.lifelink.service.DonationTypeService;

@RestController
@RequestMapping("/api/donation-types")
public class DonationTypeController {

    private final DonationTypeService donationTypeService;

    public DonationTypeController(DonationTypeService donationTypeService) {
        this.donationTypeService = donationTypeService;
    }

    // Save a donation type
    @PostMapping("/save")
    public ResponseEntity<String> saveDonationType(
            @RequestBody DonationType donationType) {

        String message = donationTypeService.saveDonationType(donationType);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // Get all donation types
    @GetMapping("/all")
    public List<DonationType> getAllDonationTypes() {
        return donationTypeService.getAllDonationTypes();
    }

    // Get one donation type by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDonationTypeById(@PathVariable UUID id) {

        var donationType = donationTypeService.getDonationTypeById(id);

        if (donationType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donation type with that id does not exist");
        }

        return ResponseEntity.ok(donationType.get());
    }

    // Update a donation type
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDonationType(
            @PathVariable UUID id,
            @RequestBody DonationType donationType) {

        String message =
                donationTypeService.updateDonationType(id, donationType);

        if (message.equals("The donation type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // Delete a donation type
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDonationType(@PathVariable UUID id) {

        String message = donationTypeService.deleteDonationType(id);

        if (message.equals("The donation type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // C3: Find unused donation types
    @GetMapping("/unused")
    public List<DonationType> getUnusedDonationTypes() {
        return donationTypeService.getUnusedDonationTypes();
    }
}
