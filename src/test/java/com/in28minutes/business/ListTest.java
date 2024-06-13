package com.in28minutes.business;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

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
	
}
