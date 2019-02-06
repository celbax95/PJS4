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

	private SendItem si;

	private Socket socket;
	private Screen scr;

	private Timer t;

	private List<Drawable> listD;

	private ObjectOutputStream sOut;
	private ObjectInputStream sIn;

	private Root(Screen scr, int w, int h, int m) {
		super();
		this.scr = scr;
		WIDTH = w;
		HEIGHT = h;
		this.setLocation(m, m);
		this.setSize(w, h);

		si = new SendItem();

		socket = connexion("localhost", 5000);
		try {
			sOut = new ObjectOutputStream(socket.getOutputStream());
			sIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread repainter = new Thread(new Repainter(this));
		repainter.start();

		t = new Timer();
	}

	public void closeScr() {
		scr.dispatchEvent(new WindowEvent(scr, WindowEvent.WINDOW_CLOSING));
	}

	public void closeSocket() {
		try {
			System.out.println("FIN");
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
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

	@SuppressWarnings({ "unchecked" })
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		this.setBackground(new Color(240, 240, 240));

		if (socket == null) {
			closeScr();
		}

		Drawable d;
		try {
			while ((d = (Drawable) sIn.readObject()) != null) {
				d.draw(g);
			}

			sOut.writeObject(KeyBoard.getKeys());
			sOut.reset();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			closeScr();
		}

		t.tick();
	}

	public static Root create(Screen scr, int w, int h, int m) {
		if (single == null) {
			single = new Root(scr, w, h, m);
			return single;
		} else
			return null;
	}
}
