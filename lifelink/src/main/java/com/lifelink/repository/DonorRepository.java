package com.lifelink.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelink.entity.Donor;

public interface DonorRepository extends JpaRepository<Donor, UUID> {

}