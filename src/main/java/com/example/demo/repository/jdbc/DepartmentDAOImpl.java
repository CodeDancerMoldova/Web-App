package com.example.demo.repository.jdbc;


import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@Profile("jdbc")
public class DepartmentDAOImpl implements DepartmentRepository {

    private JdbcTemplate jdbcTemplate;

    public DepartmentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Department> rowMapper = (rs, rowNum) -> {
        Department department = new Department();
        department.setId(rs.getLong("id"));
        department.setLocation(rs.getString("location"));
        department.setName(rs.getString("name"));
        return department;
    };


    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM DEPARTMENT";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Department> findById(Long id) {
        String sql = "SELECT * FROM DEPARTMENT WHERE id = ?";
        Department department = null;
        try {
            department = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            return Optional.ofNullable(department);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }


    public Department update(Long id, Department department) {
        String sql = "UPDATE DEPARTMENT  SET LOCATION = ? , NAME = ? WHERE ID = ?";
        jdbcTemplate.update(sql, department.getLocation(), department.getName(), id);
        return department;
    }


    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE DEPARTMENT WHERE ID = ?", id);
    }


    @Override
    public Department save(Department department) {
        if (findById(department.getId()).isPresent()) {
            return update(department.getId(), department);
        } else {
            String sql = "INSERT INTO DEPARTMENT VALUES(?,?,?)";
            jdbcTemplate.update(sql, department.getId(), department.getLocation(), department.getName());
            return department;
        }
    }
}
