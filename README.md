# Rosu Mihai Cosmin 323CA

Walsh:
- Explicatii:
 - Se incepe de la matricea de dimensiune N, delimitata de coltul din stanga
 sus (de coordonate 1 1) si cel din dreapta jos (de coordonate N N)
 - La fiecare pas se cauta cadranul, si in functie de cel gasit se reapeleaza
 functia schimband colturile, iar in cazul cadranului din dreapta jos se neaga
 raspunsul.
 - Cand se ajunge la matricea de 1 x 1 se returneaza direct false (echivalent
 cu 0).
 - Dupa ce se gaseste raspunsul este convertit din boolean in int (true -> 1,
 false -> 0).
- Complexitate:
 - La fiecare pas matricea se imparte in 4 => O(log4(N))
 - Se calculeaza de K ori => O(K * log4(N))
 - Complexitatea finala: O(K * log4(N))
 
Statistics:
- Explicatii:
 - Pentru fiecare cuvant din lista se calculeaza, pentru fiecare litera din
 alfabet, frecventa de aparitie si un scor.
 - Scorul este calculat astfel: daca este gasita litera scorul este
 incrementat, iar daca este gasita alta litera scorul este decrementat.
 - Apoi, pentru fiecare litera din alfabet, lista de cuvinte este sortata
 dupa scor, respectiv lungime, in mod descrescator.
 - Dupa aceea, se ia cuvant cu cuvant din lista si se construieste un sir
 final prin concatenarea cuvintelor extrase, pana cand ratia dintre frecventa
 de aparitie a literei curente in sir si lungimea sirului ajunge mai mica sau
 egala decat 0.5.
 - O data ce se termina construirea sirurilor, pentru fiecare litera, raspunsul
 este egal cu maximul cuvintelor concatenate pentru fiecare sir.
- Complexitate:
 - Pentru fiecare cuvant calculez scorul pentru fiecare litera => O(L), unde
 L = lungimea cuvantului
 - Pentru N cuvinte => O(L * N) = O(N)
 - Cand construiesc sirul final, in cel mai rau caz folosesc toate cuvintele
 => O(N)
 - Complexitatea finala: O(N) + O(N) = O(2N) = O(N)
 
Prinel:
- Explicatii:
 - Pentru fiecare target este calculat numarul de operatii folosind programare
 dinamica, astfel: Se parcurge vectorul targets, unde targets[i] = numarul de
 operatii necesare pentru a ajunge la i, pornind de la 1, pana cand se ajunge
 la elementul cautat, si pentru fiecare pozitie, se calculeaza
 targets[i + div] = 1 + targets[i], daca targets[i + div] nu a fost deja
 calculat, caz in care este minimul dintre valoarea deja calculata si
 targets[i] + 1, unde div este orice divizor al lui i.
 - Aceasta metoda este eficienta deoarece calculand numarul de operatii pentru
 un numar, se calculeaza automat si numarul de operatii pentru toate numerele
 mai mici decat x, iar pentru eficienta, in caz ca este necesar numarul de
 operatii pentru un numar mai mare decat cel precedent, se retine ultima
 valoare, continuandu-se de la aceasta.
 - In cazul in care targets[i] este calculat deja, se preia direct valoarea
 fara a mai fi necesar alt calcul.
 - Dupa ce am aflat numarul de operatii pentru fiecare target, calculez numarul
 maxim de puncte ce se pot obtine folosind K operatii printr-o rezolvare ca cea
 a problemei rucsacului, unde pretul obiectelor este reprezentat de puncte,
 obiectele de target-uri si greutatea de numarul de operatii.
 - De asemenea, pentru a nu calcula degeaba, in timp ce se calculeaza numarul
 operatiilor pentru target-uri se retine si suma totala a acestora, iar in
 cazul in care aceasta suma este mai mica decat K, inseamna ca se pot obtine
 toate punctele.
- Complexitate:
 - Consider E = elementul maxim din lista de target-uri.
 - Pentru un numar calculez divizorii, iar apoi ii parcurg
 => O(2 * sqrt(nr)) = O(sqrt(nr)), unde nr este un numar
 - Pentru toate numerele de la 1 la E => O(1) + O(sqrt(2)) + ... + O(sqrt(E))
 - Apoi, pentru a determina numarul maxim de puncte se foloseste o rezolvare
 ca cea a problemei rucsacului => O(N * K)
 - Complexitatea finala: O(1) + O(sqrt(2)) + ... + O(sqrt(E)) + O(N * K), dar
 deoarece E este o constanta => O(N * K)
 
Crypto:
- Explicatii:
 - In cadrul rezolvarii este folosita o matrice dp, unde dp[i][j] = de cate ori
 apare sirul S pana la al j-ulea caracter in sirul K pana la al i-ulea
 caracter.
 - Rezolvarea incepe prin initializarea primei linii cu 0, deoarece in sirul
 vid nu poate fi gasit niciun sir nevid.
 - dp[0][0] este initializat cu 1, deoarece sirul vid poate fi gasit o data in
 sirul vid.
 - Apoi, se populeaza coloana 0, care reprezinta de cate ori se gaseste sirul
 vid in sirul K pana la un anumit caracter. Formula folosita este
 dp[i][0] = dp[i-1][0], in cazul in care caracterul curent din K nu este '?',
 deoarece se pastreaza numarul de cazuri precedent. In schimb, daca se gaseste
 un '?', formula devine dp[i][0] = dp[i-1][0] * diffLetters, unde diffLetters
 este numarul de litere distincte din S. Acest lucru se datoreaza faptului ca
 in locul unui '?' se poate pune orice litera din S, dar daca S contine aceeasi
 litera de multe ori, litera este considerata doar o data intrucat s-ar obtine
 de doua ori acelasi caz. Prin urmare, pentru fiecare caz precedent se obtin
 inca diffLetters cazuri, fiecare caz continand un sir vid.
 - Dupa aceea se incepe popularea matricei, iar la fiecare pas avem 3 cazuri:
 1. Cele doua litere curente din K si S sunt diferite, ceea ce inseamna ca
 numarul de aparitii ramane egal cu cel precedent, din K pana la (i-1), adica
 dp[i][j] = dp[i-1][j].  
 2. Cele doua litere curente sunt egale, caz in care numarul de aparitii va fi
 egal cu numarul de aparitii ale lui S pana la j in K pana la (i-1), adica
 dp[i-1][j], adunat cu numarul de aparitii ale lui S pana la (j-1) in K pana la
 (i-1), adica dp[i-1][j-1], deoarece folosind litera curenta, aparitiile
 acestea vor forma aparitii noi pentru cazul curent. Prin urmare
 dp[i][j] = dp[i-1][j] + dp[i-1][j-1].
 3. Caracterul curent din K este '?', ceea ce inseamna ca poate fi inlocuit cu
 orice litera din S. Asadar, pentru o litera se va obtine cazul 2, iar pentru
 restul cazul 1, ceea ce inseamna ca
 dp[i][j] = dp[i-1][j] + dp[i-1][j-1] + (diffLetters - 1) * dp[i-1][j]
          = dp[i-1][j-1] + diffLetters * dp[i-1][j].
- Complexitate:
 - Deoarece calculul elementelor din matricea dp are loc linie cu linie
 => O(N * L)
 - Complexitate finala: O(N * L), unde N = length K, L = length S
