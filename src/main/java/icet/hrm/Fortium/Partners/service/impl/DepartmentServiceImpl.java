package icet.hrm.Fortium.Partners.service.impl;

import icet.hrm.Fortium.Partners.entity.Department;
import icet.hrm.Fortium.Partners.model.DepartmentDto;
import icet.hrm.Fortium.Partners.repository.DepartmentRepository;
import icet.hrm.Fortium.Partners.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> createDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsByName(departmentDto.getName())) {
            return ResponseEntity.badRequest().body("Error: Department name already exists!");
        }

        Department department = modelMapper.map(departmentDto, Department.class);
        Department saved = departmentRepository.save(department);
        return ResponseEntity.ok(modelMapper.map(saved, DepartmentDto.class));
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class)).toList();
    }

    @Override
    public ResponseEntity<?> getDepartmentById(Long id) {
        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Department not found!! ");
        }

        DepartmentDto dto = modelMapper.map(optional.get(), DepartmentDto.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> updateDepartment(Long id, DepartmentDto departmentDto) {
        return departmentRepository.findById(id)
                .map(existing -> {
                    if (!existing.getName().equals(departmentDto.getName()) &&
                            departmentRepository.existsByName(departmentDto.getName())) {
                        return ResponseEntity.badRequest().body("Error: Department name already exists!");
                    }

                    existing.setName(departmentDto.getName());
                    existing.setDescription(departmentDto.getDescription());
                    Department updated = departmentRepository.save(existing);
                    return ResponseEntity.ok(modelMapper.map(updated, DepartmentDto.class));
                })
                .orElse(ResponseEntity.badRequest().body("Error: Department not found!"));
    }

    @Override
    public ResponseEntity<?> deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Error: Department not found!");
        }
        departmentRepository.deleteById(id);
        return ResponseEntity.ok("Department deleted successfully.");
    }
}
