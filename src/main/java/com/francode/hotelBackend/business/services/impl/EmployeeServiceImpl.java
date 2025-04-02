package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.mapper.EmployeeMapper;
import com.francode.hotelBackend.business.services.interfaces.EmployeeService;
import com.francode.hotelBackend.domain.entity.Company;
import com.francode.hotelBackend.domain.entity.ERole;
import com.francode.hotelBackend.domain.entity.Employee;
import com.francode.hotelBackend.domain.entity.UserApp;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.persistence.repository.JpaCompanyRepository;
import com.francode.hotelBackend.persistence.repository.JpaEmployeeRepository;
import com.francode.hotelBackend.persistence.repository.JpaUserRepository;
import com.francode.hotelBackend.presentation.dto.request.EmployeeRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeResponseDTO;
import com.francode.hotelBackend.presentation.dto.response.EmployeeStatisticsDTO;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.validator.internal.properties.Field;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final JpaEmployeeRepository employeeRepository;
    private final JpaUserRepository userAppRepository;
    private final JpaCompanyRepository companyRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(JpaEmployeeRepository employeeRepository,
                               JpaUserRepository userAppRepository,
                               JpaCompanyRepository companyRepository,
                               EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.userAppRepository = userAppRepository;
        this.companyRepository = companyRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Optional<EmployeeResponseDTO> findById(Long id) {
        if (id == null) {
            throw new ValidationException("El ID del empleado no puede ser nulo.");
        }

        return employeeRepository.findById(id)
                .map(employeeMapper::toResponseDTO)
                .or(() -> {
                    throw new NotFoundException("No se encontró un empleado con el ID: " + id);
                });
    }

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRequestDTO == null) {
            throw new ValidationException("La solicitud de creación de empleado no puede ser nula.");
        }

        UserApp userApp = userAppRepository.findById(employeeRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("No se encontró un usuario con el ID: " + employeeRequestDTO.getUserId()));

        Company company = companyRepository.findById(employeeRequestDTO.getCompanyId())
                .orElseThrow(() -> new NotFoundException("No se encontró una empresa con el ID: " + employeeRequestDTO.getCompanyId()));

        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        employee.setUserApp(userApp);
        employee.setCompany(company);

        return employeeMapper.toResponseDTO(employeeRepository.save(employee));
    }


    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO employeeRequestDTO) {
        if (id == null) {
            throw new ValidationException("El ID del empleado no puede ser nulo.");
        }

        if (employeeRequestDTO == null) {
            throw new ValidationException("La solicitud de actualización de empleado no puede ser nula.");
        }

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró un empleado con el ID: " + id));

        UserApp userApp = userAppRepository.findById(employeeRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("No se encontró un usuario con el ID: " + employeeRequestDTO.getUserId()));

        Company company = companyRepository.findById(employeeRequestDTO.getCompanyId())
                .orElseThrow(() -> new NotFoundException("No se encontró una empresa con el ID: " + employeeRequestDTO.getCompanyId()));

        employeeMapper.updateEntityFromDTO(employeeRequestDTO, existingEmployee);
        existingEmployee.setUserApp(userApp);
        existingEmployee.setCompany(company);

        return employeeMapper.toResponseDTO(employeeRepository.save(existingEmployee));
    }


    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("El ID del empleado no puede ser nulo.");
        }

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró un empleado con el ID: " + id));

        employeeRepository.delete(employee);
    }

    @Override
    public Page<EmployeeResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<Employee> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<Employee> employees = employeeRepository.findAll(spec, pageable);

        if (employees.isEmpty()) {
            throw new NoRecordsException("Todavía no hay registros disponibles.");
        }

        return employees.map(employeeMapper::toResponseDTO);
    }

    @Override
    public Optional<EmployeeResponseDTO> findByDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.isEmpty()) {
            throw new ValidationException("El número de documento no puede ser nulo o vacío.");
        }

        return employeeRepository.findByDocumentNumber(documentNumber)
                .map(employeeMapper::toResponseDTO)
                .or(() -> {
                    throw new NotFoundException("No se encontró un empleado con el número de documento: " + documentNumber);
                });
    }

    @Override
    public Optional<EmployeeResponseDTO> findByUserAppId(Long userId) {
        if (userId == null) {
            throw new ValidationException("El ID de usuario no puede ser nulo.");
        }

        return employeeRepository.findByUserAppId(userId)
                .map(employeeMapper::toResponseDTO)
                .or(() -> {
                    throw new NotFoundException("No se encontró un empleado asociado al usuario con ID: " + userId);
                });
    }

    @Override
    public Page<EmployeeResponseDTO> findByRole(ERole role, Pageable pageable) {
        Page<Employee> employees = employeeRepository.findByRole(role, pageable);

        if (employees.isEmpty()) {
            throw new NoRecordsException("No se encontraron empleados con el rol: " + role.name());
        }
        return employees.map(employeeMapper::toResponseDTO);
    }

    @Override
    public EmployeeStatisticsDTO findEmployeeStatistics(Long employeeId) {
        // Llamada al repositorio para obtener las estadísticas de un único empleado
        Object[] result = employeeRepository.findEmployeeStatisticsById(employeeId);

        // Verificamos si el resultado está vacío o no tiene 5 elementos (ID, nombre, y 3 estadísticas)
        if (result == null || result.length != 5) {
            throw new NotFoundException("Empleado no encontrado o sin estadísticas con ID: " + employeeId);
        }

        // Mapeo de los resultados al DTO correspondiente
        Long completedCleanings = (Long) result[2];
        Long canceledCleanings = (Long) result[3];
        Double avgCleaningDuration = (Double) result[4];

        // Retornar el DTO con los valores obtenidos
        return new EmployeeStatisticsDTO(
                (Long) result[0],   // employeeId
                (String) result[1], // employeeName
                completedCleanings,
                canceledCleanings,
                avgCleaningDuration
        );
    }


    @Override
    public Page<EmployeeStatisticsDTO> findTopEmployees(Pageable pageable) {
        // Llamada al repositorio sin el parámetro sortOrder
        List<Object[]> results = employeeRepository.findTopOrBottomEmployees(pageable);

        // Mapeo de los resultados a la lista de DTOs
        List<EmployeeStatisticsDTO> statisticsList = results.stream()
                .map(result -> new EmployeeStatisticsDTO(
                        (Long) result[0],  // employeeId
                        (String) result[1], // employeeName
                        (Long) result[2],   // completedCleanings
                        (Long) result[3],   // canceledCleanings (mapeo correcto)
                        (Double) result[4]  // avgCleaningDuration
                ))
                .collect(Collectors.toList());

        // Retorno de la lista paginada
        return new PageImpl<>(statisticsList, pageable, statisticsList.size());
    }

}
