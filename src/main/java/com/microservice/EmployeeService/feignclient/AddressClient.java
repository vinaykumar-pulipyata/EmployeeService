package com.microservice.EmployeeService.feignclient;


import com.microservice.EmployeeService.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//name --> eureka instance name,
// url = "http://localhost:8081" -> if url not given than automatic load balancing
@FeignClient(name = "ADDRESSSERVICE", path = "/address-app/api/")
public interface AddressClient {
    @GetMapping("/address/{employeeId}")
    AddressResponse getAddressByEmployeeId(@PathVariable("employeeId") int employeeId);
}
