package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

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

			count = objArray.length();
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
			sessionNext();

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
//					sendServer(((new CJson()).serialize(new JSONObject())));
					msg="";
				}
				if(msg.contains("OUT-SERVER")) {
					endServerConnection();
					msg="";
					count=objArray.length();
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
		if(count>10) {
			sendServer(String.valueOf(count));
			count=count-10;
			for(int i = 0 ;i<10;i++) {
				sendServer((((new CJson()).serialize(objArray.getJSONObject(i)))));
			}
		}else {
			sendServer(String.valueOf(count));
			for(int i = 0 ;i<count;i++) {
				sendServer((((new CJson()).serialize(objArray.getJSONObject(i)))));
			}
			count=0;
		}
	}
	public void sessionPrevious() {
		if(count>10) {
			sendServer(String.valueOf(count));
			count=count-10;
			for(int i = 0 ;i<10;i++) {
				sendServer((((new CJson()).serialize(objArray.getJSONObject(i)))));
			}
		}else {
			sendServer(String.valueOf(count));
			for(int i = 0 ;i<count;i++) {
				sendServer((((new CJson()).serialize(objArray.getJSONObject(i)))));
			}
			count=0;
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
