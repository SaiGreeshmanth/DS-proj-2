// this class ring keeps track of new joining peers and handles messages between the peers
public class Ring {

	// starting node
	Node head;
	
	public Ring() {
		
		head = null;
		
	}

	//
	 public Ring add(ThreadPC peer)
	 {
		    // new joining peer joins the successor peer
	        Node new_node = new Node(peer);
	        new_node.next = head;

			// if no active peers in chat
	        if (head == null) {
	        
	        	head = new_node;
	        	head.next = head;
	        	
	        }

			// if peers are present in chat
	        else {
	            
	        	Node current = head;
	        	
	            while (current.next != head) {
	            
	            	current = current.next;
	            
	            }
	  
	            current.next = new_node;

	        }
	   
	        return this;
	    }

	 // transmits message to all the active clients in ring
	 public void broadcastMsg(String message) {
		 
		 Node current = head;
		 
		 if(head == null)
			 return;
		 
		 while(current.next != head)
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
		 
		 while(current.next != head)
		 {
			 if(current.next.peer == peer)
				 current.next = current.next.next;
			 
			 current = current.next;
		 
		 }
		 
	 }
	
}
