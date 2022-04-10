package com.expenses.model.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Created by hubinotech on 09/04/22.
 */
@Data
@Entity(name = "expenses")
@Table(name = "expenses")
public class ExpensesEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "payment_gateway_id")
    private int paymentGatewayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "payment_gateway_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private PaymentTypeGatewayEntity paymentTypeGatewayEntity;


    @Column(name = "consumer_id")
    private int consumerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "consumer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private ConsumerEntity consumerEntity;

    @Column(name = "amount")
    private String amount;

    @Column(name = "created_at")
    private String createdAt;
}
