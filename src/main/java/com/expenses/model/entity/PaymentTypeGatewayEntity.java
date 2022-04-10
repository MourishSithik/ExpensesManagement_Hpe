package com.expenses.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by hubinotech on 09/04/22.
 */
@Data
@Entity(name = "payment_type_gateway")
@Table(name = "payment_type_gateway")
public class PaymentTypeGatewayEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "payment_type_name")
    private String paymentTypeName;
    @Column(name = "created_at")
    private String createdAt;
}
