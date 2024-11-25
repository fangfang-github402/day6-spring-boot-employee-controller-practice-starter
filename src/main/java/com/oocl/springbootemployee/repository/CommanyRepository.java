package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public CommanyRepository() {
        companies.add(new Company(1, "OOCL"));
        companies.add(new Company(2, "ThoughtWorks"));
    }

    public List<Company> getAll() {
        return companies;
    }
}
