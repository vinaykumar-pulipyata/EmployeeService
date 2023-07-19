package com.microservice.EmployeeService.feignclient;


import com.microservice.EmployeeService.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "addressservice", url = "http://localhost:8081", path = "/address-app/api/")
public interface AddressClient {
    @GetMapping("/address/{employeeId}")
    AddressResponse getAddressByEmployeeId(@PathVariable("employeeId") int employeeId);
}
