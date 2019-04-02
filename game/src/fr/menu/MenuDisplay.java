package fr.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.gameLauncher.GameLauncher;
import fr.gameLauncher.Menu;
import fr.server.Player;

public class MenuDisplay implements Menu {

	private static MenuDisplay menu;

	private static String TITLE = "TEST PJS4";
	private static int windowSize = 500;

	private static final Color BACKGROUND_COLOR = new Color(50, 54, 57);
	private static final Color PLAYER_RED_BACKGROUND_COLOR = new Color(198, 33, 69);
	private static final Color PLAYER_BLUE_BACKGROUND_COLOR = new Color(33, 75, 198);
	private static final Color PLAYER_GREEN_BACKGROUND_COLOR = new Color(33, 198, 99);
	private static final Color PLAYER_YELLOW_BACKGROUND_COLOR = new Color(209, 197, 31);
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(59, 89, 182);
	private static final Color MESSAGE_BACKGROUND_COLOR = new Color(102, 102, 153);
	private static final Color ARROWS_BACKGROUND_COLOR = new Color(80, 100, 190);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.BOLD, 20);
	private static final int SIZE_BUTTON_X = 210, SIZE_BUTTON_Y = 45;
	private static final int NB_MAX_PLAYERS = 4, NB_MIN_PLAYERS = 1, NB_INIT_PLAYERS = 1;

	private static final String IP = "localhost";
	private static final int PORT = 5000;

	static {
		GameLauncher.setMenu(MenuDisplay.getInstance());
	}


	private JFrame frame;
	private JPanel panel;
	private JLabel message;
	private JLabel nbPlayersLabel;
	private ArrayList<JLabel> playersLabels;
	private JTextField ipTextField;
	private JTextField aliasTextField;
	private JButton host, join, back, decNbPlayers, incNbPlayers, mapL, mapR, search, start, connect;
	private ArrayList<JButton> buttonList;
	private ArrayList<JComponent> componentHostList;
	private ArrayList<JComponent> componentStartList;
	private ArrayList<JComponent> componentJoinList;
	private ArrayList<JComponent> componentConnectList;
	private int menuPosition;
	private int nbPlayers;
	private ArrayList<Player> players;

	private boolean sawIpAdress;
	private boolean sawAlias;

	private ArrayList<Integer> results;
	
	/**
	 * Récupère l'instance unique du menu
	 */
	public static Menu getInstance() {
		if (MenuDisplay.menu == null) {
			MenuDisplay.menu = new MenuDisplay();
			return MenuDisplay.menu;
		} else {
			return MenuDisplay.menu;
		}
	}

	/**
	 * Constructeur du menu (initialisation de tous ses composants)
	 */
	private MenuDisplay() {
		this.frame = new JFrame(MenuDisplay.TITLE);
		this.frame.setSize(new Dimension(windowSize, 250));
		this.frame.setLocationRelativeTo(null);

		this.frame.setPreferredSize(new Dimension(windowSize, windowSize));
		this.frame.setBackground(BACKGROUND_COLOR);
		this.panel = new MenuPanel(windowSize);

		players = new ArrayList<Player>();

		this.menuPosition = 0;
		this.nbPlayers = NB_INIT_PLAYERS;
		this.sawIpAdress = false;
		this.sawAlias = false;

		// composants du menu

		this.host = new JButton("Host");
		this.join = new JButton("Join");
		this.back = new JButton("Back");
		this.search = new JButton("Search");
		this.start = new JButton("Start");
		this.connect = new JButton("Connect");
		this.decNbPlayers = new JButton("-");
		this.incNbPlayers = new JButton("+");
		this.mapL = new JButton("<");
		this.mapR = new JButton(">");
		this.nbPlayersLabel = new JLabel(String.valueOf(nbPlayers), SwingConstants.CENTER);
		this.message = new JLabel("", SwingConstants.CENTER);
		this.ipTextField = new JTextField("Ip Adress");
		this.aliasTextField = new JTextField("Alias");
		this.buttonList = new ArrayList<JButton>(
				Arrays.asList(host, join, back, decNbPlayers, incNbPlayers, mapL, mapR, search, start, connect));
		this.componentHostList = new ArrayList<JComponent>(
				Arrays.asList(search, decNbPlayers, incNbPlayers, mapL, mapR, nbPlayersLabel, aliasTextField));
		this.componentJoinList = new ArrayList<JComponent>(Arrays.asList(ipTextField, aliasTextField, connect));
		this.componentStartList = new ArrayList<JComponent>(Arrays.asList(start));
		this.componentConnectList = new ArrayList<JComponent>(Arrays.asList());

		this.host.setBounds(145, 110, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.join.setBounds(145, 260, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.back.setBounds(260, 400, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.search.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.start.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.connect.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.decNbPlayers.setBounds(145 - SIZE_BUTTON_X / 3, 110, SIZE_BUTTON_X / 3, SIZE_BUTTON_Y);
		this.incNbPlayers.setBounds(145 + SIZE_BUTTON_X, 110, SIZE_BUTTON_X / 3, SIZE_BUTTON_Y);
		this.mapL.setBounds(145 - SIZE_BUTTON_X / 3, 110 + SIZE_BUTTON_Y * 2, SIZE_BUTTON_X / 3, SIZE_BUTTON_Y);
		this.mapR.setBounds(145 + SIZE_BUTTON_X, 110 + SIZE_BUTTON_Y * 2, SIZE_BUTTON_X / 3, SIZE_BUTTON_Y);
		this.nbPlayersLabel.setBounds(130 + SIZE_BUTTON_X / 3, 110, SIZE_BUTTON_X / 2, SIZE_BUTTON_Y);
		this.message.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.ipTextField.setBounds(145, 110, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.aliasTextField.setBounds(145, 140 + SIZE_BUTTON_Y * 2, SIZE_BUTTON_X, SIZE_BUTTON_Y);

		setFocusPaintedButtons();

		this.nbPlayersLabel.setOpaque(true);
		this.message.setOpaque(true);
		this.ipTextField.setHorizontalAlignment(JTextField.CENTER);
		this.aliasTextField.setHorizontalAlignment(JTextField.CENTER);

		this.host.setBackground(BUTTON_BACKGROUND_COLOR);
		this.join.setBackground(BUTTON_BACKGROUND_COLOR);
		this.back.setBackground(BUTTON_BACKGROUND_COLOR);
		this.search.setBackground(BUTTON_BACKGROUND_COLOR);
		this.start.setBackground(BUTTON_BACKGROUND_COLOR);
		this.connect.setBackground(BUTTON_BACKGROUND_COLOR);
		this.decNbPlayers.setBackground(ARROWS_BACKGROUND_COLOR);
		this.incNbPlayers.setBackground(ARROWS_BACKGROUND_COLOR);
		this.mapL.setBackground(ARROWS_BACKGROUND_COLOR);
		this.mapR.setBackground(ARROWS_BACKGROUND_COLOR);
		this.nbPlayersLabel.setBackground(BUTTON_BACKGROUND_COLOR);
		this.message.setBackground(MESSAGE_BACKGROUND_COLOR);
		this.ipTextField.setBackground(BUTTON_BACKGROUND_COLOR);
		this.aliasTextField.setBackground(BUTTON_BACKGROUND_COLOR);

		setFontButtons();
		this.nbPlayersLabel.setFont(BUTTON_FONT);
		this.message.setFont(BUTTON_FONT);
		this.ipTextField.setFont(BUTTON_FONT);
		this.aliasTextField.setFont(BUTTON_FONT);

		setForegroundButtons();
		this.nbPlayersLabel.setForeground(Color.white);
		this.message.setForeground(Color.white);
		this.ipTextField.setForeground(Color.gray);
		this.aliasTextField.setForeground(Color.gray);

		// labels des joueurs
		this.playersLabels = new ArrayList<JLabel>();

		for (int i = 1; i < NB_MAX_PLAYERS + 1; i++) {
			JLabel newPlayer = new JLabel("", SwingConstants.CENTER);
			switch (i) {
			case 1:
				newPlayer.setBounds(145, 40, SIZE_BUTTON_X, SIZE_BUTTON_Y);
				newPlayer.setBackground(PLAYER_RED_BACKGROUND_COLOR);
				break;
			case 2:
				newPlayer.setBounds(145, 60 + SIZE_BUTTON_Y, SIZE_BUTTON_X, SIZE_BUTTON_Y);
				newPlayer.setBackground(PLAYER_BLUE_BACKGROUND_COLOR);
				break;
			case 3:
				newPlayer.setBounds(145, 80 + SIZE_BUTTON_Y * 2, SIZE_BUTTON_X, SIZE_BUTTON_Y);
				newPlayer.setBackground(PLAYER_GREEN_BACKGROUND_COLOR);
				break;
			case 4:
				newPlayer.setBounds(145, 100 + SIZE_BUTTON_Y * 3, SIZE_BUTTON_X, SIZE_BUTTON_Y);
				newPlayer.setBackground(PLAYER_YELLOW_BACKGROUND_COLOR);
				break;
			}
			newPlayer.setOpaque(true);
			newPlayer.setFont(BUTTON_FONT);
			newPlayer.setForeground(Color.white);
			newPlayer.setVisible(false);
			this.playersLabels.add(newPlayer);
		}
	}

	@Override
	public void back() {
		back.doClick();
	}

	public void changePlayersLabels(ArrayList<Player> players) {
		for (JLabel jl : playersLabels) {
			jl.setVisible(false);
		}
		for (int i = 0; i < players.size(); i++) {
			this.playersLabels.get(i).setText(players.get(i).getAlias());
			this.playersLabels.get(i).setVisible(true);
		}
	}

	@Override
	public void display() {
		this.init();
	}

	/**
	 * Cache les composants du menu en fonction de la position dans l'arborescence
	 * du menu
	 */
	private void hideComponents() {
		if (menuPosition == 0) {
			for (JComponent jc : componentHostList) {
				jc.setVisible(false);
			}
			for (JComponent jc : componentJoinList) {
				jc.setVisible(false);
			}
			for (JComponent jc : componentStartList) {
				jc.setVisible(false);
			}
		} else if (menuPosition == 1) {
			for (JComponent jc : componentHostList) {
				jc.setVisible(false);
			}
		} else if (menuPosition == 2) {
			for (JComponent jc : componentJoinList) {
				jc.setVisible(false);
			}
		} else if (menuPosition == 3) {
			for (JComponent jc : componentStartList) {
				jc.setVisible(false);
			}
		} else if (menuPosition == 4) {
			for (JComponent jc : componentConnectList) {
				jc.setVisible(false);
			}
		} else if (menuPosition == 5){
			
		} else if (menuPosition == 6){
			
		} else {/* ÃƒÆ’Ã‚Â  faire */ }

	}

	@Override
	public void hideWindow() {
		frame.setVisible(false);
	}

	/**
	 * Déclaration de tous les listeners des objets du menu et affichage du menu.
	 */
	public void init() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPosition = 1;
				aliasTextField.setBounds(145, nbPlayersLabel.getY()-(SIZE_BUTTON_Y+SIZE_BUTTON_Y/2), SIZE_BUTTON_X, SIZE_BUTTON_Y);
				showComponents();
				search.requestFocusInWindow();
				host.setVisible(false);
				join.setVisible(false);
				back.setVisible(true);
			}
		});
		this.join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPosition = 2;
				aliasTextField.setBounds(145, 140 + SIZE_BUTTON_Y * 2, SIZE_BUTTON_X, SIZE_BUTTON_Y);
				showComponents();
				connect.requestFocusInWindow();
				host.setVisible(false);
				join.setVisible(false);
				back.setVisible(true);
			}
		});
		this.back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideComponents();
				switch (menuPosition) {
				case 1:
					nbPlayers = NB_INIT_PLAYERS;
					nbPlayersLabel.setText(String.valueOf(nbPlayers));
					aliasTextField.setForeground(Color.gray);
					aliasTextField.setText("Alias");
					menuPosition = 0;
					break;
				case 2:
					menuPosition = 0;
					ipTextField.setForeground(Color.gray);
					ipTextField.setText("Ip Adress");
					aliasTextField.setForeground(Color.gray);
					aliasTextField.setText("Alias");
					sawIpAdress = false;
					sawAlias = false;
					break;
				case 3:
					GameLauncher.clientClose();
					GameLauncher.serverClose();
					nbPlayersLabel.setText(String.valueOf(nbPlayers));
					GameLauncher.updateMenu(new ArrayList<Player>());
					menuPosition = 1;
					break;
				case 4:
					GameLauncher.clientClose();
					menuPosition = 2;
					break;
				case 5:
					for(int i = 0; i < players.size(); i++) {
						playersLabels.get(i).setVisible(false);
					}
					nbPlayersLabel.setText(String.valueOf(nbPlayers));
					message.setVisible(false);
					menuPosition = 1;
					break;
				case 6:
					for(int i = 0; i < players.size(); i++) {
						playersLabels.get(i).setVisible(false);
					}
					message.setVisible(false);
					connect.requestFocusInWindow();
					menuPosition = 2;
					break;
				}
				showComponents();
			}
		});
		this.search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GameLauncher.createServer(TITLE, PORT, nbPlayers);
					GameLauncher.serverStart();
					GameLauncher.createClient(IP, PORT, aliasTextField.getText(), MenuDisplay.menu);
					hideComponents();
					menuPosition = 3;
					showComponents();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}

			}
		});
		this.connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GameLauncher.createClient(ipTextField.getText(), PORT, aliasTextField.getText(), MenuDisplay.menu);
					hideComponents();
					menuPosition = 4;
					showComponents();
				} catch (IOException e1) {
					System.err.println("Impossible de se connecter au serveur");
				}

			}
		});
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (GameLauncher.getServerNbPlayers() < Integer.parseInt(nbPlayersLabel.getText())) {
					return;
				}
				hideWindow();
				GameLauncher.serverSetGameOn();
			}

		});
		this.decNbPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nbPlayers--;
				nbPlayersLabel.setText(String.valueOf(nbPlayers));
				if (nbPlayers == NB_MIN_PLAYERS) {
					decNbPlayers.setVisible(false);
				} else {
					decNbPlayers.setVisible(true);
					if (nbPlayers != NB_MAX_PLAYERS) {
						incNbPlayers.setVisible(true);
					}
				}
			}
		});
		this.incNbPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nbPlayers++;
				nbPlayersLabel.setText(String.valueOf(nbPlayers));
				if (nbPlayers == NB_MAX_PLAYERS) {
					incNbPlayers.setVisible(false);
				} else {
					incNbPlayers.setVisible(true);
					if (nbPlayers != NB_MIN_PLAYERS) {
						decNbPlayers.setVisible(true);
					}
				}
			}
		});
		this.ipTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!sawIpAdress) {
					ipTextField.setText("");
					ipTextField.setForeground(Color.white);
					sawIpAdress = true;
				}
			}
		});
		this.aliasTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!sawAlias) {
					aliasTextField.setText("");
					aliasTextField.setForeground(Color.white);
					sawAlias = true;
				}
			}
		});
		;

		this.frame.add(host);
		this.frame.add(join);
		this.frame.add(back);
		for (JComponent jc : componentHostList) {
			this.frame.getContentPane().add(jc);
		}
		for (JComponent jc : componentJoinList) {
			this.frame.getContentPane().add(jc);
		}
		for (JComponent jc : componentStartList) {
			this.frame.getContentPane().add(jc);
		}
		for (JLabel jl : playersLabels) {
			this.frame.getContentPane().add(jl);
		}
		this.message.setVisible(false);
		this.frame.getContentPane().add(message);
		this.frame.add(panel);
		this.frame.pack();
		this.frame.setVisible(true);
		this.frame.setResizable(false);

		back.setVisible(false);
		hideComponents();
	}

	public void refresh() {
		this.frame.repaint();
	}

	/**
	 * Retour au menu après une partie
	 */
	@Override
	public void reset() {
		start.setVisible(false);
		if (menuPosition == 3) {
			menuPosition = 5;
		} else if (menuPosition == 4) {
			menuPosition = 6;
		}
		if(results.isEmpty()) {
			for(int i = 0; i < this.players.size(); i++) {
				this.playersLabels.get(i).setVisible(true);
			}
			this.message.setText("No one has won...");
			this.message.setVisible(true);
		} else {
			for(int i = 0; i < this.players.size(); i++) {
				if(new Integer(this.players.get(i).getNo()).equals(this.results.get(0))) {
					this.playersLabels.get(i).setText(this.playersLabels.get(i).getText()+" has won!");
				}
				this.playersLabels.get(i).setVisible(true);
			}
		}
		MenuDisplay.menu.frame.setVisible(true);
	}

	private void setFocusPaintedButtons() {
		for (JButton jb : buttonList) {
			jb.setFocusPainted(false);
		}
	}

	private void setFontButtons() {
		for (JButton jb : buttonList) {
			jb.setFont(BUTTON_FONT);
		}
	}

	private void setForegroundButtons() {
		for (JButton jb : buttonList) {
			jb.setForeground(Color.white);
		}
	}

	/**
	 * Montre les composants du menu en fonction de la position dans l'arborescence
	 * du menu
	 */
	private void showComponents() {
		if (menuPosition == 0) {
			host.setVisible(true);
			join.setVisible(true);
			back.setVisible(false);
		} else if (menuPosition == 1) {
			for (JComponent jc : componentHostList) {
				if (jc == this.decNbPlayers && this.nbPlayers == NB_MIN_PLAYERS) {
					continue;
				}
				if (jc == this.incNbPlayers && this.nbPlayers == NB_MAX_PLAYERS) {
					continue;
				}
				jc.setVisible(true);
			}
			GameLauncher.updateMenu(new ArrayList<Player>());
		} else if (menuPosition == 2) {
			for (JComponent jc : componentJoinList) {
				jc.setVisible(true);
			}
		} else if (menuPosition == 3) {
			for (JComponent jc : componentStartList) {
				jc.setVisible(true);
			}
		} else if (menuPosition == 4) {
			for (JComponent jc : componentConnectList) {
				jc.setVisible(true);
			}
		} else {
			/* éventuellement à rajouter */ }
	}

	@Override
	public void updatePlayers(ArrayList<Player> players) {
		if (this.players.size() != players.size()) {
			changePlayersLabels(players);
			this.players = players;
		} else {
			boolean notSame = false;
			for (int i = 0; i < players.size(); i++) {
				if (!players.get(i).toString().equals(this.players.get(i).toString())) {
					notSame = true;
				}
			}
			if (notSame) {
				changePlayersLabels(players);
				this.players = players;
			}
		}
	}

	@Override
	public void giveResults(ArrayList<Integer> results) {
		this.results = results;
		
	}
}
