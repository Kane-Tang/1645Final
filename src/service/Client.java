package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.FileChannel;

import com.sun.javafx.font.Metrics;

public class Client {

	public static void main(String args[]) throws IOException {
		Socket socket = new Socket(Constants.HOST_NAME, Constants.PORT_NUM);

		String userId = getUserId();
		generateRandomFile(Constants.DIR_PATH + userId);
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
		printWriter.println(userId);
		printWriter.flush();
		BufferedReader loginInfo = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println(loginInfo.readLine());

		while (true) {
			try {
				String[] command = receiveCommand().split(Constants.SPLIT);
				String service = command[0];
				if (service.equals(Constants.REGISTER)) {
					String address = Constants.DIR_PATH + userId + Constants.SEPARATOR;
					sendRequest(printWriter, service, address);
					System.out.println(receiveFromServer(socket));
				} else if (service.equals(Constants.SEARCH)) {
					String fileName = command[1];
					sendRequest(printWriter, service, fileName);
					System.out.println(receiveFromServer(socket));
				} else if (service.equals(Constants.DOWNLOAD)) {
					obtain(socket, userId, printWriter, command[1], service);
				} else if (service.equals(Constants.USER_EXIT)) {
					sendRequest(printWriter, service, Constants.UNKNOWN);
					System.out.println(receiveFromServer(socket));
					break;
				} else if (service.equals(Constants.LOOKUP)) {
					lookupCurrentFile(userId);
				} else if (service.equals(Constants.DELETE)) {
					delete(userId, printWriter, command);
				} else {
					System.out.println("invalid input");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.exit(1);
	}


	private static String getUserId() throws IOException {
		System.out.println("Please enter your user id");
		DataInputStream enteredId = new DataInputStream(System.in);
		return enteredId.readLine();
	}


	private static void delete(String userId, PrintWriter printWriter, String[] command) {
		for (int i = 1; i < command.length; i++) {
			File delFile = new File(Constants.DIR_PATH + userId + Constants.SEPARATOR + command[i]);
			if (delFile.delete()) {
				sendRequest(printWriter, Constants.DELETE, command[i]);
			}
		}
	}


	private static void obtain(Socket socket, String userId, PrintWriter printWriter, String fileName, String service) throws IOException {
		sendRequest(printWriter, service, fileName);
		String response = receiveFromServer(socket);
		if (response.equals(Constants.FAIL)){
			return;
		}
		String[] allusers = response.split(Constants.SPLIT);
		try {
			if (allusers.length > 0) {
				File source = new File(Constants.DIR_PATH + allusers[1] + Constants.SEPARATOR + fileName);
				File dest = new File(Constants.DIR_PATH + userId + Constants.SEPARATOR + fileName);
				sendRequest(printWriter, Constants.SUCCESS, Constants.UNKNOWN);
				System.out.println("download " + fileName + " succeed");
			} else {
				System.out.println("There is no available resource");
			}
		} catch (Exception e) {
			System.out.println("There is no available resource");
		}
	}


	private static void lookupCurrentFile(String userId) {
		String fileList = "";
		File folder = new File(Constants.DIR_PATH + userId);
		System.out.println(Constants.DIR_PATH + userId);
		String[] files = folder.list();
		for (String name : files) {
			fileList += name + Constants.SPLIT;
		}
		System.out.println(fileList);
	}


	private static String receiveFromServer(Socket socket) throws IOException {
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		return bf.readLine();
	}


	private static void sendRequest(PrintWriter printWriter, String service, String detail) {
		printWriter.println(service + Constants.SPLIT + detail);
		printWriter.flush();
	}


	private static String receiveCommand() throws IOException {
		System.out.println("\nPlease follow the instruction below");
		System.out.println("1 " + Constants.REGISTER + " \t\t\t\tRegister");
		System.out.println("2 " + Constants.LOOKUP + " \t\t\t\tShow my files");
		System.out.println("3 " + Constants.SEARCH + " <filename> \tSearch");
		System.out.println("4 " + Constants.DOWNLOAD + " <filename> \tObtain");
		System.out.println("5 " + Constants.DELETE + " <filename(s)> \tDelete");
		System.out.println("6 " + Constants.USER_EXIT + "\t\t\t\tExit");
		DataInputStream dIS = new DataInputStream(System.in);
		return dIS.readLine();
	}


	private static void downloadFile(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}


	/**
	 * generate random number of files with specified peerId
	 * @param dir
	 */
	public static void generateRandomFile(String dir) {
		try {
			File directory = new File(dir);
			if (!directory.exists()) {
				directory.mkdirs();
				int randomFiles = (int) (Math.random() * 100) + 1;
				for (int i = 0; i < randomFiles; i++) {
					createFile(dir);
				}
			}
		} catch (Exception e) {
			//            e.printStackTrace();
		}
	}


	private static void createFile(String dir) throws IOException {
		int fileNum = (int) (Math.random() * 100) + 1;
		File file = new File(dir + Constants.SEPARATOR + fileNum);
		if (!file.exists())
			file.createNewFile();
		int randomRows = (int) (Math.random() * 100) + 1;
		String row = ": This is test content for the file" + String.valueOf(fileNum);
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 1; i <= randomRows; i++)
			bw.write(i + row + "\n");
		bw.close();
	}
}
