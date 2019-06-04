
import Serializable.CJson;
import Socket.CServer;

public class Main {
	
	public static void main(String[] args) {
		CServer server = new CServer();
		server.startServer(5000);
		while (true) {
			server.receiveServer();
		     
		    // read data from the client
		    // send data to the client
		}
	}

}
