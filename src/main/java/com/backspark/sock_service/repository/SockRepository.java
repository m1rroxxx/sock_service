package com.backspark.sock_service.repository;

import com.backspark.sock_service.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {
    Optional<Sock> findByColorHexAndCottonPercentage(String colorHex, int cottonPercentage);
}
