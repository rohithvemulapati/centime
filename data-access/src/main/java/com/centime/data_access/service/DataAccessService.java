package com.centime.data_access.service;

import java.util.List;

import com.centime.data_access.dto.HierarchyNode;
import com.centime.data_access.dto.HierarchyResponse;

public interface DataAccessService {

    public List<HierarchyResponse> getAllHierarchy();

    public HierarchyResponse getHierarchyById(int id) ;
}
