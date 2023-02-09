package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Cacheable("employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Cacheable("employee")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    @Caching(evict = {
        @CacheEvict(value="employees",allEntries = true),
        @CacheEvict(value="employee",allEntries = true)
    })
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @Caching(evict = {
        @CacheEvict(value="employees",allEntries = true),
        @CacheEvict(value="employee",allEntries = true)
    })
    public void deleteEmployee(Long employeeId){
        // walidate if the employee to be deleted exist
        if(!employeeRepository.existsById(employeeId)){
            // will need to throw exception
            log.error("The employee doesn't exist");
            return;
        }
        employeeRepository.deleteById(employeeId);
        log.debug("Employee Deleted Successfully with ID:"+employeeId);
    }

    @Caching(evict = {
        @CacheEvict(value="employees",allEntries = true),
        @CacheEvict(value="employee",allEntries = true)
    })
    public Employee updateEmployee(Employee employee, Long employeeId) {
        Employee employeeResult = new Employee();
        // walidate if the employee to be updated exist
        if(!employeeRepository.existsById(employeeId)){
            // will need to throw exception
            log.error("The employee doesn't exist");
        }else{
            employeeResult=employeeRepository.save(employee);
            log.debug("Employee Saved Successfully with ID:"+employeeResult.getId());
        }
        return employeeResult;
    }
}