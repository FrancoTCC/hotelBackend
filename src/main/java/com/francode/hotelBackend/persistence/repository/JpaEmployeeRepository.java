package com.francode.hotelBackend.persistence.repository;

import com.francode.hotelBackend.domain.entity.CleaningStatus;
import com.francode.hotelBackend.domain.entity.ERole;
import com.francode.hotelBackend.domain.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByDocumentNumber(String documentNumber);
    Optional<Employee> findByUserAppId(Long userId);

    @Query("SELECT e FROM Employee e " +
            "JOIN e.userApp u " +
            "JOIN u.roles r " +
            "WHERE r.name = :role")
    Page<Employee> findByRole(ERole role, Pageable pageable);

    // Consulta para obtener las estadísticas de un empleado con limpiezas finalizadas, canceladas y promedio de duración
    @Query("SELECT e.id AS employeeId, " +
            " e.name AS employeeName, " +
            " SUM(CASE WHEN c.status = 'TERMINADO' THEN 1 ELSE 0 END) AS completedCleanings, " +
            " SUM(CASE WHEN c.status = 'CANCELADO' THEN 1 ELSE 0 END) AS canceledCleanings, " +
            " AVG(CASE WHEN c.endDate IS NOT NULL THEN TIMESTAMPDIFF(SECOND, c.startDate, c.endDate) ELSE 0 END) AS avgCleaningDuration " +
            "FROM Cleaning c " +
            "JOIN c.employee e " +
            "WHERE e.id = :employeeId " +
            "GROUP BY e.id")
    Object[] findEmployeeStatisticsById(@Param("employeeId") Long employeeId);

    // Consulta para obtener la lista de empleados con más limpiezas terminadas y mejores promedios de duración
    @Query("SELECT e.id AS employeeId, " +
            " e.name AS employeeName, " +
            " SUM(CASE WHEN c.status = 'TERMINADO' THEN 1 ELSE 0 END) AS completedCleanings, " +
            " AVG(CASE WHEN c.endDate IS NOT NULL THEN TIMESTAMPDIFF(SECOND, c.startDate, c.endDate) ELSE 0 END) AS avgCleaningDuration " +
            "FROM Cleaning c " +
            "JOIN c.employee e " +
            "GROUP BY e.id " +
            "ORDER BY " +
            "   SUM(CASE WHEN c.status = 'TERMINADO' THEN 1 ELSE 0 END) " +
            "   " + ":sortOrder, " +
            "   AVG(CASE WHEN c.endDate IS NOT NULL THEN TIMESTAMPDIFF(SECOND, c.startDate, c.endDate) ELSE 0 END) " +
            "   " + ":sortOrder")
    List<Object[]> findTopOrBottomEmployees(
            @Param("sortOrder") String sortOrder,
            Pageable pageable);

}
