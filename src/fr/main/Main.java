package fr.main;

import java.util.Scanner;

import fr.screen.Screen;
import fr.srv.Serveur;

public class Main {

	public static final int HEIGHT = 768;
	private static final int MARGE_H = 35;
	private static final int MARGE_T = 2;
	private static final int MARGE_W = 6;

	public static final int WIDTH = 1366;

	Screen scr;

	public static void main(String[] args) {

		String question = "Action :\n\t-1 : Host\n\t-2 : Connect";

		Scanner sc = new Scanner(System.in);

		System.out.println(question);

		int c = sc.nextInt();
		do {

			if (c == 1) {
				(new Serveur(5000, WIDTH, HEIGHT)).start();
				Screen.create(WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T, null);
			} else if (c == 2) {
				String ip = "localhost"; // sc.next();
				Screen.create(WIDTH, HEIGHT, MARGE_W, MARGE_H, MARGE_T, ip);

			} else {
				System.out.println("Rien Compris\n\n" + question);
				c = sc.nextInt();
				continue;
			}
		} while (false);
		sc.close();
	}
}