package com.centime.data_access.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.centime.data_access.dto.HierarchyNode;
import com.centime.data_access.dto.HierarchyResponse;
import com.centime.data_access.repository.DataAccessRepository;

@ExtendWith(MockitoExtension.class)
class DataAccessServiceImplTest {

    @InjectMocks
    private DataAccessServiceImpl dataAccessService;

    @Mock
    private DataAccessRepository dataAccessRepo;

    @Test
	void testGetAllHierarchy_success() {

    	HierarchyNode root = new HierarchyNode(1, 0, "Root", "green", new ArrayList<>());
		HierarchyNode child = new HierarchyNode(2, 1, "Child", "blue", new ArrayList<>());
		List<HierarchyNode> nodes = List.of(root, child);

		when(dataAccessRepo.findAll()).thenReturn(nodes);

		List<HierarchyResponse> result = dataAccessService.getAllHierarchy();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Root", result.get(0).getName());
		assertEquals(1, result.get(0).getSubClasses().size());
		assertEquals("Child", result.get(0).getSubClasses().get(0).getName());
	}

    @Test
    void testGetHierarchyById_success() {

    	HierarchyNode root = new HierarchyNode(1, 0, "Root", "green", new ArrayList<>());
        HierarchyNode child = new HierarchyNode(2, 1, "Child", "blue", new ArrayList<>());
        List<HierarchyNode> nodes = List.of(root, child);

        when(dataAccessRepo.findAll()).thenReturn(nodes);

        HierarchyResponse result = dataAccessService.getHierarchyById(1);

        assertNotNull(result);
        assertEquals("Root", result.getName());
        assertEquals(1, result.getSubClasses().size());
        assertEquals("Child", result.getSubClasses().get(0).getName());
    }

    @Test
    void testGetAllHierarchy_whenExceptionThrown() {
        when(dataAccessRepo.findAll()).thenThrow(new RuntimeException("DB Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dataAccessService.getAllHierarchy();
        });

        assertEquals("DB Error", exception.getMessage());
    }

    @Test
    void testGetHierarchyById_whenExceptionThrown() {
        when(dataAccessRepo.findAll()).thenThrow(new RuntimeException("DB Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dataAccessService.getHierarchyById(1);
        });

        assertEquals("DB Error", exception.getMessage());
    }
}
