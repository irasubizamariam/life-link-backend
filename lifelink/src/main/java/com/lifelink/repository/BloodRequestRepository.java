
package com.lifelink.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lifelink.entity.BloodRequest;
import com.lifelink.entity.Recipient;
import com.lifelink.enums.RequestStatus;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {

    List<BloodRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    List<BloodRequest> findByRequestDateBetweenOrderByRequestDateAsc(
            LocalDate start, LocalDate end);

    @Query("SELECT DISTINCT br.recipient FROM BloodRequest br WHERE br.donor.id = :donorId")
    List<Recipient> findRecipientsByDonorId(@Param("donorId") UUID donorId);

    boolean existsByDonor_IdAndRequestDateAndStatusNot(
            UUID donorId, LocalDate requestDate, RequestStatus status);

    @Query("SELECT br.status, COUNT(br) FROM BloodRequest br GROUP BY br.status")
    List<Object[]> countRequestsByStatus();

    @Modifying
    @Query("UPDATE BloodRequest br SET br.status = com.lifelink.enums.RequestStatus.DECLINED " +
           "WHERE br.donor.id = :donorId AND br.requestDate = :date " +
           "AND br.status <> com.lifelink.enums.RequestStatus.DONATED")
    int cancelRequestsByDonorAndDate(
            @Param("donorId") UUID donorId,
            @Param("date") LocalDate date);
}
