package br.com.erudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}