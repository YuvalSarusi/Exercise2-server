package com.dev.objects;


import javax.persistence.*;

@Entity
@Table(name = "userToOrganization")
public class UserToOrganization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "organization")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserObject userObject;
}
