package com.charter.user.rewards.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    Long userId;

    @Column(name = "USER_NAME")
    String userName;


}
