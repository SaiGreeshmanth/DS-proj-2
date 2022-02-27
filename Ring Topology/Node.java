// node class containing info of node
public class Node {

	//an instance of the thread which connects the peer is created
	public ThreadPC peer;
	public Node next;
	
	public Node(ThreadPC peer) {
		
		this.peer = peer;
		this.next = null;
	
	}
	
}
