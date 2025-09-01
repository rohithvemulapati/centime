package com.centime.data_access.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centime.data_access.annotations.LogMethodParam;
import com.centime.data_access.dto.HierarchyNode;
import com.centime.data_access.dto.HierarchyResponse;
import com.centime.data_access.repository.DataAccessRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataAccessServiceImpl implements DataAccessService {

	@Autowired
	private DataAccessRepository dataAccessRepo;

	@Override
	@LogMethodParam
	public List<HierarchyResponse> getAllHierarchy() {
		List<HierarchyNode> allNodes = dataAccessRepo.findAll();
		List<HierarchyResponse> response = allNodes.stream().map(this::convertToResponse).collect(Collectors.toList());

		List<HierarchyResponse> roots;
		try {
			log.info("Fetched {} nodes from the database", response.size());
			Map<Integer, HierarchyResponse> map = new HashMap<>();
			for (HierarchyResponse node : response) {
				map.put(node.getId(), node);
			}

			roots = new ArrayList<>();

			for (HierarchyResponse node : response) {
				if (node.getParentId() == 0) {
					roots.add(node);
				} else {
					HierarchyResponse parent = map.get(node.getParentId());
					if (parent != null) {
						if (parent.getSubClasses() == null) {
							parent.setSubClasses(new ArrayList<>());
						}
						parent.getSubClasses().add(node);
					}
				}
			}
			log.info("Constructed hierarchy with {} root nodes", roots.size());
		} catch (Exception e) {
			log.error("Error constructing hierarchy: {}", e.getMessage());
			throw e;
		}

//		List<HierarchyResponse> response = roots.stream().map(this::convertToResponse).collect(Collectors.toList());
		return roots;
	}

	@Override
	@LogMethodParam
	public HierarchyResponse getHierarchyById(int id) {
		Map<Integer, HierarchyResponse> map;
		try {
			List<HierarchyNode> allNodes = dataAccessRepo.findAll();
			List<HierarchyResponse> response = allNodes.stream().map(this::convertToResponse).collect(Collectors.toList());

			map = new HashMap<>();
			for (HierarchyResponse node : response) {
				map.put(node.getId(), node);
			}
			for (HierarchyResponse node : response) {
				if (node.getParentId() != 0) {
					HierarchyResponse parent = map.get(node.getParentId());
					if (parent != null) {
						if (parent.getSubClasses() == null) {
							parent.setSubClasses(new ArrayList<>());
						}
						parent.getSubClasses().add(node);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error constructing hierarchy by Id: {}", e.getMessage());
			throw e;
		}
		return map.get(id);
	}

	@LogMethodParam
	public HierarchyResponse convertToResponse(HierarchyNode node) {
		HierarchyResponse response = new HierarchyResponse(node.getId(), node.getName(), node.getParentId(),
				new ArrayList<>());
		return response;
	}
}
