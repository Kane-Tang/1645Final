package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The stand alone index server. It provides services such as register, search, delete for peers in this system. It use simple hashmap get to perform search.
 */
public class Server {

	public static void main(String args[]) throws IOException {
		ServerSocket serverSocket = new ServerSocket(Constants.PORT_NUM);
		Index data = new Index();
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				Thread thread = new Thread(new Service(socket, data));
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}