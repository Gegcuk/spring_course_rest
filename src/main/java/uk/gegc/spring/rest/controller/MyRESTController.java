package uk.gegc.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.spring.rest.entity.Employee;
import uk.gegc.spring.rest.exceptionHandling.EmployeeIncorrectData;
import uk.gegc.spring.rest.exceptionHandling.NoSuchEmployeeException;
import uk.gegc.spring.rest.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRESTController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id){
        Employee employee = employeeService.getEmployee(id);

        if(employee == null) {
            throw new NoSuchEmployeeException("There is no employee with id = " + id + " in database");
        }

    return employee;
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(NoSuchEmployeeException exception){
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(Exception exception){
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());


        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

}


