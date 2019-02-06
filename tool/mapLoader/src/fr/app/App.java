package fr.app;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class App {

	private static class Color {
		int r,g,b;

		Color(int c) {
			this((c >> 16) & 0xff, (c >> 8) & 0xff, c & 0xff);
		}

		Color(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Color other = (Color) obj;
			if (b != other.b)
				return false;
			if (g != other.g)
				return false;
			if (r != other.r)
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + b;
			result = prime * result + g;
			result = prime * result + r;
			return result;
		}

		@Override
		public
		String toString() {
			return r + " " + g + " " + b;
		}
	}

	private static class Pos {
		int x;
		int y;

		Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private static HashMap<Color, Character> cS;

	public static void convert(String colorPath, String path, String pathOut) {

		BufferedImage img = getImg(path);
		BufferedWriter f = null;
		String s = "";

		// colorScheme
		cS = getScheme(colorPath);

		try {
			f = new BufferedWriter(new FileWriter(pathOut, false));

			int w = img.getWidth();
			int h = img.getHeight();
			s += (w + " " + h + "\n");
			f.write(s);

			s = getInfos(img, w, h);
			f.write(s);

			s = App.getMap(img, w, h);
			f.write(s);

			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage getImg(String path) {
		BufferedImage img = null;
		File f = null;

		try {
			f = new File(path);
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(e);
		}
		return img;
	}

	private static String getInfos(BufferedImage img, int w, int h) {
		ArrayList<Pos> sp = new ArrayList<>();
		Character spc = 'S';
		Character tmp;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < h; x++) {
				Color c = new Color(img.getRGB(x, y));
				if ((tmp = cS.get(c)) != null && tmp == spc) {
					sp.add(new Pos(x, y));
				}
			}
		}

		String s = String.valueOf(sp.size()) + "\n";
		for (int i = 0; i < sp.size(); i++) {
			s += sp.get(i).x + " " + sp.get(i).y + "\n";
		}
		return s;
	}

	private static String getMap(BufferedImage img, int w, int h) {
		Character ch = null;
		String s = "";
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Color c = new Color(img.getRGB(x, y));
				System.out.println(c);
				System.out.println(cS);
				if ((ch = cS.get(c)) != null)
					s += ch;
			}
			if (y != h - 1)
				s += "\n";
		}
		return s;
	}

	@SuppressWarnings("resource")
	private static HashMap<Color, Character> getScheme(String colorPath) {
		HashMap<Color, Character> cs = null;
		Color c;
		Character tmp;
		try {
			BufferedReader f = new BufferedReader(new FileReader(colorPath));
			cs = new HashMap<>();
			while (f.ready()) {
				tmp = (char) f.read();
				f.read();
				String s = f.readLine();
				String[] ts = s.split(" ");
				c = new Color(Integer.valueOf(ts[0]),Integer.valueOf(ts[1]),Integer.valueOf(ts[2]));
				cs.put(c, tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cs;
	}

	private static Integer setDir(int x, int y, int w, int h) {
		if (y == 0)
			return 0;
		if (x == w - 1)
			return 1;
		if (y == h - 1)
			return 2;
		if (x == 0)
			return 3;
		return null;
	}
}
