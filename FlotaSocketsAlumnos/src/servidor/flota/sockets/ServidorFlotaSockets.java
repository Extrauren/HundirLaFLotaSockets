package servidor.flota.sockets;

import java.net.ServerSocket;

import comun.flota.sockets.MyStreamSocket;

/**
 * Este modulo contiene la logica de aplicacion del servidor del juego Hundir la flota
 * Utiliza sockets en modo stream para llevar a cabo la comunicacion entre procesos.
 * Puede servir a varios clientes de modo concurrente lanzando una hebra para atender a cada uno de ellos.
 * Se le puede indicar el puerto del servidor en linea de ordenes.
 */


public class ServidorFlotaSockets {

	public static void main(String[] args) {

		// Acepta conexiones vía socket de distintos clientes.
		// Por cada conexión establecida lanza una hebra de la clase HiloServidorFlota.


		// Revisad el apartado 5.5 del libro de Liu

		try {
			ServerSocket miSocketConexion = new ServerSocket(12345);
			System.out.println("El servidor Hundir La Flota esta listo");
			while(true) {
				System.out.println("Espera una conexion");
				MyStreamSocket miSocket = new MyStreamSocket(miSocketConexion.accept( ));
				Thread hilo = new Thread(new HiloServidorFlota(miSocket));
				hilo.start();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}

	} //fin main
} // fin class
