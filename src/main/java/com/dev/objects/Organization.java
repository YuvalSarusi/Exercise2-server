package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "organization_name")
    private String organizationName;



}
