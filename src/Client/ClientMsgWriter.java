package Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMsgWriter extends Thread {
	

	public ClientMsgWriter() {
	}

	@SuppressWarnings("deprecation")
	public void run() {
		Scanner sc = new Scanner(System.in);
		try {
			PrintWriter ps = new PrintWriter(Client.Sc.getOutputStream(), true);
			while (true) {
//				System.out.print(user.getName() + " : ");
				String str = sc.nextLine();
				ps.println(str);
				if (str.equals("exit")) {
					Client.Sc.close();
					Client.CMR.stop();
					sc.close();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
