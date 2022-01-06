package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "user_to_organization")
public class UserToOrganization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserObject userObject;

    @ManyToOne
    @JoinColumn(name = "organization")
    private Organization organization;

    public UserToOrganization(int id, UserObject userObject, Organization organization) {
        this.id = id;
        this.userObject = userObject;
        this.organization = organization;
    }

    public UserToOrganization() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserObject getUserObject() {
        return userObject;
    }

    public void setUserObject(UserObject userObject) {
        this.userObject = userObject;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


}
