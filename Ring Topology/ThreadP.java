import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// ThreadP class is for the Peer
public class ThreadP extends Thread {

	private BufferedReader bufferedReader;
	private Socket socket;
	
	public ThreadP(Socket socket) throws IOException{
	
		this.socket = socket;
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
	}

	// active thread running
	public void run() {
		
		boolean flag = true;
		while(flag) {
			
			try {
				// returns the message if socket is closed
				if(socket.isClosed()) {
					
					return;
					
				}
				
				String received = bufferedReader.readLine();
				//bufferedReader.close();
				//bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println(received);

				// peer or peers exits if message contains shutdown/shutdown all respectively
				if(received.contains("shutdown")||received.contains("SHUTDOWN")){
					
					System.out.println("Exiting..");
					System.exit(0);
					
				}
				
				
			}
			catch(Exception e) {
				
				flag = false;
				interrupt();
				
			}
			
		}
		
	}
}
