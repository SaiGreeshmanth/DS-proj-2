import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
// This class handles the peer to peer connection
public class PeerConnector extends Thread {
	//creates a new server socket
	private ServerSocket serverSocket;
	// adds new peer into stick
	private Stick peersInRing = new Stick();
	
	public PeerConnector(int portnum) throws IOException{
		
		serverSocket = new ServerSocket((portnum));
		
	}
	// when peers are connected and thread is allowed to run
	public void run() {
		
		try {
			
			while(true) {
				
				ThreadPC serverThreadThread = new ThreadPC(serverSocket.accept(), this);
				peersInRing.add(serverThreadThread);
				serverThreadThread.start();
				
			}
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			
		}		
		
	}
	// sends message to peers
	void sendMessage(String message) {
		
		try {
						
			//peersInRing.forEach(t -> t.getPrintWriter().println(message));
			peersInRing.broadcastMsg(message);
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}

	// what all peers are connected in the stick topology
	public Stick getRing(){
		
		return peersInRing;
		
	}
	
}
