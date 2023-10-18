package team.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.store.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}