
package com.lifelink.controller;

import java.time.LocalDate;
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

    // D1: Save blood request with validation
    @PostMapping("/save")
    public ResponseEntity<?> saveBloodRequest(
            @RequestBody BloodRequest bloodRequest) {

        String result =
                bloodRequestService.saveBloodRequest(bloodRequest);

        if (result.equals("Blood request date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(result);

        } else if (result.equals(
                "Donor is already matched to a request on that date")) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(result);

        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(result);
        }
    }

    @GetMapping("/all")
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestService.getAllBloodRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBloodRequestById(
            @PathVariable UUID id) {

        return bloodRequestService.getBloodRequestById(id)
                .map(request -> ResponseEntity.ok((Object) request))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The blood request with that id does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBloodRequest(
            @PathVariable UUID id,
            @RequestBody BloodRequest bloodRequest) {

        return bloodRequestService.updateBloodRequest(id, bloodRequest)
                .map(request -> ResponseEntity.ok((Object) request))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The blood request with that id does not exist"));
    }

    // E3: Delete a blood request
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBloodRequest(
            @PathVariable UUID id) {

        String message = bloodRequestService.deleteBloodRequest(id);

        if (message.equals(
                "The blood request with that id does not exist")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals(
                "That blood request has donation records, delete them first")) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // B2
    @GetMapping("/by-status")
    public List<BloodRequest> getBloodRequestsByStatus(
            @RequestParam RequestStatus status) {

        return bloodRequestService.getBloodRequestsByStatus(status);
    }

    // B3
    @GetMapping("/between")
    public List<BloodRequest> getBloodRequestsBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return bloodRequestService
                .getBloodRequestsBetweenDates(start, end);
    }

    // C4
    @GetMapping("/recipients-by-donor/{donorId}")
    public ResponseEntity<?> getRecipientsByDonorId(
            @PathVariable UUID donorId) {

        if (!donorService.getDonorById(donorId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donor with that id does not exist");
        }

        List<Recipient> recipients =
                bloodRequestService.getRecipientsByDonorId(donorId);

        return ResponseEntity.ok(recipients);
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeBloodRequestStatus(
            @PathVariable UUID id,
            @RequestParam RequestStatus status) {

        String result =
                bloodRequestService.changeBloodRequestStatus(id, status);

        if (result.equals(
                "The blood request with that id does not exist")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(result);

        } else if (result.startsWith("Cannot change status")) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(result);

        } else {
            return ResponseEntity.ok(result);
        }
    }

    // F1: Get blood request statistics by status
    @GetMapping("/stats/by-status")
    public List<Object[]> getRequestStatistics() {
        return bloodRequestService.getRequestStatistics();
    }

    // F2: Cancel requests for a donor on a particular date
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelRequestsByDonorAndDate(
            @RequestParam UUID donorId,
            @RequestParam LocalDate date) {

        String result =
                bloodRequestService.cancelRequestsByDonorAndDate(
                        donorId, date);

        return ResponseEntity.ok(result);
    }
}
