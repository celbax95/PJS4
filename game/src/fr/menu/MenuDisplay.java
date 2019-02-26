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
import javax.swing.border.Border;
//import javax.swing.Timer;

import fr.client.Client;
import fr.server.Server;

public class MenuDisplay {
	private static MenuDisplay menu;
	
	private static String TITLE;
	private static final Color BACKGROUND_COLOR = new Color(50, 54, 57);
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(59, 89, 182);
	private static final Color ARROWS_BACKGROUND_COLOR = new Color(80, 100, 190);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.BOLD, 20);
	private static final int SIZE_BUTTON_X = 210, SIZE_BUTTON_Y = 45; 
	private static final int NB_MAX_PLAYERS = 4, NB_MIN_PLAYERS = 2;
	
	private static final String IP = "localhost";
	private static final int PORT = 5000;
	private ArrayList<Client> clients;
	private Client cli;
	
	private JFrame f;
	private JPanel p;
	private JLabel nbPlayersLabel;
	private JTextField ipTF;
	private JButton host, join, back, decNbPlayers, incNbPlayers, mapL, mapR, search, start, connect;
	private ArrayList<JButton> bList;
	private ArrayList<JComponent> cHList;
	private ArrayList<JComponent> cSList;
	private ArrayList<JComponent> cJList;
	private ArrayList<JComponent> cCList;
	private int hj;
	private int nbPlayers;
	private boolean sawIpAdress;
	
	private Server server;
	
	public static MenuDisplay getInstance(String title, int rw) {
		if(MenuDisplay.menu == null) {
			MenuDisplay.menu = new MenuDisplay(title, rw);
			return MenuDisplay.menu;
		}
		else { return MenuDisplay.menu; }
	}
	
	private MenuDisplay(String title, int rw) {
		MenuDisplay.TITLE = title;
		this.f = new JFrame(title);
		this.f.setPreferredSize(new Dimension(rw, rw));
		this.f.setBackground(BACKGROUND_COLOR);
		this.p = new MenuPanel(rw);
		
		this.hj = 0;
		this.nbPlayers = 3;
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
		this.ipTF = new JTextField("Ip Adress");
		bList = new ArrayList<JButton>(Arrays.asList(host, join, back, decNbPlayers, incNbPlayers, mapL, mapR, search, start, connect));
		cHList = new ArrayList<JComponent>(Arrays.asList(search, decNbPlayers, incNbPlayers, mapL, mapR, nbPlayersLabel));
		cJList = new ArrayList<JComponent>(Arrays.asList(ipTF, connect));		
		cSList = new ArrayList<JComponent>(Arrays.asList(start));
		cCList = new ArrayList<JComponent>(Arrays.asList());
		
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
		this.ipTF.setBounds(145, 140, SIZE_BUTTON_X, SIZE_BUTTON_Y);
		
		setFocusPaintedButtons();
		
		this.nbPlayersLabel.setOpaque(true);
		this.ipTF.setHorizontalAlignment(JTextField.CENTER);
		
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
		this.ipTF.setBackground(BUTTON_BACKGROUND_COLOR);
		
		
		setFontButtons();
		this.nbPlayersLabel.setFont(BUTTON_FONT);
		this.ipTF.setFont(BUTTON_FONT);
		
		nbPlayersLabel.setBorder(BorderFactory.createLineBorder(BUTTON_BACKGROUND_COLOR));
		
		setForegroundButtons();
		this.nbPlayersLabel.setForeground(Color.white);
		this.ipTF.setForeground(Color.gray);
	}
	
	public void init() {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hj = 1;
				showComponents();
				host.setVisible(false);
				join.setVisible(false);
				back.setVisible(true);
			}
		});
		this.join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hj = 2;
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
				switch(hj) {
					case 1:
						hj = 0;
						break;
					case 2:
						hj = 0;
						ipTF.setForeground(Color.gray);
						ipTF.setText("Ip Adress");
						sawIpAdress = false;
						break;
					case 3:
					try {
						cli.close();
						server.finalize();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
						hj = 1;
						break;
					case 4:
						cli.close();
						hj = 2;
						break;
				}
				showComponents();
			}
		});
		this.search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hideComponents();
				hj = 3;
				showComponents();
				server = new Server(TITLE,PORT, nbPlayers);
				server.start();
				cli = new Client(IP,PORT, MenuDisplay.menu);
				
			}
		});
		this.connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cli = new Client(IP,PORT, MenuDisplay.menu);
				if (cli.getSocket()==null) {
					return;
				}
				hideComponents();
				hj = 4;
				showComponents();
				
				
			}
		});
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(server.getNbPlayers() < Integer.parseInt(nbPlayersLabel.getText())) {	
					return;
				}
				hideWindow();
				server.setGameOn();
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
		this.ipTF.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		    	if(!sawIpAdress) {
		    		ipTF.setText("");
		    		ipTF.setForeground(Color.white);
		    		sawIpAdress = true;
		    	}
		    }
		});;
		
		
		this.f.add(host);
		this.f.add(join);
		this.f.add(back);
		for(JComponent jc : cHList) {
			this.f.getContentPane().add(jc);
		}
		for(JComponent jc : cJList) {
			this.f.getContentPane().add(jc);
		}
		for(JComponent jc : cSList) {
			this.f.getContentPane().add(jc);
		}
		this.f.add(p);
		this.f.pack();
		this.f.setVisible(true);
		this.f.setResizable(false);
		
		back.setVisible(false);
		hideComponents();
	}
	
	public void refresh() {
		this.f.repaint();
	}

	public void display() {
		this.init();
	}
	
	public void hideWindow() {
		f.setVisible(false);
	}
	
	private void hideComponents() {
		if(hj == 0) {
			for(JComponent jc : cHList) {
				jc.setVisible(false);
			}
			for(JComponent jc : cJList) {
				jc.setVisible(false);
			}
			for(JComponent jc : cSList) {
				jc.setVisible(false);
			}
		}
		else if(hj == 1) {
			for(JComponent jc : cHList) {
				jc.setVisible(false);
			}
		}
		else if(hj == 2) {
			for(JComponent jc : cJList) {
				jc.setVisible(false);
			}
		}
		else if(hj == 3) {
			for(JComponent jc : cSList) {
				jc.setVisible(false);
			}
		}
		else if(hj == 4) {
			for(JComponent jc : cCList) {
				jc.setVisible(false);
			}
		}
		else { /* ÃƒÂ  faire */ }
		
	}
	private void showComponents() {
		if(hj ==0) {
			host.setVisible(true);
			join.setVisible(true);
			back.setVisible(false);
		}
		else if(hj == 1) {
			for(JComponent jc : cHList) {
				jc.setVisible(true);
			}
		}
		else if(hj == 2) {
			for(JComponent jc : cJList) {
				jc.setVisible(true);
			}
		}
		else if(hj == 3) {
			for(JComponent jc : cSList) {
				jc.setVisible(true);
			}
		}
		else if(hj == 4) {
			for(JComponent jc : cCList) {
				jc.setVisible(true);
			}
		}
		else { /* ÃƒÂ  faire */ }
	}
	private void setFocusPaintedButtons() {
		for(JButton jb : bList) {
			jb.setFocusPainted(false);
		}
	}
	private void setFontButtons() {
		for(JButton jb : bList) {
			jb.setFont(BUTTON_FONT);
		}
	}
	private void setForegroundButtons() {
		for(JButton jb : bList) {
			jb.setForeground(Color.white);
		}
	}
}
