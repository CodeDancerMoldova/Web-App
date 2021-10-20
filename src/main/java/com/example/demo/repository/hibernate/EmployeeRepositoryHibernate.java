package com.example.demo.repository.hibernate;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Profile("hibernate")
@Repository
public class EmployeeRepositoryHibernate implements EmployeeRepository {

    private EntityManager entityManager;

    @Autowired
    public EmployeeRepositoryHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Employee> theQuery = currentSession.createQuery("from Employee");
        List<Employee> employees = theQuery.getResultList();
        return employees;
    }

    @Override
    public Optional<Employee> findEmployeeByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Employee save(Employee theEmployee) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(theEmployee);
        return theEmployee;
    }

    public void update(Long id, Employee theEmployee) {
        Session currentSession = entityManager.unwrap(Session.class);
        Employee employee = currentSession.get(Employee.class, id);
        employee.setPhoneNumber(theEmployee.getPhoneNumber());
        employee.setDepartmentId(theEmployee.getDepartmentId());
        employee.setEmail(theEmployee.getEmail());
        employee.setLastName(theEmployee.getLastName());
        employee.setFirstName(theEmployee.getFirstName());
        employee.setSalary(theEmployee.getSalary());
    }

    @Override
    public Optional<Employee> findById(Long theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Employee theEmployee = currentSession.get(Employee.class, theId);
        return Optional.ofNullable(theEmployee);
    }

    @Override
    public void deleteById(Long theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Employee theEmployee = currentSession.get(Employee.class, theId);
        currentSession.delete(theEmployee);
    }
}
