package com.in28minutes.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import com.in28minutes.data.api.TodoService;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.atLeast;
import static org.mockito.BDDMockito.then;
import static org.hamcrest.CoreMatchers.is;

// @RunWith es necesaria para poder utilizar la anotacion @Mock
// Al utilizar @Rule se puede quitar @RunWith que solo puede cargar una sola clase
// y con la @Rule se pueden utilizar varias clases
//@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplMockitoInjectMockTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Mock
	TodoService todoServiceMock;
	
	@InjectMocks
	TodoBusinessImpl todoBusinessImpl;
	// Con esta anotacion mockito injecta el @Mock que se use dentro de la implementacion de 
	// todoBusinessImpl y se puede eliminar completamente el siguiente codigo :
	// TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
	
	@Captor
	ArgumentCaptor<String> stringArgumentCaptor;
	// La anotacion @Captor en automatico nos crea un ArgumentCaptor y ya no sera necesaria esta implementacion
	//ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
	
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMock() {
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
	
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingAMockWithEmptyList() {
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList();
		
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
	
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		assertEquals(0, filteredTodos.size());
	}
	
	@Test
	public void testRetrieveTodosRelatedToSpring_usingBDD() {
		// Given
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
		// When
		List<String> filteredTodos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Dummy");
		
		// Then
		assertThat(filteredTodos.size(), is(2));
	}
	
	@Test
	public void testDeleteTodosNotRelatedToSpring_usingBDD() {
		// Given
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
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
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
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
		// Se elimina y es sustituido por @Captor al principo de la clase
		//ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
		
		// Define Argument captor on specific method call
		// Capture the argument
		
		// Given
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
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
		// Se elimina y es sustituido por @Captor al principo de la clase
		//ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
		
		// Define Argument captor on specific method call
		// Capture the argument
		
		// Given
		// Se quita esta linea porque el mock ya esta disponible con la notacion @Mock
		//TodoService todoServiceMock = mock(TodoService.class);
		
		List<String> todos = Arrays.asList("Learn to Rock and Roll", "Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
		
		// El @Mock sera inyectado dentro de todoBusinessImpl automaticamente sin tener que definirlo explicitamente
		//TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		
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
