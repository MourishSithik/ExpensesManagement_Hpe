package com.expenses.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by hubinotech on 09/04/22.
 */
@Data
@Entity(name = "consumers")
@Table(name = "consumers")
public class ConsumerEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "fname")
    private String fname;
    @Column(name = "lname")
    private String lname;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "created_at")
    private String createdAt;
}
