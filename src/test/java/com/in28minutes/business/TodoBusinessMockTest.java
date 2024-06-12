package com.in28minutes.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.api.TodoServiceStub;

public class TodoBusinessMockTest {
	// What is mocking
	// mocking is creating objects that simulates the behavior of real objects
	// Unlike stubs, mocks can be dinamically created from code - at runtime.
	// Mocks offfer more functionallity than stubbing.
	// You can verify method calls and a lot of other things.
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMock() {
		TodoService mockTodoService = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		
		when(mockTodoService.retrieveTodos("Dummy")).thenReturn(todos);
	
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(mockTodoService);
		
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMockWithEmptyList() {
		TodoService mockTodoService = mock(TodoService.class);
		
		List<String> todos = Arrays.asList();
		
		when(mockTodoService.retrieveTodos("Dummy")).thenReturn(todos);
	
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(mockTodoService);
		
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		assertEquals(0, filteredTodos.size());
	}
	
}
