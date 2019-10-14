package pl.bookworm.bookworm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.bookworm.bookworm.service.ServiceName;

@RestController
public class Controller {

    private ServiceName serviceName;

    @Autowired
    public Controller(ServiceName serviceName) {
        this.serviceName = serviceName;
    }
}
