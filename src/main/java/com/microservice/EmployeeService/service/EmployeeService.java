package com.microservice.EmployeeService.service;


import com.microservice.EmployeeService.response.EmployeeResponse;

public interface EmployeeService {

    public EmployeeResponse getEmployeeById(int id);
}
