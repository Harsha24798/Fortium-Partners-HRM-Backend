package icet.hrm.Fortium.Partners.service.impl;

import icet.hrm.Fortium.Partners.entity.Employee;
import icet.hrm.Fortium.Partners.model.DepartmentDto;
import icet.hrm.Fortium.Partners.model.EmployeeDto;
import icet.hrm.Fortium.Partners.repository.EmployeeRepository;
import icet.hrm.Fortium.Partners.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createEmployee(EmployeeDto employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email already exists!");
        }

        Employee employeeEntity = modelMapper.map(employee, Employee.class);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());

        Employee savedEmployee = employeeRepository.save(employeeEntity);

        return ResponseEntity.ok(modelMapper.map(savedEmployee, EmployeeDto.class));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();
    }

    @Override
    public ResponseEntity<?> updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Employee not found!! ID : " + id);
        }

        Employee existing = optionalEmployee.get();

        if (!existing.getEmail().equals(employeeDto.getEmail()) &&
                employeeRepository.existsByEmail(employeeDto.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email already exists!");
        }

        existing.setName(employeeDto.getName());
        existing.setEmail(employeeDto.getEmail());
        existing.setDepartment(employeeDto.getDepartment());
        existing.setUpdatedAt(LocalDateTime.now());

        Employee savedEmployee = employeeRepository.save(existing);

        return ResponseEntity.ok(modelMapper.map(savedEmployee, EmployeeDto.class));
    }

    @Override
    public ResponseEntity<?> deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Error: Employee not found! ID: " + id);
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Employee deleted successfully!");
    }

    @Override
    public ResponseEntity<?> getDepartmentById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Employee not found!");
        }

        DepartmentDto dto = modelMapper.map(optional.get(), DepartmentDto.class);
        return ResponseEntity.ok(dto);
    }
}
