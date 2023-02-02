package perpinya_arnau;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Utilitat per enviar correus electrònics via SMTP
 * 
 * @author pererdg
 *
 */
public class MailSender {

	private String smtp_server;
	private int smtp_port;
	private Socket sck;
	private BufferedReader in;
	private PrintStream out;
	private PrintStream printer;

	/**
	 * Constructor
	 * 
	 * @param smtp_server
	 * @param smtp_port
	 */
	public MailSender(String smtp_server, int smtp_port) {
		this.smtp_server = smtp_server;
		this.smtp_port = smtp_port;
	}
	
	/**
	 * Obrir connexió a servidor SMTP i enviar HELO
	 * 
	 * @param helo Identificació HELO
	 * @param printer PrintStream on s'enviarà la informació de la connexió
	 * @throws Exception
	 */
	public void connect(String helo, PrintStream printer) throws Exception {
		// Crear socket client i connectar
		sck = new Socket(smtp_server, smtp_port);
		// Agafar streams d'entrad, sortida i sortida d'informació
		in = new BufferedReader(new InputStreamReader(sck.getInputStream()));
		out = new PrintStream(sck.getOutputStream(), true); // Autoflush
		this.printer = printer;
		// OK connexió
		read();
		// HELO
		writeRead("HELO " + helo);
	}
	
	/**
	 * Enviar un missatge
	 * 
	 * @param mail
	 * @throws Exception
	 */
	public void send(EMail mail) throws Exception {
		// MAIL FROM:
		writeRead("MAIL FROM: <" + mail.getFrom() + ">");
		// RCPT TO:
		writeRead("RCPT TO: <" + mail.getTo() + ">");
		// DATA
		writeRead("DATA");
		// Header
		write("From: " + mail.getFrom());
		write("To: " + mail.getTo());
		write("Subject: " + mail.getSubject());
		write("");
		// Body
		write(mail.getBody());
		write("");
		writeRead(".");
	}

	/**
	 * Tancar la connexió SMTP
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		writeRead("QUIT");
		in.close();
		out.close();
		printer = null;
		sck.close();
	}
	
	/**
	 * Legir de l'stream de la connexió SMTP
	 * 
	 * @throws Exception
	 */
	private void read() throws Exception {
		String line;
		do {
			line = in.readLine();
			if (printer != null) printer.println(">> " + line);
		} while (line != null && line.substring(3, 4).equals("-"));
	}
	
	/**
	 * Escriure a l'stream de la connexió SMTP
	 * 
	 * @param txt
	 * @throws Exception
	 */
	private void write(String txt) throws Exception {
		out.println(txt);
		if (printer != null) printer.println("<< " + txt);
	}
	
	/**
	 * Escriure i llegir de la connexió SMTP
	 * 
	 * @param txt
	 * @throws Exception
	 */
	private void writeRead(String txt) throws Exception {
		write(txt);
		read();
	}


	
}
