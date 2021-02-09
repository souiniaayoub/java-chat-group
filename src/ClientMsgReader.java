
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMsgReader extends Thread {

	public ClientMsgReader() {
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Client.Sc.getInputStream()));
			while (!Client.Sc.isClosed()) {
				
				String Receivedmsg;
				if ((Receivedmsg = br.readLine()) != null)
					System.out.println(Receivedmsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
