package com.centime.data_access.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centime.data_access.dto.HierarchyNode;

@Repository
public interface DataAccessRepository extends JpaRepository<HierarchyNode, Integer> {

	List<HierarchyNode> findAll();
}
