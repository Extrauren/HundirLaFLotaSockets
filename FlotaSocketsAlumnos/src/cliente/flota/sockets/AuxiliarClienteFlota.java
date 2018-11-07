
package cliente.flota.sockets;
import java.net.*;
import java.io.*;

import comun.flota.sockets.*;

/**
 * Esta clase implementa el intercambio de mensajes
 * asociado a cada una de las operaciones basicas que comunican cliente y servidor
 */

public class AuxiliarClienteFlota {

	private MyStreamSocket mySocket;
	private InetAddress serverHost;
	private int serverPort;

	/**
	 * Constructor del objeto auxiliar del cliente
	 * Crea un socket de tipo 'MyStreamSocket' y establece una conexi贸n con el servidor
	 * 'hostName' en el puerto 'portNum'
	 * @param	hostName	nombre de la m谩quina que ejecuta el servidor
	 * @param	portNum		numero de puerto asociado al servicio en el servidor
	 */
	AuxiliarClienteFlota(String hostName,
			String portNum) throws SocketException,
	UnknownHostException, IOException {

		// Por implementar
		serverPort = Integer.parseInt(portNum);								
		serverHost = InetAddress.getByName(hostName);								//Obtenemos el Puerto, el host y creamos el socket
		mySocket = new MyStreamSocket(serverHost,serverPort);

	} // end constructor

	/**
	 * Usa el socket para enviar al servidor una petici贸n de fin de conexi贸n
	 * con el formato: "0"
	 * @throws	IOException
	 */
	public void fin( ) {
		// Por implementar
		try {
			mySocket.sendMessage("0");
			mySocket.close();													//Enviamos el mensaje 0 y cerramos el socket
		} catch (IOException e) {												//Obligatorio el try catch o da errores 
			e.printStackTrace();
		}	
	} // end fin 

	/**
	 * Usa el socket para enviar al servidor una peticion de creacion de nueva partida 
	 * con el formato: "1#nf#nc#nb"
	 * @param nf	numero de filas de la partida
	 * @param nc	numero de columnas de la partida
	 * @param nb	numero de barcos de la partida
	 * @throws IOException
	 */
	public void nuevaPartida(int nf, int nc, int nb)  throws IOException {
		// Por implementar

		mySocket.sendMessage("1#"+String.valueOf(nf)+"#"+String.valueOf(nc)+"#"+String.valueOf(nb));	//Envia mensaje al servidor con formato descrito arriba 

	} // end nuevaPartida

	/**
	 * Usa el socket para enviar al servidor una petici贸n de disparo sobre una casilla 
	 * con el formato: "2#f#c"
	 * @param f	fila de la casilla
	 * @param c	columna de la casilla
	 * @return	resultado del disparo devuelto por la operaci贸n correspondiente del objeto Partida
	 * 			en el servidor.
	 * @throws IOException
	 */
	public int pruebaCasilla(int f, int c) throws IOException {

		mySocket.sendMessage("2#"+String.valueOf(f)+"#"+String.valueOf(c));   	 //Envia mensaje al servidor con formato descrito arriba 

		return Integer.parseInt(mySocket.receiveMessage());  					//Recive el mesaje del servidor y lo devuleve con el valor que le habiamos pedido

	} // end getCasilla

	/**
	 * Usa el socket para enviar al servidor una peticion de los datos de un barco
	 * con el formato: "3#idBarco"
	 * @param idBarco	identidad del Barco
	 * @return			resultado devuelto por la operacion correspondiente del objeto Partida
	 * 					en el servidor.
	 * @throws IOException
	 */
	public String getBarco(int idBarco) throws IOException {

		mySocket.sendMessage("3#"+String.valueOf(idBarco));   					//Envia mensaje al servidor con formato descrito arriba 


		return mySocket.receiveMessage(); 										//Devolvemos el String que contiene el barco

	} // end getCasilla

	/**
	 * Usa el socket para enviar al servidor una petici贸n de los datos de todos los barcos
	 * con el formato: "4"
	 * @return	resultado devuelto por la operaci贸n correspondiente del objeto Partida
	 * 			en el servidor
	 * @throws IOException
	 */
	public String[] getSolucion() throws IOException {
		mySocket.sendMessage("4");											//Enviamos mensaje al servidor para que nos de la soluci髇
		int nBarcos = Integer.parseInt(mySocket.receiveMessage());
		String[] barcos = new String[nBarcos];
		for (int i=0; i<nBarcos; i++){
			barcos[i] = mySocket.receiveMessage();
		}

		return barcos; // cambiar por el retorno correcto					//Obtenemos la soluci髇
	} // end getSolucion
} //end class
