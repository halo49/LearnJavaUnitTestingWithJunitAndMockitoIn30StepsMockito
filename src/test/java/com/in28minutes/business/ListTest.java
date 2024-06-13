package com.in28minutes.business;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.hamcrest.CoreMatchers.is;

public class ListTest {

	@Test
	public void letsMockListSizeMethod() {
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(2);
		assertEquals(2, listMock.size());
	}
	
	@Test
	public void letsMockListSize_ReturnMultipleValues() {
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(2).thenReturn(3);
	
		// Aqui leera el valor regresado 2
		assertEquals(2, listMock.size());
		// Aqui leera el valor regresado 3
		assertEquals(3, listMock.size());
	}

	@Test
	public void letsMockListGetMethodWithCeroAndOne() {
		List listMock = mock(List.class);
		// Argument Matcher
		when(listMock.get(0)).thenReturn("in28Minutes");
	
		assertEquals("in28Minutes", listMock.get(0));
		// Si no se especifico cero mockito regresa valores por default
		// en este caso null cuando se manda un 1 en el metodo get(1)
		assertEquals(null, listMock.get(1));
	}
	
	@Test
	public void letsMockListGetMethodWithAnyInt() {
		List listMock = mock(List.class);
		// Argument Matcher
		when(listMock.get(anyInt())).thenReturn("in28Minutes");
	
		assertEquals("in28Minutes", listMock.get(0));
		assertEquals("in28Minutes", listMock.get(1));
	}

	@Test(expected=RuntimeException.class)
	public void letsMockList_throwsException() {
		List listMock = mock(List.class);
		// Argument Matcher
		when(listMock.get(anyInt())).thenThrow(new RuntimeException("Runtime Exception"));
	
		listMock.get(0);
	}
	
	@Test(expected=RuntimeException.class)
	public void letsMockList_mixingUp() {
		List listMock = mock(List.class);
		// Argument Matcher
		// no se puede combinar un any con un numero estatico
		// porque todo se hara any
		when(listMock.subList(anyInt(), 5)).thenThrow(new RuntimeException("Runtime Exception"));
	
		listMock.subList(0, 9);
	}
	
	// What happens if an unstubbed method is called?
	// by default, for all methods that return a value, a mock will return either null, 
	// a a primitive/primitive wrapper value, or an empty collection, as appropriate. 
	// For example 0 for an int/Integer and false for a boolean/Boolean
	
	@Test
	public void letsMockListGet_usingBDD() {
		// Given
		List<String> listMock = mock(List.class);
		// Argument Matcher
		given(listMock.get(anyInt())).willReturn("in28Minutes");
		
		// When
		String firstElement = listMock.get(0);
		
		// Then
		assertThat(firstElement, is("in28Minutes"));
	}
	
}
