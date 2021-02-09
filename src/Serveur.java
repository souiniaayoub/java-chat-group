
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Serveur {
	public static Queue<Socket> WaitingClients;
	public static Queue<Socket> ActiveClients;
	public static ThreadPoolExecutor threadpool;

	public static void main(String[] args) {
		int maxClients = 2;
		WaitingClients = new LinkedList<Socket>();
		ActiveClients = new LinkedList<Socket>();
		threadpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxClients);
		ServerSocket SSc = null;
		try {
			SSc = new ServerSocket(4040);
			System.out.println("Server Running....");
			while (true) {
				Socket Sc = SSc.accept();
				WaitingClients.add(Sc);
				if (ActiveClients.size() < maxClients) {
					ActiveClients.add(Sc);
					WaitingClients.remove(Sc);
					threadpool.execute(new ClientRunnable(Sc));
				} else {
					sendWaitingMsg(Sc, "The Queue is Full wait for a user to disconnect.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				SSc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void sendWaitingMsg(Socket Sc, String Msg) {
		try {
			PrintWriter ps = new PrintWriter(Sc.getOutputStream(), true);
			ps.println(Msg);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
