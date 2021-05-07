package com.example.jpademo.repository;

import com.example.jpademo.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EmployeeRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EntityManager entityManager;

    public EmployeeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Employee> findAllEmployees() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    public Employee findById(Long id) {
        Employee employee =  entityManager.find(Employee.class, id);

        logger.info("Employee -> {}" , employee);

        return employee;
    }

    public void insert() {
        Employee employee = new Employee("John", "Doe","jdoe@hotmail.com","Test");

        entityManager.persist(employee);

        logger.info("New employee -> {}", employee);
    }

    public void update() {
        Employee employee = findById(1L);
        employee.setFirstName("Jane");

        entityManager.merge(employee);

        logger.info("Updated employee -> {}", employee);
    }

    public void delete() {
        Employee employee = findById(1L);

        entityManager.remove(employee);
    }
}