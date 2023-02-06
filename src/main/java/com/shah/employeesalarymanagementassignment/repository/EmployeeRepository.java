package com.shah.employeesalarymanagementassignment.repository;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> , JpaSpecificationExecutor<Employee> {
    Employee findDistinctByLogin(String login);
    List<Employee> findBySalaryBetween(double min, double max);

    static Specification<Employee> salaryGreaterThan(double min) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.gt(
                root.get("salary"), min));
    }
    static Specification<Employee> salaryLessThanOrEqualTo(double max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
                root.get("salary"), max));
    }

}
