package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "saleToOrganization")
public class SaleToOrganization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
