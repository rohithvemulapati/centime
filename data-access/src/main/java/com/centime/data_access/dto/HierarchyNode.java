package com.centime.data_access.dto;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "hierarchy", schema = "centime")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyNode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    private int id;
    private int parentId;
    private String name;
    private String color;
   
}

