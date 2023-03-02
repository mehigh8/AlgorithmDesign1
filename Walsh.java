import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Walsh {
	// Clasa folosita pentru a retine coordonatele unui punct.
	private static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		// Doua puncte sunt egale daca au coordonatele egale.
		public boolean isEqual(Point point) {
			return point.x == this.x && point.y == this.y;
		}
	}

	static int n;
	static int k;
	static ArrayList<Point> points = new ArrayList<>();

	static ArrayList<Integer> results = new ArrayList<>();

	// Functie care citeste datele din fisierul de intrare
	private static void read() {
		try {
			File in = new File("walsh.in");
			Scanner scan = new Scanner(in);
			n = scan.nextInt();
			k = scan.nextInt();
			for (int i = 0; i < k; i++) {
				points.add(new Point(scan.nextInt(), scan.nextInt()));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Read file not found");
			e.printStackTrace();
		}
	}

	// Functie care scrie rezultatele in fisierul de iesire
	private static void write() {
		try {
			FileWriter out = new FileWriter("walsh.out");
			for (Integer result : results) {
				out.write(result.toString() + "\n");
			}
			out.close();
		} catch (IOException e) {
			System.out.println("Write file error");
			e.printStackTrace();
		}
	}

	// Functie care gaseste valoarea unui punct, in format boolean
	private static boolean findPair(Point point, Point leftUp, Point rightDown) {
		// Daca am ajuns la matricea de 1 x 1 inseamna ca pot intoarce direct false (0).
		if (leftUp.isEqual(rightDown)) {
			return false;
		} else {
			if (point.x <= leftUp.x + (rightDown.x - leftUp.x) / 2) {
				// Verific daca punctul se afla in cadranul din stanga sus.
				if (point.y <= leftUp.y + (rightDown.y - leftUp.y) / 2) {
					return findPair(point, leftUp,
							new Point(leftUp.x + (rightDown.x - leftUp.x) / 2,
							leftUp.y + (rightDown.y - leftUp.y) / 2));
				// Verific daca punctul se afla in cadranul din dreapta sus.
				} else {
					return findPair(point, new Point(leftUp.x,
							leftUp.y + (rightDown.y - leftUp.y) / 2 + 1),
							new Point(leftUp.x + (rightDown.x - leftUp.x) / 2, rightDown.y));
				}
			} else {
				// Verific daca punctul se afla in cadranul din stanga jos.
				if (point.y <= leftUp.y + (rightDown.y - leftUp.y) / 2) {
					return findPair(point, new Point(leftUp.x + (rightDown.x - leftUp.x) / 2 + 1,
							leftUp.y), new Point(rightDown.x,
							leftUp.y + (rightDown.y - leftUp.y) / 2));
				// Verific daca punctul se afla in cadranul din dreapta jos.
				} else {
					return !findPair(point, new Point(leftUp.x + (rightDown.x - leftUp.x) / 2 + 1,
							leftUp.y + (rightDown.y - leftUp.y) / 2 + 1), rightDown);
				}
			}
		}
	}

	// Functie gaseste valoarea fiecarui punct
	private static void solve() {
		for (Point point : points) {
			results.add((findPair(point, new Point(1, 1), new Point(n, n)) ? 1 : 0));
		}
	}

	public static void main(String[] args) {
		read();
		solve();
		write();
	}
}
