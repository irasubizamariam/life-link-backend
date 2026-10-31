package com.lifelink.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelink.entity.DonationType;

public interface DonationTypeRepository extends JpaRepository<DonationType, UUID> {

}