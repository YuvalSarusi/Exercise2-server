package com.dev.objects;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shop")
    private Shop shop;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "is_for_all")
    private int isForAll;


    public Sale(int id, Shop shop, String description, Date startTime, Date endTime, Organization organization) {
        this.id = id;
        this.shop = shop;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Sale(int id, Shop shop, String description, Date startTime, Date endTime, int isForAll) {
        this.id = id;
        this.shop = shop;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isForAll = isForAll;
    }

    public Sale() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



    public int isForAll() {
        return isForAll;
    }

    public void setForAll(int forAll) {
        isForAll = forAll;
    }
}
