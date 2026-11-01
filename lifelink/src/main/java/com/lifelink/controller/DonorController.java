
package com.lifelink.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifelink.entity.Donor;
import com.lifelink.service.DonorService;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    // Save a donor
    @PostMapping("/save")
    public ResponseEntity<String> saveDonor(@RequestBody Donor donor) {
        String message = donorService.saveDonor(donor);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // Get all donors
    @GetMapping("/all")
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    // Get one donor by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDonorById(@PathVariable UUID id) {

        var donor = donorService.getDonorById(id);

        if (donor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donor with that id does not exist");
        }

        return ResponseEntity.ok(donor.get());
    }

    // Update a donor
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDonor(
            @PathVariable UUID id,
            @RequestBody Donor donor) {

        String message = donorService.updateDonor(id, donor);

        if (message.equals("The donor with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // Delete a donor
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDonor(@PathVariable UUID id) {

        String message = donorService.deleteDonor(id);

        if (message.equals("The donor with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // C1: Find donors by partial donation type name
    @GetMapping("/by-donation-type")
    public List<Donor> getDonorsByDonationType(
            @RequestParam("name") String name) {

        return donorService.getDonorsByDonationType(name);
    }

    // D3: Add a donation type to a donor
    @PutMapping("/{donorId}/donation-types/{donationTypeId}")
    public ResponseEntity<String> addDonationTypeToDonor(
            @PathVariable UUID donorId,
            @PathVariable UUID donationTypeId) {

        String message = donorService.addDonationTypeToDonor(
                donorId, donationTypeId);

        if (message.equals("The donor with that id does not exist")
                || message.equals("The donation type with that id does not exist")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals("Donor already has this donation type")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // D4: Change a donor's donation slot
    @PatchMapping("/{donorId}/donation-slot")
    public ResponseEntity<String> changeDonationSlot(
            @PathVariable UUID donorId,
            @RequestParam int slotNumber) {

        String message = donorService.changeDonationSlot(donorId, slotNumber);

        if (message.equals("The donor with that id does not exist")
                || message.equals("The donation slot with that number does not exist")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals("The donation slot is already taken")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
