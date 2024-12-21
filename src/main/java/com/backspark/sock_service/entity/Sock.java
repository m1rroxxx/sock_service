package com.backspark.sock_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "socks")
public class Sock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "color_hex")
    private String colorHex;

    @Column(name = "cotton_percentage")
    private int cottonPercentage;

    @Column(name = "quantity")
    private int quantity;
}
