package com.lifelink.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelink.entity.DonationSlot;

public interface DonationSlotRepository extends JpaRepository<DonationSlot, UUID> {

}