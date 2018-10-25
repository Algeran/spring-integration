package com.algeran.springfreamwork.springintegration;

import com.algeran.springfreamwork.springintegration.app.integration.PersonService;
import com.algeran.springfreamwork.springintegration.domain.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@IntegrationComponentScan
public class SpringIntegrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);
        List<Person> department_1 = Arrays.asList(
                new Person("Jhon", "Weak", LocalDate.of(1984, 5, 12), true, true),
                new Person("Dean", "Watson", LocalDate.of(1993, 3, 1), false, true),
                new Person("Bob", "Sunny", LocalDate.of(1974, 11, 23), false, true)
        );

        List<Person> department_2 = Arrays.asList(
                new Person("Sam", "Buddy", LocalDate.of(1982, 1, 22), true, true),
                new Person("Will", "Pops", LocalDate.of(1991, 6, 5), false, false),
                new Person("Glean", "Dill", LocalDate.of(1965, 12, 2), false, true)
        );

        PersonService personService = context.getBean(PersonService.class);
        personService.organizeEmployees(Arrays.asList(department_1,department_2))
                .forEach(System.out::println);

        context.close();
    }
}
