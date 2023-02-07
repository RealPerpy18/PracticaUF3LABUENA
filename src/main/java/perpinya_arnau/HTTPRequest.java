package perpinya_arnau;

import java.net.URL;
import java.util.HashMap;

/**
 * Dades d'una petició HTTP.
 * El body sols es pot carregar amb Content-Type: application/x-www-form-urlencoded.
 *
 * @author pererdg
 *
 */
public class HTTPRequest {

	private String method;
	private URL url;
	private String version;
	private HashMap<String,String> headers;
	private String body;
	/**
	 * Constructor
	 *
	 * @param method Mètode HTTP
	 * @param url URL
	 * @param version Versió HTTP: HTTP/1.1
	 * @param headers Matriu dels headers de la petició HTTP
	 * @param body Body
	 * @throws Exception
	 */

	public HTTPRequest(String method, URL url, String version, HashMap<String, String> headers, String body) {
		this.method = method;
		this.url = url;
		this.version = version;
		this.headers = headers;
		this.body = body;
	}



	public String getMethod() {
		return method;
	}

	public URL getUrl() {
		return url;
	}

	public String getVersion() {
		return version;
	}

	public HashMap<String,String> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}
}
