package fr.srv;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.screen.Drawable;

public class Service implements Runnable {

	private Socket socket;
	private Thread thread;

	private Ball b;

	List<Drawable> listD;

	public Service(Socket serverSocket) {
		this.socket = serverSocket;
		listD = new ArrayList<>();
		b = new Ball(50, 50, 20, new Color(255, 0, 0));
		listD.add(b);
		thread = new Thread(this);
	}

	@Override
	public void finalize() throws IOException {
		socket.close();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void run() {
		try {
			ObjectOutputStream sOut = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream sIn = new ObjectInputStream(socket.getInputStream());
			while (true) {
				Thread.sleep(1000);

				sOut.writeObject(listD);
				b.move(10, 0);
				listD = new ArrayList<>();
				listD.add(b);
				// sIn.readObject();

			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		thread.start();
	}
}
