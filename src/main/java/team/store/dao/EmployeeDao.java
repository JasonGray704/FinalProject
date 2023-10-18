package team.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.store.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}