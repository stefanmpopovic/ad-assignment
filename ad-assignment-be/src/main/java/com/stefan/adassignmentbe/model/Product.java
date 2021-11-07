package com.stefan.adassignmentbe.model;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String unit;

    @Column
    private String deliveryTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDeliveryTimes() {
        return deliveryTimes;
    }

    public void setDeliveryTimes(String deliveryTimes) {
        this.deliveryTimes = deliveryTimes;
    }
}
