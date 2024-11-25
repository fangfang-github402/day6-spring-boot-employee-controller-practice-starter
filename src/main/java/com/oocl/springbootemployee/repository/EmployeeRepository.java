package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(1,"Tom1",18, Gender.MALE,5000.0));
        employees.add(new Employee(2,"Tom2",18,Gender.MALE,7000.0));
        employees.add(new Employee(3,"Tom3",18,Gender.MALE,6000.0));
    }

    public void resetRepository() {
        employees.clear();
        employees.add(new Employee(1,"Tom1",18, Gender.MALE,5000.0));
        employees.add(new Employee(2,"Tom2",18,Gender.MALE,7000.0));
        employees.add(new Employee(3,"Tom3",18,Gender.MALE,6000.0));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(Integer id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Employee> getByGender(Gender gender) {
            return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployeeAgeAndSalary(Integer id, Employee employee) {
        Employee employeeToBeUpdated = getById(id);

        if (Objects.isNull(employeeToBeUpdated)) {
            employees.add(employee);
        }

        System.out.println(employeeToBeUpdated.toString());

        employeeToBeUpdated.setAge(employee.getAge());
        employeeToBeUpdated.setSalary(employee.getSalary());
        employees = employees.stream()
                .filter(employeeSearch -> Objects.equals(employeeSearch.getId(), employee.getId()))
                .map(employeeOriginal -> update(employeeOriginal, employeeToBeUpdated))
                .collect(Collectors.toList());
        return getById(employeeToBeUpdated.getId());
    }

    public Employee update(Employee employee, Employee updatedEmployee) {
        if (Objects.equals(employee.getId(), updatedEmployee.getId())) {
            employee.setSalary(updatedEmployee.getSalary());
            employee.setAge(updatedEmployee.getAge());
        }
        return employee;
    }
}
