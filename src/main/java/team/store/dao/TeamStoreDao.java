package team.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import team.store.entity.TeamStore;

public interface TeamStoreDao extends JpaRepository<TeamStore, Long> {

}