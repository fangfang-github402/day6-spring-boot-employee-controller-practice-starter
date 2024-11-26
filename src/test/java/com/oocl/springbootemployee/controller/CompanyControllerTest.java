package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class CompanyControllerTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private CompanyRepository commanyRepository;
    @Autowired
    private JacksonTester<List<Company>> listJson;

    @Test
    void should_return_all_companies_when_get_all_given_companies() throws Exception {
        //Given
        final List<Company> givenCompanies = commanyRepository.getAll();
        //When
        //Then
        String companiesJSONString = client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andReturn().getResponse().getContentAsString();

        List<Company> companyList = listJson.parseObject(companiesJSONString);
        assertThat(companyList)
                .usingRecursiveComparison()
                .isEqualTo(givenCompanies);
    }

}
