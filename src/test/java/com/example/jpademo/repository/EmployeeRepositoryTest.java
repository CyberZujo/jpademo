package com.example.jpademo.repository;


import com.example.jpademo.JpademoApplication;
import com.example.jpademo.entity.Employee;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpademoApplication.class)
public class EmployeeRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    @Test
    public void findById() {
        Employee employee = entityManager.find(Employee.class, 1001L);

        assertEquals(1001L, employee.getId());
    }

    @Test
    @Transactional
    @DirtiesContext
    public void insertTest() {
        Employee employee = new Employee("john","doe","jdoe@hotmail.com","test");

        entityManager.persist(employee);

        assertNotNull(employee.getId());
    }


    @Test
    @Transactional
    public void updateTest() {
        Employee employee = entityManager.find(Employee.class,1L);
        employee.setFirstName("test");
        entityManager.merge(employee);

        Employee employeeUpdated = entityManager.find(Employee.class,1L);
        assertEquals("test", employeeUpdated.getFirstName());
    }


    @Test
    @Transactional
    public void persist_merge_flush() {
        Employee employee = new Employee("Jane", "Doe","jdoe@hotmail.com","Test");

        entityManager.persist(employee);

        employee.setAddress("Test2");
        entityManager.merge(employee);
        entityManager.flush();
    }
}
