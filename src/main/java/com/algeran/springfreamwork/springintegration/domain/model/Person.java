package com.algeran.springfreamwork.springintegration.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private Integer age;
    private boolean lead;
    private boolean active;

    public Person(String name, String surname, LocalDate birthDate, boolean lead, boolean active) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.lead = lead;
        this.active = active;
    }
}
