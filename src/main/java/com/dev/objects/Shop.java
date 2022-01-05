package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



}
