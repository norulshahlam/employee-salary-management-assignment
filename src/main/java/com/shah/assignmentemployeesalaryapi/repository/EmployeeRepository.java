package com.shah.assignmentemployeesalaryapi.repository;

import com.shah.assignmentemployeesalaryapi.entity.Employee;
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

    /**
     * Using Spring Jpa specification
     * https://reflectoring.io/spring-data-specifications/
     * https://wesome.org/spring-data-jpa-specifications
     * @param min
     * @return
     */
    static Specification<Employee> salaryGreaterThan(double min) {
        return (root, query, builder) -> builder.gt(root.get("salary"), min);
    }

    static Specification<Employee> salaryLessThanOrEqualTo(double max) {
        return ((root, query, builder) -> builder.lessThanOrEqualTo(root.get("salary"), max));
    }

    static Specification<Employee> limitAndOffset(int offset, int limit) {
        return (root, query, cb) -> {
            query.distinct(true);
            ((org.hibernate.query.Query<?>) query).setFirstResult(offset);
            ((org.hibernate.query.Query<?>) query).setMaxResults(limit);
            return cb.conjunction();
        };
    }
}
