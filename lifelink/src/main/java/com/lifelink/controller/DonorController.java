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

import com.lifelink.entity.Donor;
import com.lifelink.service.DonorService;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveDonor(@RequestBody Donor donor) {
        String message = donorService.saveDonor(donor);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDonorById(@PathVariable UUID id) {
        var donor = donorService.getDonorById(id);

        if (donor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donor with that id does not exist");
        }

        return ResponseEntity.ok(donor.get());
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDonor(@PathVariable UUID id) {
        String message = donorService.deleteDonor(id);

        if (message.equals("The donor with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}