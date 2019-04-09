package learn.controller;

import learn.monitoring.PositionError;
import learn.monitoring.PositionErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ErrorController {

    @Autowired
    private PositionErrorRepository repository;

    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public List<PositionError> getErrors() {
        return repository.findAll();
    }
}
