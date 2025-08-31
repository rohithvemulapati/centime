package com.centime.data_access.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HierarchyResponse {
    private String Name;
    private List<HierarchyResponse> SubClasses;

    public HierarchyResponse(String name) {
        this.Name = name;
        this.SubClasses = new ArrayList<>();
    }

}
