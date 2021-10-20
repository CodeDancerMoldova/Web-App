package com.example.demo.repository.jdbc;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class EmployeeDAOImpl implements EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Employee> rowMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setSalary(rs.getLong("salary"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setDepartmentId(rs.getLong("department_id"));
        employee.setPhoneNumber(rs.getString("phone_number"));
        return employee;
    };

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, rowMapper);
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
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;
        try {
            employee = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            return Optional.ofNullable(employee);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public Employee update(Long id, Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ? , email = ?, " +
                "phone_number = ?, salary = ?, department_id = ? where ID = ?";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                employee.getPhoneNumber(), employee.getSalary(), employee.getDepartmentId(), id);

        return employee;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE EMPLOYEES WHERE ID = ?", id);
    }

    @Override
    public Employee save(Employee employee) {
        if (findById(employee.getId()).isPresent()) {
            return update(employee.getId(), employee);
        }
        String sql = "INSERT INTO employees(id, first_name, last_name, email, phone_number, salary, department_id) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, employee.getId(), employee.getFirstName(),
                employee.getLastName(), employee.getEmail(), employee.getPhoneNumber(),
                employee.getSalary(), employee.getDepartmentId());
        return employee;
    }
}
