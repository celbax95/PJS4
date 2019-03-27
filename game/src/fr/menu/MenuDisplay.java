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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import fr.gameLauncher.GameLauncher;
import fr.gameLauncher.Menu;

public class MenuDisplay implements Menu{
	
	
	
	private static MenuDisplay menu;
	
	private static String TITLE = "TEST PJS4";
	private static int windowSize = 500;
	
	private static final Color BACKGROUND_COLOR = new Color(50, 54, 57);
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(59, 89, 182);
	private static final Color ARROWS_BACKGROUND_COLOR = new Color(80, 100, 190);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.BOLD, 20);
	private static final int SIZE_BUTTON_X = 210, SIZE_BUTTON_Y = 45; 
	private static final int NB_MAX_PLAYERS = 4, NB_MIN_PLAYERS = 2, NB_INIT_PLAYERS = 3;
	
	private static final String IP = "localhost";
	private static final int PORT = 5000;
	
	static {
		GameLauncher.setMenu(MenuDisplay.getInstance());
	}
	
	private JFrame frame;
	private JPanel panel;
	private JLabel nbPlayersLabel;
	private JTextField ipTextField;
	private JButton host, join, back, decNbPlayers, incNbPlayers, mapL, mapR, search, start, connect;
	private ArrayList<JButton> buttonList;
	private ArrayList<JComponent> componentHostList;
	private ArrayList<JComponent> componentStartList;
	private ArrayList<JComponent> componentJoinList;
	private ArrayList<JComponent> componentConnectList;
	private int menuPosition;
	private int nbPlayers;
	private boolean sawIpAdress;
	
	/**
	 * Récupère l'instance unique du menu
	 */
	public static Menu getInstance() {
		if(MenuDisplay.menu == null) {
			MenuDisplay.menu = new MenuDisplay();
			return MenuDisplay.menu;
		}
		else { return MenuDisplay.menu; }
	}
	
	/**
	 * Retour au menu après une partie
	 */
	public void reset() {
		hideComponents();
		
		if(menuPosition==3) {
			nbPlayers = NB_INIT_PLAYERS;
			nbPlayersLabel.setText(String.valueOf(nbPlayers));
			menuPosition = 1;
		}
		else if(menuPosition==4) {
			menuPosition = 2;
			connect.requestFocusInWindow();
		}
		showComponents();
		MenuDisplay.menu.frame.setVisible(true);
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
		
		this.menuPosition = 0;
		this.nbPlayers = NB_INIT_PLAYERS;
		this.sawIpAdress = false;
		
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
		this.nbPlayersLabel = new JLabel(String.valueOf(nbPlayers),SwingConstants.CENTER);
		this.ipTextField = new JTextField("Ip Adress");
		buttonList = new ArrayList<JButton>(Arrays.asList(host, join, back, decNbPlayers, incNbPlayers, mapL, mapR, search, start, connect));
		componentHostList = new ArrayList<JComponent>(Arrays.asList(search, decNbPlayers, incNbPlayers, mapL, mapR, nbPlayersLabel));
		componentJoinList = new ArrayList<JComponent>(Arrays.asList(ipTextField, connect));		
		componentStartList = new ArrayList<JComponent>(Arrays.asList(start));
		componentConnectList = new ArrayList<JComponent>(Arrays.asList());
		
		this.host.setBounds(145, 110, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.join.setBounds(145, 260, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.back.setBounds(260, 400, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.search.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.start.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.connect.setBounds(145, 335, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		this.decNbPlayers.setBounds(145-SIZE_BUTTON_X/3, 110, SIZE_BUTTON_X/3, SIZE_BUTTON_Y);
		this.incNbPlayers.setBounds(145+SIZE_BUTTON_X, 110, SIZE_BUTTON_X/3, SIZE_BUTTON_Y);
		this.mapL.setBounds(145-SIZE_BUTTON_X/3, 110+SIZE_BUTTON_Y*2, SIZE_BUTTON_X/3, SIZE_BUTTON_Y);
		this.mapR.setBounds(145+SIZE_BUTTON_X, 110+SIZE_BUTTON_Y*2, SIZE_BUTTON_X/3, SIZE_BUTTON_Y);
		this.nbPlayersLabel.setBounds(130+SIZE_BUTTON_X/3, 110, SIZE_BUTTON_X/2, SIZE_BUTTON_Y);
		this.ipTextField.setBounds(145, 140, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		
		setFocusPaintedButtons();
		
		this.nbPlayersLabel.setOpaque(true);
		this.ipTextField.setHorizontalAlignment(JTextField.CENTER);
		
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
		this.ipTextField.setBackground(BUTTON_BACKGROUND_COLOR);
		
		
		setFontButtons();
		this.nbPlayersLabel.setFont(BUTTON_FONT);
		this.ipTextField.setFont(BUTTON_FONT);
		
		nbPlayersLabel.setBorder(BorderFactory.createLineBorder(BUTTON_BACKGROUND_COLOR));
		
		setForegroundButtons();
		this.nbPlayersLabel.setForeground(Color.white);
		this.ipTextField.setForeground(Color.gray);
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
				showComponents();
				host.setVisible(false);
				join.setVisible(false);
				back.setVisible(true);
			}
		});
		this.join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPosition = 2;
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
				switch(menuPosition) {
					case 1:
						nbPlayers = NB_INIT_PLAYERS;
						nbPlayersLabel.setText(String.valueOf(nbPlayers));
						menuPosition = 0;
						break;
					case 2:
						menuPosition = 0;
						ipTextField.setForeground(Color.gray);
						ipTextField.setText("Ip Adress");
						sawIpAdress = false;
						break;
					case 3:
						GameLauncher.clientClose();
						GameLauncher.serverClose();
						nbPlayers = NB_INIT_PLAYERS;
						nbPlayersLabel.setText(String.valueOf(nbPlayers));
						menuPosition = 1;
						break;
					case 4:
						GameLauncher.clientClose();
						menuPosition = 2;
						break;
				}
				showComponents();
			}
		});
		this.search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideComponents();
				menuPosition = 3;
				showComponents();
				GameLauncher.createServer(TITLE,PORT, nbPlayers);
				GameLauncher.serverStart();
				try {
					GameLauncher.createClient(IP,PORT, MenuDisplay.menu);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		this.connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GameLauncher.createClient(IP,PORT, MenuDisplay.menu);
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
				if(GameLauncher.getServerNbPlayers() < Integer.parseInt(nbPlayersLabel.getText())) {	
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
				if(nbPlayers==NB_MIN_PLAYERS) {
					decNbPlayers.setVisible(false);
				}
				else {
					decNbPlayers.setVisible(true);
					if(nbPlayers!=NB_MAX_PLAYERS) {
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
				if(nbPlayers==NB_MAX_PLAYERS) {
					incNbPlayers.setVisible(false);
				}
				else {
					incNbPlayers.setVisible(true);
					if(nbPlayers!=NB_MIN_PLAYERS) {
						decNbPlayers.setVisible(true);
					}
				}
			}
		});
		this.ipTextField.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		    	if(!sawIpAdress) {
		    		ipTextField.setText("");
		    		ipTextField.setForeground(Color.white);
		    		sawIpAdress = true;
		    	}
		    }
		});;
		
		
		this.frame.add(host);
		this.frame.add(join);
		this.frame.add(back);
		for(JComponent jc : componentHostList) {
			this.frame.getContentPane().add(jc);
		}
		for(JComponent jc : componentJoinList) {
			this.frame.getContentPane().add(jc);
		}
		for(JComponent jc : componentStartList) {
			this.frame.getContentPane().add(jc);
		}
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

	public void display() {
		this.init();
	}
	
	public void hideWindow() {
		frame.setVisible(false);
	}
	
	/**
	 * Cache les composants du menu en fonction de la position dans l'arborescence du menu
	 */
	private void hideComponents() {
		if(menuPosition == 0) {
			for(JComponent jc : componentHostList) {
				jc.setVisible(false);
			}
			for(JComponent jc : componentJoinList) {
				jc.setVisible(false);
			}
			for(JComponent jc : componentStartList) {
				jc.setVisible(false);
			}
		}
		else if(menuPosition == 1) {
			for(JComponent jc : componentHostList) {
				jc.setVisible(false);
			}
		}
		else if(menuPosition == 2) {
			for(JComponent jc : componentJoinList) {
				jc.setVisible(false);
			}
		}
		else if(menuPosition == 3) {
			for(JComponent jc : componentStartList) {
				jc.setVisible(false);
			}
		}
		else if(menuPosition == 4) {
			for(JComponent jc : componentConnectList) {
				jc.setVisible(false);
			}
		}
		else { /* ÃƒÆ’Ã‚Â  faire */ }
		
	}
	/**
	 * Montre les composants du menu en fonction de la position dans l'arborescence du menu
	 */
	private void showComponents() {
		if(menuPosition ==0) {
			host.setVisible(true);
			join.setVisible(true);
			back.setVisible(false);
		}
		else if(menuPosition == 1) {
			for(JComponent jc : componentHostList) {
				jc.setVisible(true);
			}
		}
		else if(menuPosition == 2) {
			for(JComponent jc : componentJoinList) {
				jc.setVisible(true);
			}
		}
		else if(menuPosition == 3) {
			for(JComponent jc : componentStartList) {
				jc.setVisible(true);
			}
		}
		else if(menuPosition == 4) {
			for(JComponent jc : componentConnectList) {
				jc.setVisible(true);
			}
		}
		else { /* éventuellement à rajouter */ }
	}
	private void setFocusPaintedButtons() {
		for(JButton jb : buttonList) {
			jb.setFocusPainted(false);
		}
	}
	private void setFontButtons() {
		for(JButton jb : buttonList) {
			jb.setFont(BUTTON_FONT);
		}
	}
	private void setForegroundButtons() {
		for(JButton jb : buttonList) {
			jb.setForeground(Color.white);
		}
	}
}
