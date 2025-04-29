package icet.hrm.Fortium.Partners.service;

import icet.hrm.Fortium.Partners.model.EmployeeDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface EmployeeService {

    ResponseEntity<?> createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployees();

    ResponseEntity<?> updateEmployee(Long id, EmployeeDto employeeDto);

    ResponseEntity<?> deleteEmployee(Long id);

    ResponseEntity<?> getDepartmentById(Long id);
}
