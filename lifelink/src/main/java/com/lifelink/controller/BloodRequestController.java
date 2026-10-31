
package com.lifelink.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifelink.entity.BloodRequest;
import com.lifelink.entity.Recipient;
import com.lifelink.enums.RequestStatus;
import com.lifelink.service.BloodRequestService;
import com.lifelink.service.DonorService;

@RestController
@RequestMapping("/api/blood-requests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;
    private final DonorService donorService;

    public BloodRequestController(
            BloodRequestService bloodRequestService,
            DonorService donorService) {
        this.bloodRequestService = bloodRequestService;
        this.donorService = donorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBloodRequest(
            @RequestBody BloodRequest bloodRequest) {
        String message = bloodRequestService.saveBloodRequest(bloodRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestService.getAllBloodRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBloodRequestById(@PathVariable UUID id) {
        var bloodRequest = bloodRequestService.getBloodRequestById(id);

        if (bloodRequest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The blood request with that id does not exist");
        }

        return ResponseEntity.ok(bloodRequest.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBloodRequest(
            @PathVariable UUID id,
            @RequestBody BloodRequest bloodRequest) {
        String message =
                bloodRequestService.updateBloodRequest(id, bloodRequest);

        if (message.equals("The blood request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBloodRequest(@PathVariable UUID id) {
        String message = bloodRequestService.deleteBloodRequest(id);

        if (message.equals("The blood request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // B2
    @GetMapping("/by-status")
    public List<BloodRequest> getRequestsByStatus(
            @RequestParam("status") RequestStatus status) {
        return bloodRequestService.getRequestsByStatus(status);
    }

    // B3
    @GetMapping("/between")
    public List<BloodRequest> getRequestsBetweenDates(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return bloodRequestService.getRequestsBetweenDates(startDate, endDate);
    }

    // C4
    @GetMapping("/recipients-by-donor/{donorId}")
    public ResponseEntity<?> getRecipientsByDonorId(
            @PathVariable UUID donorId) {

        if (donorService.getDonorById(donorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donor with that id does not exist");
        }

        List<Recipient> recipients =
                bloodRequestService.getRecipientsByDonorId(donorId);

        return ResponseEntity.ok(recipients);
    }
}
