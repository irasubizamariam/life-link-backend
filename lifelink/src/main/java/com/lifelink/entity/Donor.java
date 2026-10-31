package com.lifelink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "donors")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String bloodGroup;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "donation_slot_id")
    private DonationSlot donationSlot;

    @ManyToMany
    @JoinTable(
        name = "donor_donation_type",
        joinColumns = @JoinColumn(name = "donor_id"),
        inverseJoinColumns = @JoinColumn(name = "donation_type_id")
    )
    private List<DonationType> donationTypes = new ArrayList<>();

    public Donor() {
    }

    public Donor(String firstName, String lastName,
                 String bloodGroup, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DonationSlot getDonationSlot() {
        return donationSlot;
    }

    public void setDonationSlot(DonationSlot donationSlot) {
        this.donationSlot = donationSlot;
    }

    public List<DonationType> getDonationTypes() {
        return donationTypes;
    }

    public void setDonationTypes(List<DonationType> donationTypes) {
        this.donationTypes = donationTypes;
    }
}