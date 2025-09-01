package com.centime.data_access.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(value = {"id", "parentId"})
@AllArgsConstructor
public class HierarchyResponse {
	
	private int id;
    private String Name;
    private int parentId;
    private List<HierarchyResponse> SubClasses;

    public HierarchyResponse(String name) {
        this.Name = name;
        this.SubClasses = new ArrayList<>();
    }

}
