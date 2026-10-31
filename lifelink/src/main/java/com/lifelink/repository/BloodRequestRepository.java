package com.lifelink.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelink.entity.BloodRequest;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {

}