package com.algeran.springfreamwork.springintegration.domain.model;

import lombok.Data;

import java.util.Collection;

@Data
public class Department {

    private Person lead;
    private Collection<Person> employees;
}
