package com.in28minutes.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.in28minutes.data.api.TodoService;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.atLeast;
import static org.mockito.BDDMockito.then;
import static org.hamcrest.CoreMatchers.is;

public class TodoBusinessImplMockTest {
	// What is mocking
	// mocking is creating objects that simulates the behavior of real objects
	// Unlike stubs, mocks can be dinamically created from code - at runtime.
	// Mocks offfer more functionallity than stubbing.
	// You can verify method calls and a lot of other things.
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMock() {
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
	
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMockWithEmptyList() {
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList();
		
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
	
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		assertEquals(0, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingBDD() {
		// Given
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		// When
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		// Then
		assertThat(filteredTodos.size(), is(2));
	}
	
	@Test
	public void testDeleteTodosNotRelatedToSpring_usingBDD() {
		// Given
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		// When
		todoBusinessImpl
			.deleteTodosNotRelatedToSpring("Dummy");
		
		// Then
		// Este checa si se llamo al metodo deleteTodo con "Learn to Dance" no importa cuantas veces
		verify(todoServiceMock).deleteTodo("Learn to Dance");
		
		// Este checa cuantas veces se llamo al metodo deleteTodo con "Learn to Dance"
		verify(todoServiceMock, times(1)).deleteTodo("Learn to Dance");

		// Este checa cuantas veces al menos se llamo al metodo deleteTodo con "Learn to Dance"
		verify(todoServiceMock, atLeast(1)).deleteTodo("Learn to Dance");
		
		verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");
		
		verify(todoServiceMock, never()).deleteTodo("Learn Spring");
	}

	@Test
	public void testDeleteTodosNotRelatedToSpring_usingBDD_v2() {
		// Given
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		// When
		todoBusinessImpl
			.deleteTodosNotRelatedToSpring("Dummy");
		
		// Then
		//verify(todoServiceMock).deleteTodo("Learn to Dance");
		then(todoServiceMock).should().deleteTodo("Learn to Dance");
		
		//verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");
		then(todoServiceMock).should(never()).deleteTodo("Learn Spring MVC");
		
		//verify(todoServiceMock, never()).deleteTodo("Learn Spring");
		then(todoServiceMock).should(never()).deleteTodo("Learn Spring");
	}
	
	@Test
	public void testDeleteTodosNotRelatedToSpring_usingBDD_argumentCapture() {
		
		// Declare Argument Capture
		ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
		
		// Define Argument captor on specific method call
		// Capture the argument
		
		// Given
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		// When
		todoBusinessImpl
			.deleteTodosNotRelatedToSpring("Dummy");
		
		// Then
		then(todoServiceMock).should().deleteTodo(stringArgumentCaptor.capture());
		
		assertThat(stringArgumentCaptor.getValue(), is("Learn to Dance"));

	}
	
	@Test
	public void testDeleteTodosNotRelatedToSpring_usingBDD_argumentCaptureMultipleTimes() {
		
		// Declare Argument Capture
		ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
		
		// Define Argument captor on specific method call
		// Capture the argument
		
		// Given
		TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn to Rock and Roll", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		// When
		todoBusinessImpl
			.deleteTodosNotRelatedToSpring("Dummy");
		
		// Then
		then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());
		
		// Si se cambia "Learn Spring MVC" a "Learn to Rock and Roll" entonces
		// sucederan dos llamados al metodo delete y esta linea marcara error, ya
		// que solo espera una llamada
		//assertThat(stringArgumentCaptor.getValue(), is("Learn to Dance"));
		// Para solucionar lo anterior se cambia el getValue() por getAllValues() 
		// y arriba se cambia el el should() por el should(times(2))
		// El test prueba solo "Learn to Dance" para hacer un test que funcione
		// cambiamos esta linea
		//assertThat(stringArgumentCaptor.getAllValues(), is("Learn to Dance"));
		// por esta otra
		assertThat(stringArgumentCaptor.getAllValues().size(), is(2));

	}
}
