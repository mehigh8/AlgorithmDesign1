import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Crypto {
	static int n;
	static int m;
	static char[] k;
	static char[] s;
	// Numarul de caractere distincte din s
	static long diffLetters = 0;
	static final long mod = 1000000007;

	static long result = 0;

	// Functie care citeste datele din fisierul de intrare
	private static void read() {
		try {
			File in = new File("crypto.in");
			Scanner scan = new Scanner(in);
			n = scan.nextInt();
			m = scan.nextInt();
			scan.nextLine();
			k = new char[n];
			s = new char[m];
			k = scan.nextLine().toCharArray();
			s = scan.nextLine().toCharArray();
			// Calculez numarul de litere distincte.
			boolean[] letters = new boolean[26];
			for (int i = 0; i < m; i++) {
				if (!letters[(int) s[i] - 97]) {
					letters[(int) s[i] - 97] = true;
					diffLetters++;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Read file not found");
			e.printStackTrace();
		}
	}

	// Functie care scire rezultatul in fisierul de iesire
	private static void write() {
		try {
			FileWriter out = new FileWriter("crypto.out");
			out.write(Integer.toString((int) result));
			out.close();
		} catch (IOException e) {
			System.out.println("Write file error");
			e.printStackTrace();
		}
	}

	private static void solve() {
		long[][] dp = new long[n + 1][m + 1];
		// Initializez prima linie cu 0, deoarece in sirul vid nu voi putea gasii niciun sir nevid.
		for (int i = 1; i <= m; i++) {
			dp[0][i] = 0;
		}
		// In cazul in care ambele siruri sunt vide, inseamna ca pot gasi o data sirul vid in
		// sirul vid.
		dp[0][0] = 1;
		// Parcurg prima coloana.
		for (int i = 1; i <= n; i++) {
			// Daca gasesc un '?' inseamna ca fiecare caz precedent va genera un numar de cazuri
			// noi egal cu numarul e litere distincte.
			if (k[i - 1] == '?') {
				dp[i][0] = (dp[i - 1][0] % mod * diffLetters) % mod;
			// Altfel, numarul de cazuri ramane la fel.
			} else {
				dp[i][0] = dp[i - 1][0];
			}
		}
		// Populez matricea.
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				// Daca gasesc un '?' in k, inseamna ca voi avea o data cazul in care cele doua
				// litere curent sunt egale, si restul cazurilor, vor fi cu litere diferite.
				if (k[i - 1] == '?') {
					dp[i][j] = (diffLetters * (dp[i - 1][j] % mod)) % mod + dp[i - 1][j - 1] % mod;
				// Daca cele doua litere curente sunt egale, valoarea va fi valoarea precedenta
				// adunata cu valoarea precedenta pentru sirul s fara caracterul curent,
				// intrucat folosind caracterul curent se vor forma noile subsiruri.
				} else if (k[i - 1] == s[j - 1]) {
					dp[i][j] = dp[i - 1][j] % mod + dp[i - 1][j - 1] % mod;
				// Daca cele doua litere curente sunt diferite, valoarea va fi egala cu cea
				// precedenta.
				} else {
					dp[i][j] = dp[i - 1][j] % mod;
				}
				dp[i][j] %= mod;
			}
		}
		result = dp[n][m];
	}

	public static void main(String[] args) {
		read();
		solve();
		write();
	}
}
