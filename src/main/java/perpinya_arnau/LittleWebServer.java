package perpinya_arnau;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Petit servidor web amb capacitat per respondre les comandes GET i POST.
 * També pot enviar correus electrònics mitjançant el recurs sendmail.html.
 *  
 * @author pererdg
 *
 */
public class LittleWebServer {

	private static final int PORT = 80;
	
	/**
	 * Obrir port 80, esperar connexió i llançar nou HTTPConnection.
	 * 
	 * @param args 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("LittleWebServer ENGEGAT");
		
		//TODO Crear socket servidor
		ServerSocket serverSck = new ServerSocket(PORT);
			//TODO Esperar connexió
			do {
				// Esperar connexió
				Socket sck = serverSck.accept();
				new Thread(new HTTPConnection(sck)).start();
				System.out.println("Connexió: " + sck.getRemoteSocketAddress().toString());

			} while (true);
			//TODO Connexió establerta


	}
}
