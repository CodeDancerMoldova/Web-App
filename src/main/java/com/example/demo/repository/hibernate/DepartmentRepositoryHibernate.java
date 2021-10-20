package com.example.demo.repository.hibernate;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Profile("hibernate")
@Repository
public class DepartmentRepositoryHibernate implements DepartmentRepository {
    private EntityManager entityManager;

    @Autowired
    public DepartmentRepositoryHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public List<Department> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Department> theQuery = currentSession.createQuery("from Department");
        List<Department> departments = theQuery.getResultList();
        return departments;
    }
    @Transactional
    @Override
    public Department save(Department theDepartment) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(theDepartment);
        return theDepartment;
    }

    @Transactional
    public Department update(Long id,Department theDepartment) {
        Session currentSession = entityManager.unwrap(Session.class);
        Department department = currentSession.get(Department.class,id);
        department.setName(theDepartment.getName());
        department.setLocation(theDepartment.getLocation());
        return department;
    }

    @Transactional
    @Override
    public Optional<Department> findById(Long theId) {

        Session currentSession = entityManager.unwrap(Session.class);
        Department theDepartment= currentSession.get(Department.class,theId);
        return Optional.ofNullable(theDepartment);
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Transactional
    @Override
    public void deleteById(Long theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Department theDepartment = currentSession.get(Department.class,theId);
        currentSession.delete(theDepartment);
    }
}