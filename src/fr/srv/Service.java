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

	private List<Drawable> listD;

	public Service(Socket serverSocket) {
		this.socket = serverSocket;
		listD = new ArrayList<>();
		b = new Ball(50, 50, 20, 10, new Color(255, 0, 0));
		listD.add(b);

		thread = new Thread(this);
	}

	@Override
	public void finalize() throws IOException {
		socket.close();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void run() {
		try {
			ObjectOutputStream sOut = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream sIn = new ObjectInputStream(socket.getInputStream());
			while (true) {
				Thread.sleep(17);
				for (Drawable d : listD) {
					sOut.writeObject(d);
				}
				sOut.writeObject(null);
				sOut.reset();

				List<Integer> keys = (List<Integer>) sIn.readObject();
				b.move(keys);

			}
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			try {
				socket.close();
			} catch (IOException e1) {
			}
			thread = null;
		}
	}

	public void start() {
		thread.start();
	}
}
