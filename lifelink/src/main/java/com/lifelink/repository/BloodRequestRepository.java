
package com.lifelink.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lifelink.entity.BloodRequest;
import com.lifelink.entity.Recipient;
import com.lifelink.enums.RequestStatus;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {

    // B2: Find requests by status
    List<BloodRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    // B3: Find requests between two dates
    List<BloodRequest> findByRequestDateBetweenOrderByRequestDateAsc(
            LocalDate start, LocalDate end);

    // C4: Find recipients associated with a donor
    @Query("SELECT DISTINCT br.recipient FROM BloodRequest br " +
           "WHERE br.donor.id = :donorId")
    List<Recipient> findRecipientsByDonorId(
            @Param("donorId") UUID donorId);
}
