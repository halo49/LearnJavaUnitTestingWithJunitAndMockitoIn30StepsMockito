package com.in28minutes.mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpyTest {

	@Test
	public void testWithMock() {
		 // mock crea un ArrayList falso
		List arrayListMock = mock(ArrayList.class);
		assertEquals(0, arrayListMock.size());
		// mocks return default value
		when(arrayListMock.size()).thenReturn(5);
		arrayListMock.add("Dummy"); // Esto no afecta al size de abajo porque es un mock
		assertEquals(5, arrayListMock.size());
	}
	
	
	// OJO: Recomiendan evitar el uso de spy en los proyectos
	@Test
	public void testWithSpy() {
		// spy crea un ArrayList REal con todos sus metodos
		List arrayListSpy = spy(ArrayList.class);
		assertEquals(0, arrayListSpy.size());
		arrayListSpy.add("Dummy");
		// arrayListSpy si es real entonces si agregamos algo si afecta al siguiente resultado
		assertEquals(1, arrayListSpy.size());
		arrayListSpy.remove("Dummy"); // Tambien se ve afectado el metodo size con el metodo remove
		assertEquals(0, arrayListSpy.size());
	}
	
	@Test
	public void testWithSpyPartialMock() {
		// spy crea un ArrayList Real con todos sus metodos
		List arrayListSpy = spy(ArrayList.class);
		// Todos los metodos regresan el valor por default excepto el de size, 
		// este esta indicado con el valor 5 y por eso le llaman un partial mock
		when(arrayListSpy.size()).thenReturn(5);
		assertEquals(5, arrayListSpy.size());
	}
	
	@Test
	public void testWithSpyPartialMockVerifyTest() {
		// spy es un objeto en accion real
		List arrayListSpy = spy(ArrayList.class);
		arrayListSpy.add("Dummy");
		// Con esto se verifica que el metodo add fue llamado
		verify(arrayListSpy).add("Dummy");
		// Con esto se especifica que el metodo clear nunca fue llamado
		verify(arrayListSpy, never()).clear();
	}
}
