package org.example.employeemanagement.repository;

import org.example.employeemanagement.entity.dto.DepartmentStatusDto;
import org.example.employeemanagement.entity.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);
    List<Employee> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    @Query("""
        select e
        from Employee e join Department d 
            on e.department.id = d.id
        where d.name like %:name%
    """)
    List<Employee> findByDepartmentName(@Param("name") String name);

    @Query("""
        select new org.example.employeemanagement.entity.dto.DepartmentStatusDto(e.department.name, COUNT(e.id))
        from Employee e
        group by e.department.name
        order by COUNT(e.id) desc 
    """)
    List<DepartmentStatusDto> findEmployeeCountByDepartment();
}
