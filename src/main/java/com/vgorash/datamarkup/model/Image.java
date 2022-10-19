package com.vgorash.datamarkup.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "ida_image")
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String filepath;

    private Boolean gt;

    @OneToMany(mappedBy = "image")
    private List<Mapping> mappings;
}
