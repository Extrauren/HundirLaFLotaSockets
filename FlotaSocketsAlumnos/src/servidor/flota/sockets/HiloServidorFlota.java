package servidor.flota.sockets;


import java.io.IOException;
import java.net.SocketException;

import partida.flota.sockets.*;
import comun.flota.sockets.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del juego Hundir la flota.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

// Revisar el apartado 5.5. del libro de Liu

class HiloServidorFlota implements Runnable {
	MyStreamSocket myDataSocket;
	private Partida partida = null;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 */
	HiloServidorFlota(MyStreamSocket myDataSocket) {
		// Por implementar
		this.myDataSocket=myDataSocket;

	}

	/**
	 * Gestiona una sesion con un cliente	
	 */
	public void run( ) {
		boolean done = false;
		int operacion = 0;

		//
		try {
			while (!done) {
				// Recibe una peticion del cliente
				// Extrae la operación y los argumentos
				String [] dato=myDataSocket.receiveMessage().split("#");
				operacion=Integer.parseInt(dato[0]);

				switch (operacion) {
				case 0:  // fin de conexión con el cliente
					done=true;
					partida=null;
					myDataSocket.close();
					break;

				case 1: { // Crea nueva partida
					int numFila = Integer.parseInt(dato[1]);
					int numCol = Integer.parseInt(dato[2]);
					int numBarcos = Integer.parseInt(dato[3]);
					partida= new Partida(numFila, numCol, numBarcos);          
					break;
				}             
				case 2: { // Prueba una casilla y devuelve el resultado al cliente
					// ... 
					int Fila = Integer.parseInt(dato[1]);
					int Col = Integer.parseInt(dato[2]);
					myDataSocket.sendMessage(partida.pruebaCasilla(Fila, Col)+ "");
					break;
				}
				case 3: { // Obtiene los datos de un barco y se los devuelve al cliente
					// ... 
					int idBarco = Integer.parseInt(dato[1]);
					myDataSocket.sendMessage(partida.getBarco(idBarco));
					break;
				}
				case 4: { // Devuelve al cliente la solucion en forma de vector de cadenas
					// Primero envia el numero de barcos 
					// Despues envia una cadena por cada barco
					String [] vectorbarcos = partida.getSolucion();
					myDataSocket.sendMessage(String.valueOf(vectorbarcos.length+ ""));
					for(String i : vectorbarcos) {
						myDataSocket.sendMessage(i);
					}
					break;
				}
				} // fin switch
			} // fin while   
		} // fin try
		catch (Exception ex) {
			System.out.println("Exception caught in thread: " + ex);
		} // fin catch
	} //fin run

} //fin class 
