package net.tzvikaco.fruitshop.web.rest;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/management")
class ServiceInstanceResource {

    private DiscoveryClient discoveryClient;

    ServiceInstanceResource(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
        @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/services/all")
    public List<String> serviceNames() {
        return this.discoveryClient.getServices();
    }
}
