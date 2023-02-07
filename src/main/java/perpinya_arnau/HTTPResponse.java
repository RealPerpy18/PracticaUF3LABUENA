package perpinya_arnau;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * Gestiona la resposta a una petició HTTPRequest.
 * Sols dona resposta als mètodes GET i POST.
 * Pel mètode POST sols respon Content-Type: application/x-www-form-urlencoded.
 * 
 * La resposta sempre activa el header Connection: Closed.
 * 
 * El servidor té la particularitat que pot enviar un correu electrònic quan
 * des de la pàgina sendmail.html es fa un POST o un GET amb les dades del mail.
 * 
 * @author pererdg
 *
 */
public class HTTPResponse {
	
	// Directori arrel del servidor relatiu al directori d'execució del servidor.
	private static final String ROOT = "./www";
	// Identificació per la comanda HELO SMTP.
	private static final String HELO = "inspalamos.cat";
	// Fitxer/recurs que identifica que cal enviar un mail. 
	private static final String MAILSENDER = "MailSender";
	
	private Socket sck;
	private PrintStream out;
	
	/**
	 * Constructor
	 * 
	 * @param sck Socket de la connexió HTTP
	 * @throws Exception
	 */
	public HTTPResponse(Socket sck) throws Exception {
		this.sck=sck;
	}



	/**
	 * Prepara la resposta d'una petició HTTP i l'envia.
	 * Sols és capaç de gestionar els mètodes GET i POST.
	 * 
	 * @param request Dades de la petició
	 * @throws Exception
	 */
	public void sendResponse(HTTPRequest request) throws Exception {
		if (request.getMethod().equalsIgnoreCase("get")){
			processGET(request);

		}
		else if(request.getMethod().equalsIgnoreCase("post")){
			processPOST(request);
		}
		else{
			sendMethodNotAllowed();
		}
	}
	
	/**
	 * Processa la petició GET i envia resposta.
	 * 
	 * @param request Dades de la petició
	 * @throws Exception
	 */
	private void processGET(HTTPRequest request) throws Exception {
		out.println(request.getUrl());
	}

	/**
	 * Processa la petició POST i envia resposta
	 * 
	 * @param request Dades de la petició
	 * @throws Exception
	 */
	private void processPOST(HTTPRequest request) throws Exception {

	}

	
	/**
	 * Envia resposta HTTP. 
	 * Pot enviar un fitxer o processar email.
	 * Si el path es correspon amb la constant MAILSENDER llavor cal enviar un mail
	 * i als paràmetres hi ha les dades del mail, si no cal enviar un fitxer que es troba
	 * a ROOT.
	 * 
	 * @param path Fitxer a enviar
	 * @param params Paràmetres del formulari HTML (si n'hi ha)
	 * @throws Exception
	 */
	private void sendResponse(String path, HashMap<String,String> params) throws Exception {
		if (path.contains(MAILSENDER)) {
			sendMail(params);
		} else {
			sendFile(path);
		}
	}
	
	/**
	 * Envia un fitxer.
	 * El fitxer ha d'estar dins de ROOT.
	 * 
	 * @param path Path del fitxer (relatiu a ROOT).
	 * @throws Exception
	 */
	private void sendFile(String path) throws Exception {

	}
	
	/**
	 * Envia un missatge. 
	 * Com a resposta construeix una pàgina HTML amb la comunicació SMTP.
	 * 
	 * @param params Matriu amb els paràmetres del missatge.
	 * @throws Exception
	 */
	private void sendMail(HashMap<String,String> params) throws Exception {

	}

	/**
	 * Envia el header amb el format següent:
	 * ---
	 *  firstLine
	 *  Connection: Closed
	 *  
	 * ---
	 * @param firstLine Contingut de la primera línia
	 * @throws Exception
	 */
	private void sendHeader(String firstLine) throws Exception {
		writeLine(firstLine);
		writeLine("Connection: Closed");
		writeLine("");
	}
	
	/**
	 * Envia header 200 Ok.
	 * 
	 * @throws Exception
	 */
	public void sendOk() throws Exception {
		sendHeader("HTTP/1.1 200 Ok");
	}
	
	/**
	 * Envia header 404 Not Found
	 * 
	 * @throws Exception
	 */
	public void sendNotFound() throws Exception {
		sendHeader("HTTP/1.1 404 Not Found");
	}
	
	/**
	 * Envia header 500 Internal server error
	 * 
	 * @throws Exception
	 */
	public void sendInternalServerError() throws Exception {
		sendHeader("HTTP/1.1 500 Internal server error");
	}
	
	/**
	 * Envia header 405 Method not allowed
	 * 
	 * @throws Exception
	 */
	public void sendMethodNotAllowed() throws Exception {
		sendHeader("HTTP/1.1 405 Method not allowed");
	}
	
	/**
	 * Envia header 400 Bad request
	 * @throws Exception
	 */
	public void sendBadRequest() throws Exception {
		sendHeader("400 Bad request");
	}
	
	/**
	 * Escriu una línia a l'stream del socket
	 *  
	 * @param text
	 * @throws Exception
	 */
	private void writeLine(String text) throws Exception {
		debug(text);
	}
	
	/**
	 * Presenta per cònsola un text
	 * 
	 * @param txt
	 */
	private void debug(String txt) {
		System.out.println(sck.getRemoteSocketAddress() + " << " + txt);
	}
	
	/**
	 * Obté els paràmetres (key, value) d'una query d'una URL
	 * @param query Part query de la URL
	 * @return Matriu amb els paràmetres
	 * @throws Exception
	 */
	/*private HashMap<String,String> getParams(String query) throws Exception {

		return params;
	}*/
}
