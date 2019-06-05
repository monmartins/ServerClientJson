
import Socket.CServer;

public class Main {
	
	public static void main(String[] args) {
		CServer server = new CServer();
		server.startServer(5000);
		while (true) {
			server.receiveServer();
		}
	}

}
