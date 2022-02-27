// this class ring keeps track of new joining peers and handles messages between the peers
public class Stick {
	// starting node
	Node head;
	
	public Stick() {
		
		head = null;
		
	}
	
	 public Stick add(ThreadPC peer)
	 {
		 // new joining peer joins the successor peer
		 Node new_node = new Node(peer);
	        new_node.next = null;
	        new_node.prev = null;
		 // if no active peers in chat
		 if (head == null) {
	        
	        	head = new_node;
	        	
	        }
		 // if peers are present in chat
		 else {
	            
	        	Node current = head;
	        	
	            while (current.next != null) {
	            
	            	current = current.next;
	            
	            }
	  
	            current.next = new_node;
	            new_node.prev = current;
	            
	        }
	   
	        return this;
	    }
	// transmits message to all the active clients in ring
	public void broadcastMsg(String message) {
		 
		 Node current = head;
		 
		 if(head == null)
			 return;
		 
		 while(current.next != null)
		 {
			 current.peer.getPrintWriter().println(message);
			 current = current.next;
		 
		 }
		 
		 current.peer.getPrintWriter().println(message);
		 
		 
	 }
	// function that removes a peer in the chat
	public void remove(ThreadPC peer) {
		 
		 Node current = head;
		 
		 if(head == null)
			 return;
		 
		 if(head.peer == peer) {
			 
			 head = head.next;
			 
		 }
		 
		 while(current != null && current.next != null)
		 {
			 if(current.next.peer == peer) {
				
				 if(current.next != null)
					 current.next = current.next.next;
				 
				 if(current.next != null)
					 current.next.prev = current;
			 }
			 current = current.next;
		 
		 }
		 
		 if(current.peer == peer) {
			 
			 current.prev = null;
			 current = null;
		 
		 }
		 
	 }
	
}
