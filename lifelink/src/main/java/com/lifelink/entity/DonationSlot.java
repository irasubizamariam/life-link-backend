package com.lifelink.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DonationSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int slotNumber;

    private String name;

    public DonationSlot() {
    }

    public DonationSlot(int slotNumber, String name) {
        this.slotNumber = slotNumber;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}