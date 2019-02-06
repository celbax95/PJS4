package fr.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import fr.screen.keyboard.KeyBoard;
import fr.util.time.Timer;

@SuppressWarnings("serial")
public class Root extends JPanel implements Runnable {

	private static Root single;

	private static int WIDTH, HEIGHT;

	private Socket socket;

	private Screen scr;

	private Object transfer;

	private List<Drawable> listD;

	private ObjectOutputStream sOut;
	private ObjectInputStream sIn;

	Timer t;

	private Root(Screen scr, int w, int h, int m, String ip) {
		super();
		this.scr = scr;
		WIDTH = w;
		HEIGHT = h;
		this.setLocation(m, m);
		this.setSize(w, h);

		t = new Timer();

		if (ip == null)
			ip = "localhost";

		if ((socket = connexion(ip, 5000)) == null)
			closeScr();

		transfer = new Object();
		listD = new ArrayList<>();

		try {
			sOut = new ObjectOutputStream((socket.getOutputStream()));
			sIn = new ObjectInputStream((socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread repainter = new Thread(new Repainter(this));
		repainter.start();

		(new Thread(this)).start();

	}

	public void closeScr() {
		scr.dispatchEvent(new WindowEvent(scr, WindowEvent.WINDOW_CLOSING));
	}

	public void closeSocket() {
		try {
			sOut.writeObject(null);
			sOut.reset();
			System.out.println("FIN");
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	private Socket connexion(String ip, int port) {
		try {
			return new Socket(ip, port);
		} catch (IOException e) {
			System.err.println("Impossible de se connecter au serveur");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;

		this.setBackground(new Color(240, 240, 240));
		synchronized (transfer) {
			if (listD != null) {
				for (Drawable drawable : listD) {
					drawable.draw(g);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			List<Drawable> ld;
			;
			while (!Thread.currentThread().isInterrupted()) {
				ld = (List<Drawable>) sIn.readObject();
				synchronized (transfer) {
					listD = new ArrayList<>(ld);
				}
				sOut.writeUnshared(KeyBoard.getKeys());
				sOut.flush();
				sOut.reset();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public static Root create(Screen scr, int w, int h, int m, String ip) {
		if (single == null) {
			single = new Root(scr, w, h, m, ip);
			return single;
		} else
			return null;
	}
}
