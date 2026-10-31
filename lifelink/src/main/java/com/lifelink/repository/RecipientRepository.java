package com.lifelink.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelink.entity.Recipient;

public interface RecipientRepository extends JpaRepository<Recipient, UUID> {

}