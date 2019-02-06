package fr.main;

import java.util.Scanner;

import fr.appCli.AppliClient;
import fr.screen.AppliScreen;
import fr.screen.Screen;
import fr.server.Server;

public class Main {

	private static final String NAME = "TEST PJS4";

	private static final int MARGE_H = 35;
	private static final int MARGE_T = 2;
	private static final int MARGE_W = 6;

	public static final int WIDTH = 1728;
	public static final int HEIGHT = 972;

	public static final int SCALE = 120;

	public static final String IP = "localhost";
	public static final int PORT = 5000;

	public static void main(String[] args) {

		String question = "Action :\n\t-1 : Host\n\t-2 : Connect";

		Scanner sc = new Scanner(System.in);

		System.out.println(question);

		int c = sc.nextInt();
		do {
			if (c == 1) {
				(new Server(5000, WIDTH, HEIGHT)).start();
				AppliScreen appScr = new AppliClient(NAME, IP, PORT);
				Screen.getInstance(appScr, WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T);
			} else if (c == 2) {
				String ip = "172.19.34.117";// sc.next();
				AppliScreen appScr = new AppliClient(NAME, ip, PORT);
				Screen.getInstance(appScr, WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T);

			} else {
				System.out.println("Rien Compris\n\n" + question);
				c = sc.nextInt();
				continue;
			}
		} while (false);
		sc.close();
	}
}