package com.example.demo.rental.repository;

import com.example.demo.rental.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    Optional<RentalEntity>  findByRentalId(Long rentalId);

    List<RentalEntity> findByUser_UserIdAndReturnedAtIsNull(Long userId);
}
