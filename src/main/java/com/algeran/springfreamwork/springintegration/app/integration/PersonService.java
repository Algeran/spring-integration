package com.algeran.springfreamwork.springintegration.app.integration;

import com.algeran.springfreamwork.springintegration.domain.model.Department;
import com.algeran.springfreamwork.springintegration.domain.model.Person;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

@MessagingGateway
public interface PersonService {

    @Gateway(requestChannel = "persons.input")
    Collection<Department> organizeEmployees(Collection<Collection<Person>> persons);
}
