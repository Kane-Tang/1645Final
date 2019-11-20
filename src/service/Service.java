package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class Service implements Runnable {

	private Socket socket;
	private Index index;

	public Service(Socket socket, Index data) {
		this.socket = socket;
		this.index = data;
	}

	@Override
	public void run() {
		try {
			String peerId = receiveFromPeer();
			System.out.println(peerId + " connected");
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			loginCheck(peerId, printWriter);
			while (true) {
				String[] command = receiveFromPeer().split(Constants.SPLIT);
				String service = command[0];
				if (service.equals(Constants.REGISTER)) {
					register(peerId, command[1]);
					sendResponse(printWriter, "REGISTER complete!");
				} else if (service.equals(Constants.SEARCH)) {
					String response = search(command[1]);
					sendResponse(printWriter, response);
				} else if (service.equals(Constants.DOWNLOAD)) {
					String response = search(command[1]);
					sendResponse(printWriter, response);
					if (receiveFromPeer().split(Constants.SPLIT)[0].equals(Constants.SUCCESS)) {
						index.peerRegister(peerId, command[1]);
					}
				} else if (service.equals(Constants.USER_EXIT)) {
					System.out.println(peerId + " disconnected");
					exit(peerId, printWriter);
				} else if (service.equals(Constants.DELETE)) {
					String fileName = command[1];
					index.deleteFileInPeer(fileName, peerId);
				}
			}
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void exit(String peerId, PrintWriter printWriter) {
		if (index.peerLogoutSuccess(peerId)) {
			sendResponse(printWriter, "disconnected");
		} else {
			sendResponse(printWriter, "Thank you for coming!");
		}
	}


	private String search(String target1) {
		String target = target1;
		String response = Constants.SPLIT;
		for (String peer : index.searchFile(target)) {
			if (index.isAvailable(peer)) {
				response += peer + Constants.SPLIT;
			}
		}
		if (!response.equals(Constants.SPLIT)){
			return response;
		}
		return Constants.FAIL;
	}


	private void register(String peerId, String address) {
		File folder = new File(address);
		String[] files = folder.list();
		for (String name : files) {
			index.peerRegister(peerId, name);
		}
	}


	private void loginCheck(String peerId, PrintWriter printWriter) {
		if (index.peerLoginSuccess(peerId)) {
			sendResponse(printWriter, "connected");
		} else {
			sendResponse(printWriter, "welcome to file sharing system, please REGISTER first!");
		}
	}


	private void sendResponse(PrintWriter printWriter, String s) {
		printWriter.println(s);
		printWriter.flush();
	}


	private String receiveFromPeer() throws IOException {
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		return bf.readLine();
	}
}
