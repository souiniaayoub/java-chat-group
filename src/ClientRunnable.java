import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRunnable implements Runnable {
	private Socket Sc;
	private User user;

	public Socket getSc() {
		return Sc;
	}

	public void setSc(Socket sc) {
		Sc = sc;
	}

	public ClientRunnable() {
		this.Sc = null;
	}

	public ClientRunnable(Socket Sc) {
		this.Sc = Sc;
	}

	public void exitClient() {
		System.out.println(user.getName() + " : Disconnected");
		if (!Serveur.ActiveClients.remove(this.Sc))
			System.out.println("Didnt delete ClientThread");
		if (!Serveur.WaitingClients.isEmpty()) {
			Socket newsc = Serveur.WaitingClients.remove();
			Serveur.ActiveClients.add(newsc);
			Serveur.threadpool.execute(new ClientRunnable(newsc));
		}
	}

	public void run() {
		if (Sc == null)
			return;
		try {
			ObjectInputStream oi = new ObjectInputStream(Sc.getInputStream());
			this.user = (User) oi.readObject();
			System.out.println(user.getName() + " : Connected");
			BufferedReader br = new BufferedReader(new InputStreamReader(this.Sc.getInputStream()));
			while (true) {
				String clientmsg = br.readLine();
				if (clientmsg != null && clientmsg.equals("exit")) {
					this.exitClient();
					break;
				} else {
					if (clientmsg != null && !clientmsg.equals("")) {
						for (Socket clientSocket : Serveur.ActiveClients) {
							if (clientSocket != this.Sc) {
								try {
									PrintWriter ps = new PrintWriter(clientSocket.getOutputStream(), true);
									ps.println(user.getName() + " : " + clientmsg);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}
						;

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				Sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
