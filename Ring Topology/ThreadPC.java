import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// ThreadPC class is for the Peer Connector
public class ThreadPC extends Thread{

	private PeerConnector serverThread;
	private Socket socket;
	private PrintWriter printWriter;
	
	public ThreadPC(Socket socket, PeerConnector serverThread) {
		//connects with serverThread
		this.serverThread = serverThread;
		this.socket = socket;
		
	}

	// active thread running
	public void run() {
		
		try {

			//storing the string inputStream messages
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			// gets OutputStream messages
			this.printWriter = new PrintWriter(socket.getOutputStream(), true);
			while(true) {
				
				serverThread.sendMessage(bufferedReader.readLine());
				
			}
		}
		catch (Exception e) {
			
			serverThread.getRing().remove(this);
			
		}	
	}
	// returns PrintWriter
	public PrintWriter getPrintWriter() {
		
		return printWriter;
		
	}
	
	
}
