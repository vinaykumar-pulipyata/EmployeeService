package com.microservice.EmployeeService.service;


import com.microservice.EmployeeService.entity.Employee;
import com.microservice.EmployeeService.feignclient.AddressClient;
import com.microservice.EmployeeService.repo.EmployeeRepository;
import com.microservice.EmployeeService.response.AddressResponse;
import com.microservice.EmployeeService.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressClient addressClient;

    /*@Autowired
    private RestTemplate restTemplate;*/

    @Override
    public EmployeeResponse getEmployeeById(int id) {

        Employee employee = employeeRepository.findById(id).get();

        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

        //AddressResponse addressResponse = restTemplate.getForObject("http://localhost:8081/address-app/api/address/{id}", AddressResponse.class, id);

        AddressResponse addressResponse = addressClient.getAddressByEmployeeId(id);
        employeeResponse.setAddressResponse(addressResponse);

        return employeeResponse;
    }
}