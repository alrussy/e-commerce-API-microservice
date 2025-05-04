package product_app.service.impls.departmen_impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import product_app.mapper.DepartmentMapper;
import product_app.mapper.PageMapper;
import product_app.model.dto.PagedResult;
import product_app.model.dto.department_dto.DepartmentRequest;
import product_app.model.dto.department_dto.DepartmentResponse;
import product_app.model.entities.Department;
import product_app.model.entities.id.DepartmentId;
import product_app.repository.DepartmentRepository;
import product_app.service.BaseDepartmentService;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements BaseDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public PagedResult<DepartmentResponse> findAll(int pageNumber) {
        var page = PageRequest.of(pageNumber, 10);

        return new PageMapper<DepartmentResponse>()
                .toPageResponse(departmentRepository.findAll(page).map(brand -> DepartmentMapper.fromEntity(brand)));
    }

    @Override
    public List<DepartmentResponse> findByName(String name) {

        return departmentRepository.findByName(name).stream()
                .map(department -> DepartmentMapper.fromEntity(department))
                .toList();
    }

    @Override
    public DepartmentResponse save(DepartmentRequest departmentRequest) {
        return departmentRepository
                .save(DepartmentMapper.toEntity(departmentRequest))
                .mapToDepartmentResponseOutCategory();
    }

    @Override
    public List<DepartmentResponse> saveAll(List<DepartmentRequest> request) {
        return departmentRepository
                .saveAll(request.stream().map(DepartmentMapper::toEntity).toList())
                .stream()
                .map(Department::mapToDepartmentResponseOutCategory)
                .toList();
    }

    @Override
    public void deleteAll(List<Long> ids) {

        departmentRepository.deleteAllByIdInBatch(ids.stream()
                .map(e -> {
                    return departmentRepository
                            .findByIdDepartmentId(e)
                            .orElseThrow(() -> new EntityNotFoundException(e.toString()))
                            .getId();
                })
                .toList());
    }

    @Override
    public void delete(Long id) {

        departmentRepository.delete(findById(new DepartmentId(id, null)));
    }

    @Override
    public DepartmentResponse update(Long id, DepartmentRequest departmentRequest) {

        Department departmetResponseFind = departmentRepository
                .findByIdDepartmentId(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));

        if (departmentRequest.name() != null) {
            departmetResponseFind.setName(departmentRequest.name());
        }

        return departmentRepository.save(departmetResponseFind).mapToDepartmentResponseOutCategory();
    }

    @Override
    public DepartmentResponse findById(Long id) {

        return findById(new DepartmentId(id, null)).mapToDepartmentResponseOutCategory();
    }

    public Department findById(DepartmentId id) {
        Optional<Department> optinalDepartment;
        if (id.getCategoryId() == null) {
            optinalDepartment = departmentRepository.findByIdDepartmentId(id.getDepartmentId());
        } else {
            optinalDepartment = departmentRepository.findById(id);
        }
        return optinalDepartment.orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    @Override
    public Long count() {
        return departmentRepository.count();
    }
}
