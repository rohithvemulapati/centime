package com.centime.data_access.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centime.data_access.dto.HierarchyResponse;
import com.centime.data_access.service.DataAccessService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DataAccessController {

	@Autowired
	private DataAccessService dataAccessservice;

    @GetMapping(value = "/hierarchy/list", produces = "application/json")
    public ResponseEntity<List<HierarchyResponse>> getFullHierarchy()throws Exception {
    	
		try {
			log.info("Fetching full hierarchy...");
			List<HierarchyResponse> hierarchyLs = dataAccessservice.getAllHierarchy();
			return ResponseEntity.ok(hierarchyLs);
			
		} catch (Exception e) {
			log.error("Error fetching full hierarchy: {}", e.getMessage());
			throw new Exception(e);
		}
    }

    @GetMapping(value = "/hierarchy/{id}", produces = "application/json")
    public ResponseEntity<HierarchyResponse> getHierarchyById(@PathVariable("id") int id) throws Exception {
    	try {
			log.info("Fetching hierarchy for ID: {}", id);
			HierarchyResponse node = dataAccessservice.getHierarchyById(id);
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			log.error("Error fetching hierarchy for ID {}: {}", id, e.getMessage());
			throw new Exception(e);
		}
    }
    
}
