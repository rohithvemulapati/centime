package com.centime.data_access.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centime.data_access.dto.HierarchyNode;

@Repository
public interface DataAccessRepository extends JpaRepository<HierarchyNode, Integer> {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//    public List<HierarchyNode> findAllCharacters() {
//        String sql = "SELECT id, parent_id, name, color FROM centime.hierarchy"; // your table name
//        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToCharacter(rs));
//    }
//
//    private HierarchyNode mapRowToCharacter(ResultSet rs) throws SQLException {
//    	return HierarchyNode.builder()
//    		    .id(rs.getInt("id"))
//    		    .parentId(rs.getInt("parent_id"))
//    		    .name(rs.getString("name"))
//    		    .color(rs.getString("color"))
//    		    .build();
//    }
	
	List<HierarchyNode> findAll();
}
