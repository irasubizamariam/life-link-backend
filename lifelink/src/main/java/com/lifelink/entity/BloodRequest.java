package com.lifelink.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.lifelink.enums.RequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String bloodGroupNeeded;

    private int unitsNeeded;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    public BloodRequest() {
    }

    public BloodRequest(LocalDate requestDate,
                        RequestStatus status,
                        String bloodGroupNeeded,
                        int unitsNeeded,
                        Recipient recipient,
                        Donor donor) {

        this.requestDate = requestDate;
        this.status = status;
        this.bloodGroupNeeded = bloodGroupNeeded;
        this.unitsNeeded = unitsNeeded;
        this.recipient = recipient;
        this.donor = donor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getBloodGroupNeeded() {
        return bloodGroupNeeded;
    }

    public void setBloodGroupNeeded(String bloodGroupNeeded) {
        this.bloodGroupNeeded = bloodGroupNeeded;
    }

    public int getUnitsNeeded() {
        return unitsNeeded;
    }

    public void setUnitsNeeded(int unitsNeeded) {
        this.unitsNeeded = unitsNeeded;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }
}