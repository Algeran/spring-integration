package com.algeran.springfreamwork.springintegration.app.configuration;

import com.algeran.springfreamwork.springintegration.domain.model.Department;
import com.algeran.springfreamwork.springintegration.domain.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Configuration
public class IntegrationConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(1000).get();
    }

    @Bean
    public Executor channelExecutor() {
        return Executors.newFixedThreadPool(3);
    }

    @Bean
    public IntegrationFlow persons(Executor channelExecutor) {
        return f -> f
                .split()
                .split()
                .channel(c -> c.executor(channelExecutor))
                .<Person,Boolean>route(Person::isActive, mapping -> mapping
                        .subFlowMapping(false, sf -> sf
                                .channel(c -> c.queue(10))
                                .handle((Person person, Map<String, Object> headers) -> {
                                    System.out.println("Person "
                                            + person.getName()
                                            + " " + person.getSurname()
                                            + "is inactive, so we will not include him in department");
                                    return person;
                                }))
                        .subFlowMapping(true, sf -> sf
                                .channel(c -> c.queue(10))
                                .handle((Person person, Map<String, Object> headers) -> {
                                    System.out.println("Person "
                                            + person.getName()
                                            + " " + person.getSurname()
                                            + "is inactive, so we will not include him in department");
                                    return person;
                                })))
                .<Person,Person>transform(person -> {
                    int personAge = Period.between(person.getBirthDate(), LocalDate.now()).getYears();
                    person.setAge(personAge);
                    return person;
                })
                .aggregate()
                .channel(c -> c.direct())
                .<Collection<Person>,Department>transform(persons -> {
                    Department department = new Department();
                    persons = persons.stream()
                            .peek(person -> {
                                if (person.isLead()) {
                                    department.setLead(person);
                                }
                            })
                            .filter(Person::isActive)
                            .collect(Collectors.toSet());
                    department.setEmployees(persons);
                    return department;
                })
                .aggregate();
    }
}
