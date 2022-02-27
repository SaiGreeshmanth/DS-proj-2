import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.LinkedList;
// Class used to describe the Peers present in the Stick Topology
public class Peer {
	// A linked list creation
	private LinkedList<ThreadP> sockets;
	// main execution of the code
	public static void main(String[] args) throws IOException {
		// Peer name
		String username;
		// Peer Port Number
		String port;
		// to take in command line arguments
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter username: ");
		username = bufferedReader.readLine();
		
		System.out.print("Enter port to use: ");
		port = bufferedReader.readLine();
		
		
		PeerConnector serverThread = new PeerConnector(Integer.valueOf(port));
		serverThread.start();
		// new peer is joined to its successor client port number
		new Peer().updatePeers(bufferedReader, username, serverThread);
		
	}
	// Has updated info of the successor client
	public void updatePeers(BufferedReader bufferedReader, String username, PeerConnector serverThread) throws IOException{
		// checks if this is the first client that is joined the chat or not
		if(this.sockets == null || this.sockets.isEmpty()) {
			
			sockets = new LinkedList<ThreadP>();
			
		}
		
		String host = "localhost";
		String portnum;
		// takes in the successor port for ring connection
		System.out.println("For multiple connections, put space between ports");
		// enter command c to rejoin
		System.out.println("Enter Port# to connect to peer (c to continue): ");
		portnum = bufferedReader.readLine();
				
		String[] splitted = portnum.split(" ");
		// if peer is wants to join the again we will type c to continue
		if(!portnum.equals("c")) {
			
			for(int i = 0; i < splitted.length; i++) {
				
				Socket socket = null;
				// checking for any errors or invalid inputs entered
				try {
					socket = new Socket(host, Integer.valueOf(splitted[i]));
					ThreadP TP = new ThreadP(socket);
					TP.start();
					sockets.add(TP);
					
				}
				catch(Exception e) {
					
					if (socket != null) {
						
						socket.close();
						
					}
					else {
						
						System.out.print("Invalid input. Moving on..");
						
					}
					
				}
				
			}
			
		}
		
		broadcast(bufferedReader, username, serverThread);
		
	}
	// this method handles the different message broadcast types among peers in the chat
	public void broadcast(BufferedReader bufferedReader, String username, PeerConnector serverThread) {
		
		try {
			
			System.out.println("You can now communicate: (commands: leave, shutdown, shutdown all): ");
			boolean flag = true;
			
			while(flag) {
				
				System.out.print("==>");
				// if peer wants to shutdown
				String message = bufferedReader.readLine();
				if(message.equals("shutdown")) {
					
					flag = false;
					break;
					
				}
				// if peer wants to leave the chat
				else if(message.equals("leave")) {
					
					for(int i = 0; i < sockets.size(); i++) {
						
						sockets.get(i).stop();
						
					}
					updatePeers(bufferedReader, username, serverThread);
					
				}
				else {
					
					StringWriter stringWriter = new StringWriter();
					
					stringWriter.append("[" + username + "] " + message);
					
					serverThread.sendMessage(stringWriter.toString());
					// if peer wants to shutdown all clients in the chat
					if(message.contains("shutdown all")){
						
						System.out.println("Shutting down..");
						flag = false;
						
					}
					if(message.equals("leave")) {
						
						for(int i = 0; i < sockets.size(); i++) {
							
							sockets.get(i).stop();
							
						}
						updatePeers(bufferedReader, username, serverThread);
						
					}
				}
				
			}
			
			System.out.println("Exiting..");
			System.exit(0);
			
		}
		catch(Exception e) {
			
		}
		
	}

}
