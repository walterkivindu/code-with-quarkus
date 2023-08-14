package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;


@Entity
public class Person extends PanacheEntity {
    public Long age;
    public String name;
}
