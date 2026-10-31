
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

    @PostMapping("/save")
    public ResponseEntity<?> saveBloodRequest(
            @RequestBody BloodRequest bloodRequest) {

        bloodRequestService.saveBloodRequest(bloodRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Blood request saved successfully");
    }

    @GetMapping("/all")
    public List<BloodRequest> getAllBloodRequests() {
        return bloodRequestService.getAllBloodRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBloodRequestById(@PathVariable UUID id) {

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBloodRequest(@PathVariable UUID id) {

        if (bloodRequestService.deleteBloodRequest(id)) {
            return ResponseEntity.ok("Blood request deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The blood request with that id does not exist");
    }

    @GetMapping("/by-status")
    public List<BloodRequest> getBloodRequestsByStatus(
            @RequestParam RequestStatus status) {

        return bloodRequestService.getBloodRequestsByStatus(status);
    }

    @GetMapping("/between")
    public List<BloodRequest> getBloodRequestsBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return bloodRequestService.getBloodRequestsBetweenDates(start, end);
    }

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

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeBloodRequestStatus(
            @PathVariable UUID id,
            @RequestParam RequestStatus status) {

        String result =
                bloodRequestService.changeBloodRequestStatus(id, status);

        if (result.equals("The blood request with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

        } else if (result.startsWith("Cannot change status")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

        } else {
            return ResponseEntity.ok(result);
        }
    }
}
