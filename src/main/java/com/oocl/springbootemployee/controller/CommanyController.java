package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.repository.CommanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CommanyController {
    private final CommanyRepository commanyRepository;

    public CommanyController(CommanyRepository commanyRepository) {
        this.commanyRepository = commanyRepository;
    }

    @GetMapping
    public List<Company> getAll() {
        return commanyRepository.getAll();
    }
}
