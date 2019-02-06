package fr.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JPanel;

import fr.screen.keyboard.KeyBoard;
import fr.util.time.Timer;

@SuppressWarnings("serial")
public class Root extends JPanel {

	private static Root single;

	private static int WIDTH, HEIGHT;

	public static Root create(Screen scr, int w, int h, int m, String ip) {
		if (single == null) {
			single = new Root(scr, w, h, m, ip);
			return single;
		} else
			return null;
	}

	private Socket socket;

	private Screen scr;

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
		socket = connexion(ip, 5000);
		try {
			sOut = new ObjectOutputStream((socket.getOutputStream()));
			sIn = new ObjectInputStream((socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread repainter = new Thread(new Repainter(this));
		repainter.start();

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

	@SuppressWarnings("unchecked")
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;

		if (socket == null) {
			closeScr();
		}

		try {
			listD = (List<Drawable>) sIn.readObject();
			this.setBackground(new Color(240, 240, 240));
			for (Drawable drawable : listD) {
				drawable.draw(g);
			}
			sOut.writeUnshared(KeyBoard.getKeys());

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Tu as ete kick. (Serveur ferme)");
			// e.printStackTrace();
			closeScr();
		}
	}
}
