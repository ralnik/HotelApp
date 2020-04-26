package ru.ralnik.core.db.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "recieptionlist")
public class RecieptionList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "true")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rooms_id", nullable = false)
    private Rooms room;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date enterDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date exitDate;

    @Column(nullable = false)
    private Integer countPerson;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date registrationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column
    private String comments;
}
