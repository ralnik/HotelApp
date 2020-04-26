/*
 * Copyright (c) 2020.
 * @author Raschevkin Alexander (ralnik@mail.ru)
 */

package ru.ralnik.core.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "true")
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fam;

    @Column(nullable = false)
    private String im;

    @Column(nullable = false)
    private String ot;

    @Column(nullable = false)
    private String pasportSerial;

    @Column(nullable = false)
    private String pasportNumber;

    @Column(nullable = false)
    private Date pasportRecievedDate;

    @Column(nullable = false)
    private String pasportRecievedPlace;

    @Column
    private String comments;
}
