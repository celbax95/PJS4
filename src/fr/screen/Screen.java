package fr.screen;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.screen.keyboard.KeyBoard;

@SuppressWarnings("serial")
public class Screen extends JFrame {

	private static Screen single = null;

	private Root r;

	private Screen(AppliScreen appScr, int w, int h, int mx, int my, int m) {
		this.setTitle(appScr.getName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				appScr.close();
			}
		});
		this.setResizable(false);
		this.setSize(mx + w + m * 2, my + h + m * 2);
		this.setLocationRelativeTo(null);

		// Clavier
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new KeyBoard());

		JPanel jp = new JPanel();
		jp.setLayout(null);
		jp.setBackground(Color.black);
		jp.add((r = Root.getInstance(this, appScr, w, h, m)));
		this.setContentPane(jp);
		this.setVisible(true);
	}

	public static Screen getInstance() {
		return single;
	}

	public static Screen getInstance(AppliScreen appScr, int w, int h, int mx, int my, int m) {
		if (single == null) {
			single = new Screen(appScr, w, h, mx, my, m);
			return single;
		} else
			return single;
	}
}
