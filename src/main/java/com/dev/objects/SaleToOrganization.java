package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "sale_to_organization")
public class SaleToOrganization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sale")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "organization")
    private Organization organization;

    public SaleToOrganization(int id, Sale sale, Organization organization) {
        this.id = id;
        this.sale = sale;
        this.organization = organization;
    }

    public SaleToOrganization() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


}
