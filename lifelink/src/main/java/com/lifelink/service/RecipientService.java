package com.lifelink.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lifelink.entity.Recipient;
import com.lifelink.repository.RecipientRepository;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    // Save recipient
    public String saveRecipient(Recipient recipient) {
        recipientRepository.save(recipient);
        return "Recipient saved successfully";
    }

    // Get all recipients
    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    // Get recipient by ID
    public Optional<Recipient> getRecipientById(UUID id) {
        return recipientRepository.findById(id);
    }

    // Update recipient
    public String updateRecipient(UUID id, Recipient newRecipient) {

        Optional<Recipient> existingRecipient =
                recipientRepository.findById(id);

        if (existingRecipient.isEmpty()) {
            return "The recipient with that id does not exist";
        }

        Recipient recipient = existingRecipient.get();

        recipient.setFirstName(newRecipient.getFirstName());
        recipient.setLastName(newRecipient.getLastName());
        recipient.setFileNumber(newRecipient.getFileNumber());
        recipient.setBloodGroup(newRecipient.getBloodGroup());
        recipient.setHospital(newRecipient.getHospital());

        recipientRepository.save(recipient);

        return "Recipient updated successfully";
    }

    // Delete recipient
    public String deleteRecipient(UUID id) {

        if (!recipientRepository.existsById(id)) {
            return "The recipient with that id does not exist";
        }

        recipientRepository.deleteById(id);

        return "Recipient deleted successfully";
    }
}