
package com.lifelink.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelink.entity.Recipient;

public interface RecipientRepository extends JpaRepository<Recipient, UUID> {

    // B1: Find recipients by blood group, ignoring case,
    // and sort by last name in ascending order.
    List<Recipient> findByBloodGroupIgnoreCaseOrderByLastNameAsc(String bloodGroup);

}
