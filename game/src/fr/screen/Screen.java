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

	public static Screen getInstance() {
		return single;
	}

	public static Screen getInstance(AppliScreen appScreen, int width, int height, int marginX, int marginY,
			int margin) {
		if (single == null) {
			single = new Screen(appScreen, width, height, marginX, marginY, margin);
			return single;
		} else
			return single;
	}

	@SuppressWarnings("unused")
	private MainJPanel mainJpanel;

	private Screen(AppliScreen appScreen, int width, int height, int marginX, int marginY, int margin) {
		this.setTitle(appScreen.getName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				appScreen.close();
			}
		});
		this.setResizable(false);
		this.setSize(marginX + width + margin * 2, marginY + height + margin * 2);
		this.setLocationRelativeTo(null);

		// Clavier
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new KeyBoard());

		JPanel jp = new JPanel();
		jp.setLayout(null);
		jp.setBackground(Color.black);
		jp.add((mainJpanel = MainJPanel.getInstance(this, appScreen, width, height, margin)));
		this.setContentPane(jp);
		this.setVisible(true);
	}
}
