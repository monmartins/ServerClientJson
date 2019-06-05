package Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONArray;

import Serializable.CJson;

public class CServer {
	ServerSocket serverSocket;
	public Socket socket;
	public CJson cjson = new CJson();
	public JSONArray objArray = null;
	public int count;
	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			objArray = cjson.deserialize("file.json");

			this.count = objArray.length();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endServerConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveServer() {
		InputStream input = null;
		Boolean outWhile = false;
		int read_c;
		try {
			socket = serverSocket.accept();
			System.out.println("Client connected");

			String msg="";
			input = socket.getInputStream();
			while(!outWhile) {
				read_c = input.read();
				msg=msg+(char)read_c;
				if(msg.contains("NEXT")) {
					sessionNext();
					msg="";
				}
				if(msg.contains("PREVIOUS")) {
					sessionPrevious();
					msg="";
				}
				if(msg.contains("END")) {
					System.out.println(msg);
					msg="";
				}
				if(msg.contains("OUT-SERVER")) {
					endServerConnection();
					msg="";
					this.count=objArray.length();
					outWhile=true;
				}
			}
			System.out.println("Out");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   // reads a line of text
		
	}
	public void sessionNext() {
		if(this.count==0) {
			sendServer("0");
		}else {
		if(this.count>10) {
			sendServer(String.valueOf(10));
			for(int i = objArray.length()-this.count ;i<(objArray.length()-this.count)+10;i++) {
				System.out.println(i);
				System.out.println(objArray.getJSONObject(i).toString());
				sendServer(objArray.getJSONObject(i).toString()+"---|-|");
			}
			this.count=count-10;
		}else {
			sendServer(String.valueOf(this.count));
			for(int i = 0 ;i<this.count;i++) {
				sendServer(objArray.getJSONObject(i).toString()+"---|-|");
			}
			this.count=0;
		}
		}
	}
	public void sessionPrevious() {
		if(this.count>=objArray.length()) {
			sendServer("0");
		}else {
			if(this.count+10<objArray.length()) {
				System.out.println(this.count);
				System.out.println("....");
				sendServer(String.valueOf(10));
				for(int i = this.count+1 ;i<this.count+11 && (objArray.length()-i)>0;i++) {
					sendServer(objArray.getJSONObject(objArray.length()-i).toString()+"---|-|");
				}
				this.count=this.count+10;
			}else {
				System.out.println(this.count);
				System.out.println("...else...");
				this.count=this.count+10;
				sendServer(String.valueOf(this.count));
				for(int i = this.count-objArray.length() ;i<objArray.length();i++) {
					sendServer(objArray.getJSONObject(objArray.length()-i-1).toString()+"---|-|");
				}
				System.out.println(this.count);
				this.count=objArray.length();
			}
		}
	}
	
	public void sendServer(String msg) {
		OutputStream output;
		try {
			output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}   // reads a line of text
		
	}

}
