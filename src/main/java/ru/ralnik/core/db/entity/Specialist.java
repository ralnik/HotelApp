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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "specialist")
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "true")
    private Long id;

    @Column(nullable = false)
    private String fam;

    @Column(nullable = false)
    private String im;

    @Column(nullable = false)
    private String ot;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "positions_id", nullable = false)
    private Positions position;

    @Column
    private String comments;

}
