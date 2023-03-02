import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Statistics {
	// Clasa care retine o pereche (cuvant, statisticile acelui cuvant)
	private static class Pair {
		String word;
		Stats stats;

		public Pair(String word, Stats stats) {
			this.word = word;
			this.stats = stats;
		}
	}
	// Clasa care retine scorul si frecventa pentru fiecare litera, pentru un cuvant.
	private static class Stats {
		int[] score = new int[26];
		int[] freq = new int[26];

		public Stats(String word) {
			// Calculez scorul si frecventa.
			for (char c : word.toCharArray()) {
				score[(int) c - 97]++;
				freq[(int) c - 97]++;
				for (int i = 0; i < 26; i++) {
					if (i != ((int) c - 97)) {
						score[i]--;
					}
				}
			}
		}
	}
	// Comparator folosit pentru sortarea listei de cuvinte
	private static class LetterComparator implements Comparator<Pair> {
		// Litera dupa care are loc sortarea
		int letter = 0;

		public LetterComparator(int index) {
			this.letter = index;
		}

		@Override
		public int compare(Pair o1, Pair o2) {
			// Daca scorurile sunt egale, sortarea are loc dupa lungime, descrescator.
			if (Double.compare(o2.stats.score[letter], o1.stats.score[letter]) == 0) {
				return Integer.compare(o2.word.length(), o1.word.length());
			}
			// Altfel, sortarea are loc dupa scor, descrescator.
			return Double.compare(o2.stats.score[letter], o1.stats.score[letter]);
		}
	}

	static int n;
	static ArrayList<Pair> words = new ArrayList<>();

	static Integer result = -1;

	// Functie care citeste datele din fisierul de intrare
	private static void read() {
		try {
			File in = new File("statistics.in");
			Scanner scan = new Scanner(in);
			n = Integer.parseInt(scan.nextLine());
			for (int i = 0; i < n; i++) {
				String word = scan.nextLine();
				words.add(new Pair(word, new Stats(word)));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Read file not found");
			e.printStackTrace();
		}
	}

	// Functie care scrie raspunsul in fisierul de iesire
	private static void write() {
		try {
			FileWriter out = new FileWriter("statistics.out");
			out.write(result.toString());
			out.close();
		} catch (IOException e) {
			System.out.println("Write file error");
			e.printStackTrace();
		}
	}

	// Functie care cauta numarul maxim de cuvinte ce pot fi concatenate cu o litera dominanta,
	// pentru fiecare litera din alfabet.
	private static void solve() {
		int i = 0;
		while (i < 26) {
			// Rezultatul curent
			int currentRes = 0;
			// Frecventa curenta
			int currentFreq = 0;
			StringBuilder currentString = new StringBuilder();
			// Sortez lista de cuvinte dupa litera curenta.
			words.sort(new LetterComparator(i));
			for (Pair pair : words) {
				// Concatenez cuvantul curent la sirul final.
				currentString.append(pair.word);
				currentFreq += pair.stats.freq[i];
				// Daca ratia sirului final ajunge mai mica sau egala cu 0.5 opresc cautarea
				// pentru litera curenta.
				if (Double.compare((double) currentFreq / currentString.length(), 0.5d) <= 0) {
					break;
				}
				currentRes++;
			}

			if (currentRes != 0 && currentRes > result) {
				result = currentRes;
			}
			i++;
		}
	}

	public static void main(String[] args) {
		read();
		solve();
		write();
	}
}
