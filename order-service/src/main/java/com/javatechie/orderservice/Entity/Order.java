package com.javatechie.orderservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "ORDER_TB")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private int id;
    private String name;
    private int qnty;
    private double price;


}