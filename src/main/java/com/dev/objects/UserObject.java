package com.dev.objects;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class UserObject {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "token")
    private String token;

}
