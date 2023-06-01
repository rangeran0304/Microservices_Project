package com.rangedev.OrderService.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "Product_ID")
    private long productId;
    @Column(name = "Quantity")
    private long quantity;
    @Column(name = "Order_Date")
    private Instant orderDate;
    @Column(name = "Order_Status")
    private String orderStatus;
    @Column(name = "Amount")
    private long amount;
}
