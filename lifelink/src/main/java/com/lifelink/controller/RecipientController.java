
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifelink.entity.Recipient;
import com.lifelink.service.RecipientService;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    private final RecipientService recipientService;

    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    // Save a new recipient
    @PostMapping("/save")
    public ResponseEntity<String> saveRecipient(@RequestBody Recipient recipient) {
        String message = recipientService.saveRecipient(recipient);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // Get all recipients
    @GetMapping("/all")
    public List<Recipient> getAllRecipients() {
        return recipientService.getAllRecipients();
    }

    // Get one recipient by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipientById(@PathVariable UUID id) {

        var recipient = recipientService.getRecipientById(id);

        if (recipient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The recipient with that id does not exist");
        }

        return ResponseEntity.ok(recipient.get());
    }

    // Update a recipient
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecipient(
            @PathVariable UUID id,
            @RequestBody Recipient recipient) {

        String message = recipientService.updateRecipient(id, recipient);

        if (message.equals("The recipient with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // Delete a recipient
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRecipient(@PathVariable UUID id) {

        String message = recipientService.deleteRecipient(id);

        if (message.equals("The recipient with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    // B1: Find recipients by blood group and sort by last name
    @GetMapping("/by-blood-group")
    public List<Recipient> getRecipientsByBloodGroup(
            @RequestParam("bloodGroup") String bloodGroup) {

        return recipientService.getRecipientsByBloodGroup(bloodGroup);
    }

    // C4: Find recipients connected to a particular donor
    @GetMapping("/of-donor/{donorId}")
    public ResponseEntity<?> getRecipientsByDonor(
            @PathVariable UUID donorId) {

        if (!recipientService.donorExists(donorId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The donor with that id does not exist");
        }

        List<Recipient> recipients =
                recipientService.getRecipientsByDonorId(donorId);

        return ResponseEntity.ok(recipients);
    }
}
