
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static Socket Sc;
	public static ClientMsgWriter CMW;
	public static ClientMsgReader CMR;
	public static void main(String[] args) {
		try {
			 Sc = new Socket(InetAddress.getLocalHost(), 4040);
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Client name : ");
			User user = new User(sc.nextLine());
			ObjectOutputStream oi = new ObjectOutputStream(Sc.getOutputStream());
			oi.writeObject(user);
			CMW = new ClientMsgWriter();
			CMR = new ClientMsgReader();
			CMW.start();
			CMR.start();
			//sc.close(); if i close it it will close in this ClientMsgWriter thread 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
