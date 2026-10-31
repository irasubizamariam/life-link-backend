package com.lifelink.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lifelink.entity.DonationRecord;

public interface DonationRecordRepository
        extends JpaRepository<DonationRecord, UUID> {

    // E2: Find donation records using recipient file number
    @Query("SELECT dr FROM DonationRecord dr " +
           "WHERE dr.bloodRequest.recipient.fileNumber = :fileNumber " +
           "ORDER BY dr.donationDate DESC")
    List<DonationRecord> findRecordsByRecipientFileNumber(
            @Param("fileNumber") String fileNumber);

    // E3: Check whether a blood request has donation records
    boolean existsByBloodRequest_Id(UUID bloodRequestId);
}