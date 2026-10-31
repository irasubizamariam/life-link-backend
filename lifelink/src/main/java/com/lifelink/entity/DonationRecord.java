package com.lifelink.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DonationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String component;

    private String remarks;

    private int unitsCollected;

    private LocalDate donationDate;

    @ManyToOne
    @JoinColumn(name = "blood_request_id")
    private BloodRequest bloodRequest;

    public DonationRecord() {
    }

    public DonationRecord(String component, String remarks, int unitsCollected,
                          LocalDate donationDate, BloodRequest bloodRequest) {
        this.component = component;
        this.remarks = remarks;
        this.unitsCollected = unitsCollected;
        this.donationDate = donationDate;
        this.bloodRequest = bloodRequest;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getUnitsCollected() {
        return unitsCollected;
    }

    public void setUnitsCollected(int unitsCollected) {
        this.unitsCollected = unitsCollected;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public BloodRequest getBloodRequest() {
        return bloodRequest;
    }

    public void setBloodRequest(BloodRequest bloodRequest) {
        this.bloodRequest = bloodRequest;
    }
}