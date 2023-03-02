import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Prinel {
	// Clasa care retine numarul de operatii necesare pentru a ajunge la target, si numarul de
	// puncte asociate target-ului.
	private static class Pair {
		int operations;
		int points;

		public Pair(int value, int points) {
			this.points = points;
			// Daca nu a fost deja calculat numarul de operatii pentru valoare, voi calcula pentru
			// toate numerele de la ultimul calculat pana la valoarea curenta.
			if (lastTarget < value) {
				calculateOperations(value);
			}
			// Altfel, preiau rezultatul din vector.
			operations = Prinel.targets[value];
		}
		// Functie care calculeaza numarul de operatii pentru numerele de la ultimul calculat pana
		// la valoarea primita.
		private void calculateOperations(int value) {
			for (int start = lastTarget; start <= value; start++) {
				// Caut divizorii numarului curent.
				ArrayList<Integer> divisors = new ArrayList<>();
				for (int i = 1; i * i <= start; i++) {
					if (start % i == 0) {
						divisors.add(i);
						if (i * i != start) {
							divisors.add(start / i);
						}
					}
				}
				// Pentru fiecare divizor actualizez, daca este cazul, numarul de operatii
				// necesare pentru a ajunge la numar + divizor.
				for (Integer div : divisors) {
					if (start + div <= 100000) {
						if (Prinel.targets[start + div] == 0) {
							Prinel.targets[start + div] = Prinel.targets[start] + 1;
						} else {
							Prinel.targets[start + div] = Math.min(Prinel.targets[start + div],
									Prinel.targets[start] + 1);
						}
					}
				}
			}
			// Actualizez ultima valoare pentru care s-a calculat numarul de operatii.
			lastTarget = value;
		}
	}
	// Vector care retine numarul de operatii
	public static int[] targets = new int[100001];
	// Ultimul numar pentru care s-a calculat numarul de operatii.
	public static int lastTarget = 1;

	static int n;
	static int k;
	static int totalOperations = 0;
	static int totalPoints = 0;
	static ArrayList<Pair> numbers = new ArrayList<>();

	static Integer result = 0;

	// Functie care citeste datele din fisierul de intrare
	private static void read() {
		try {
			File in = new File("prinel.in");
			Scanner scan = new Scanner(in);
			n = scan.nextInt();
			k = scan.nextInt();
			for (int i = 0; i < n; i++) {
				numbers.add(new Pair(scan.nextInt(), 0));
			}
			for (int i = 0; i < n; i++) {
				numbers.get(i).points = scan.nextInt();
				totalOperations += numbers.get(i).operations;
				totalPoints += numbers.get(i).points;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Read file not found");
			e.printStackTrace();
		}
	}

	// Functie care scrie rezultatul in fisierul de iesire
	private static void write() {
		try {
			FileWriter out = new FileWriter("prinel.out");
			out.write(result.toString());
			//System.out.println(Arrays.toString(numbers.toArray()));
			out.close();
		} catch (IOException e) {
			System.out.println("Write file error");
			e.printStackTrace();
		}
	}

	// Functie care calculeaza numarul maxim de puncte ce se pot castiga pentru un numar de
	// operatii disponibile.
	private static void solve() {
		// Daca numarul de operatii disponibile este mai mare decat numarul total de operatii
		// ale target-urilor, atunci se pot castiga toate punctele.
		if (k >= totalOperations) {
			result = totalPoints;
			// Altfel, se aplica o rezolvare ca cea a problemei rucsacului.
		} else {
			int[][] dp = new int[n + 1][k + 1];
			// Parcurg target-urile.
			for (int i = 1; i <= n; i++) {
				// Verific pentru fiecare numar de operatii posibil.
				for (int j = 0; j <= k; j++) {
					Pair pair = numbers.get(i - 1);
					// Daca nu am destule operatii, inseamna ca am la fel de multe puncte ca la
					// pasul precedent.
					if (j - pair.operations < 0) {
						dp[i][j] = dp[i - 1][j];
					// Altfel, numarul de puncte este maximul dintre punctele de la pasul precedent
					// si punctele obtinute, daca as aloca operatii pentru target-ul curent.
					} else {
						dp[i][j] = Math.max(dp[i - 1][j],
								pair.points + dp[i - 1][j - pair.operations]);
					}
				}
			}
			result = dp[n][k];
		}
	}

	public static void main(String[] args) {
		read();
		solve();
		write();
	}
}
