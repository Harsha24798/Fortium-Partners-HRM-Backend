package icet.hrm.Fortium.Partners.service;

import icet.hrm.Fortium.Partners.model.DepartmentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<?> createDepartment(DepartmentDto departmentDto);
    List<DepartmentDto> getAllDepartments();
    ResponseEntity<?> getDepartmentById(Long id);
    ResponseEntity<?> updateDepartment(Long id, DepartmentDto departmentDto);
    ResponseEntity<?> deleteDepartment(Long id);
}
