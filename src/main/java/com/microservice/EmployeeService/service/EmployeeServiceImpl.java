package com.microservice.EmployeeService.service;


import com.microservice.EmployeeService.entity.Employee;
import com.microservice.EmployeeService.feignclient.AddressClient;
import com.microservice.EmployeeService.repo.EmployeeRepository;
import com.microservice.EmployeeService.response.AddressResponse;
import com.microservice.EmployeeService.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressClient addressClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public EmployeeResponse getEmployeeById(int id) {

        Employee employee = employeeRepository.findById(id).get();

        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

        AddressResponse addressResponse = getRestTemplateForObject(id);

        //AddressResponse addressResponse = addressClient.getAddressByEmployeeId(id);
        employeeResponse.setAddressResponse(addressResponse);

        return employeeResponse;
    }

    private AddressResponse getRestTemplateForObject(int id) {

        //get details using discovery service
        /*List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("AddressService");
        ServiceInstance serviceInstance = serviceInstanceList.get(0);
        String uri = serviceInstance.getUri().toString();
        String url = uri.replace("VinayKumar","localhost");*/

        //load balancing
        ServiceInstance serviceInstance = loadBalancerClient.choose("AddressService");
        String uri = serviceInstance.getUri().toString();
        String configPath = serviceInstance.getMetadata().get("configPath");
        String url = uri.replace("VinayKumar","localhost");
        System.out.println("URL + configPath >>>>>>>>>> "+ url+configPath);
        return restTemplate.getForObject(url + configPath + "/address/{id}", AddressResponse.class, id);

        //inbuilt load balancing using annotation
        //return restTemplate.getForObject("http://ADDRESSSERVICE/address-app/api/address/{id}", AddressResponse.class, id);
    }
}