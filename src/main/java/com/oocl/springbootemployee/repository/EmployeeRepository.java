package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(1,"Tom1",18, Gender.MALE,5000));
        employees.add(new Employee(2,"Tom2",18,Gender.MALE,7000));
        employees.add(new Employee(3,"Tom3",18,Gender.MALE,6000));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(Integer id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElseThrow();
    }

    public List<Employee> getByGender(Gender gender) {
            return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }
}
