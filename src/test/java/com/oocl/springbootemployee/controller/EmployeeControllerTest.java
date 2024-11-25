package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JacksonTester<Employee> json;
    @Autowired
    private JacksonTester<List<Employee>> listJson;

    @BeforeEach
    public void resetRepo() {    employeeRepository.resetRepository();}

    @Test
    void should_return_employee_when_get_all_given_employees() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        //When
        //Then
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(givenEmployees);
    }

    @Test
    void should_return_male_employees_when_get_all_with_male_gender_given_employees() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        //When
        //Then
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employees").param("gender", "MALE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(givenEmployees);
    }

    @Test
    void should_create_employee_success() throws Exception {
        //Given
        String givenEmployee = "    {\n" +
                "        \"name\": \"Tom3\",\n" +
                "        \"age\": 18,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"salary\": 6000.0\n" +
                "    }";
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        List<Employee> newEmployeeList = employeeRepository.getAll();
        assertThat(newEmployeeList.size()).isEqualTo(4);

        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee.getId()).isPositive();
        assertThat(returnedEmployee.getName()).isEqualTo("Tom3");
        assertThat(returnedEmployee.getAge()).isEqualTo(18);
        assertThat(returnedEmployee.getGender()).isEqualTo(Gender.MALE);
        assertThat(returnedEmployee.getSalary()).isEqualTo(6000.0);

    }

    @Test
    void should_return_employee_when_get_by_id_given_employee_id() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        final Integer employeeId = givenEmployees.get(0).getId();
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee.getId()).isEqualTo(employeeId);
    }

    @Test
    void should_return_employee_when_update_age_and_salary_given_employee() throws Exception {
        //Given
        String givenEmployee = "{\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Tom3\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"salary\": 7000.0\n" +
                "    }";
//        Employee giveEmployee = new Employee(3, "Tom3", 20, Gender.MALE, 7000.0);
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee.getId()).isEqualTo(3);
        assertThat(returnedEmployee.getName()).isEqualTo("Tom3");
        assertThat(returnedEmployee.getAge()).isEqualTo(20);
        assertThat(returnedEmployee.getGender()).isEqualTo(Gender.MALE);
        assertThat(returnedEmployee.getSalary()).isEqualTo(7000.0);
    }
}