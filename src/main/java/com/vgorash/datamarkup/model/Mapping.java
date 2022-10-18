package com.vgorash.datamarkup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ida_mapping")
@Getter
@Setter
public class Mapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Image image;

    private boolean result;
}
