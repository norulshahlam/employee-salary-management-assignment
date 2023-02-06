package com.shah.employeesalarymanagementassignment.repository;

import com.shah.employeesalarymanagementassignment.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author NORUL
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
    Employee findDistinctByLogin(String login);

    static Specification<Employee> salaryGreaterThan(double min) {
        return (root, query, builder) -> builder.gt(
                root.get("salary"), min);
    }

    static Specification<Employee> salaryLessThanOrEqualTo(double max) {
        return ((root, query, builder) -> builder.lessThanOrEqualTo(
                root.get("salary"), max));
    }
}
