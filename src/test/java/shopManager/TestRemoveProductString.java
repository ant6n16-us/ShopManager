package shopManager;

import shopmanager.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import exceptions.NoEnoughStock;
import exceptions.NotInStock;
import model.Product;
import model.Order;
import persistency.OrderRepository;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Isabel Román, Jorge Jimenez
 * Clase para realizar los test al método TestRemoveProductString de la clase MyBagManager
 *
 */
@ExtendWith(MockitoExtension.class)
class TestRemoveProductString {

	//Creo los objetos sustitutos (representantes o mocks)
	//Son objetos contenidos en MyBagManager de los que aún no disponemos el código
	@Mock(serializable = true)
	private static Product producto1Mock= Mockito.mock(Product.class);
	@Mock(serializable = true)
	private static Product producto2Mock= Mockito.mock(Product.class);
	@Mock
	private static StockManager stockMock= Mockito.mock(StockManager.class);
	@Mock 
	private static OrderRepository repositoryMock= Mockito.mock(OrderRepository.class);
	@Mock
	private static Order orderMock=Mockito.mock(Order.class);
	
	//Inyección de dependencias
	//Los objetos contenidos en micestaTesteada son reemplazados automáticamente por los sustitutos (mocks)
	@InjectMocks
	private static MyBagManager micestaTesteada;

	
	//Servirán para conocer el argumento con el que se ha invocado algún método de alguno de los mocks (sustitutos o representantes)
	//ArgumentCaptor es un genérico, indico al declararlo el tipo del argumento que quiero capturar
	@Captor
	private ArgumentCaptor<Integer> intCaptor;
	@Captor
	private ArgumentCaptor<Product> productCaptor;


	/**
	 * @see BeforeEach {@link org.junit.jupiter.api.BeforeEach}
	 */
	@BeforeEach
	void setUpBeforeClass(){
		//Todos los tests empiezan con la bolsa vacía
		
		   micestaTesteada.reset();
	}
	
	/**
	 * Test method for {@link shopmanager.MyBagManager#removeProduct(java.lang.String)}.
	 */
	@Test
	@Tag("unidad")
	@DisplayName("Prueba del método que elimina un producto")
	void testRemoveProductString() throws NoEnoughStock, NotInStock {
		
		// Añado 1 producto del prodcuto id1
		Mockito.when(producto1Mock.getId()).thenReturn("id1");
		Mockito.when(producto1Mock.getNumber()).thenReturn(1);
		// Añado 2 producto del prodcuto id2
		Mockito.when(producto2Mock.getId()).thenReturn("id2");
		Mockito.when(producto2Mock.getNumber()).thenReturn(2);
		
		// Meto en la cesta 1 producto 1 y 2 productos 2
		micestaTesteada.addProduct(producto1Mock);
		micestaTesteada.addProduct(producto2Mock);
		
		// Elimino todo el producto id1 a partir de sus String
		micestaTesteada.removeProduct(producto1Mock.getId());
		
		// Compruebo que ya no esta en la cesta el producto id1
		assertTrue(micestaTesteada.findProduct("id1").isEmpty());
		
		// Elimino todo el producto id2 a partir de sus String
		micestaTesteada.removeProduct(producto2Mock.getId());
				
		// Compruebo que ya no esta en la cesta el producto id1
		assertTrue(micestaTesteada.findProduct("id2").isEmpty());

		// Caso de borrar un producto con una id que no está en la cesta
		try {
			
			// Intento borrar el producto con id23
			micestaTesteada.removeProduct("id3");
	    	
			//Salta xcepción así que no debe llegar aquí		    	
	    	fail("No salta la excepcion NotInStock stock");
		    }catch(NotInStock e){
		    	assertEquals("El producto con id id3 no existe en el Stock",e.getMessage(),"El mensaje de la excepci�n no es correcto");
		    }   
		
	}
	
}
