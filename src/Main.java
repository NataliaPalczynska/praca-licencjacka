import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

    }


    static int[] rozszerzonyAlgorytmEuklidesa(int a, int b) { //a>=b>0
        int[] output = new int[3];
        int x;
        int y;
        if (b == 0) {
            int d = a;
            x = 1;
            y = 0;
            output[0] = d;
            output[1] = x;
            output[2] = y;
            return output;
        }
        int x2 = 1;
        int x1 = 0;
        int y2 = 0;
        int y1 = 1;
        while (b > 0) {
            int q = (int) Math.floor((double) a / b);
            int r = (a - q * b);
            x = (x2 - q * x1);
            y = (y2 - q * y1);
            a = b;
            b = r;
            x2 = x1;
            x1 = x;
            y2 = y1;
            y1 = y;
        }
        int d = a;
        x = x2;
        y = y2;
        output[0] = d;
        output[1] = x;
        output[2] = y;
        return output;
    }

    static int elementOdwrotny(int a, int n) {
        int[] dxy = rozszerzonyAlgorytmEuklidesa(a, n);
        int d = dxy[0];
        if (d > 1) {
            System.out.println("Brak elementu odwrotnego!");
            return -1;
        }
        int x = dxy[1];
        if (x <= 0)
            x = x + n;
        return x;
    }

    static int symbolLegendrea(int p, int a) { //p - liczba pierwsza, p>=3, 0<=a<p
        int c = (p - 1) / 2;
        int wynik = (potegowanieSzybkie(a, c, p));
        if (wynik == 0)
            return 0;
        else if (wynik == 1)
            return 1;
        else
            return -1;
    }

    static int potegowanieSzybkie(int podstawa, int wykladnik, int liczbaPierwsza) {
        BigInteger result = new BigInteger("1");
        podstawa = podstawa % liczbaPierwsza;

        while (wykladnik > 0) {
            if ((wykladnik & 1) == 1) {
                result = result.multiply(BigInteger.valueOf(podstawa)).mod(BigInteger.valueOf(liczbaPierwsza));
            }
            wykladnik >>= 1;
            podstawa = (podstawa * podstawa) % liczbaPierwsza;
        }

        return result.intValue();
    }

    static int[] pomocniczeWielokrotneDzielenie(int p) {
        int[] wynik = new int[2];
        int a = p - 1;
        int k = 0;
        int m = a;
        while (a % 2 == 0) {
            a = a / 2;
            k++;
            m = a;
        }
        wynik[0] = k;
        wynik[1] = m;
        return wynik;
    }

    static int pomocniczePodnoszenieDoKwadratu(int n, int p) {
        int k2 = 0;
        do {
            k2++;
        } while ((Math.pow(n, Math.pow(2, k2)) % p) != 1);
//        }while(potegowanieSzybkie(n, (int) Math.pow(2,k2),p).intValue() != 1);
        return k2;
    }

    static int[] algorytmTonelliegoShanksa(int a, int p) {
        int[] pierwiastkiKwadratowe = new int[2];
        if (symbolLegendrea(p, a) == -1) {
            System.out.println(a + " nie jest reszta kwadratowa mod" + p);
            return pierwiastkiKwadratowe;
        } else {
            int k = pomocniczeWielokrotneDzielenie(p)[0];
            int m = pomocniczeWielokrotneDzielenie(p)[1];
            int z;
            Random rand = new Random();
            do {
                z = rand.nextInt(p - 1) + 1;
            } while (symbolLegendrea(p, z) != -1);
            int K = k;
            int c = potegowanieSzybkie(z, m, p);
            int n = potegowanieSzybkie(a, m, p);
            int r = potegowanieSzybkie(a, (m + 1) / 2, p);

            do {
                if (n % p == 0) {
                    return pierwiastkiKwadratowe;
                } else if (n % p == 1) {
                    pierwiastkiKwadratowe[0] = r;
                    pierwiastkiKwadratowe[1] = p - r;
                    return pierwiastkiKwadratowe;
                } else {
                    int k2 = pomocniczePodnoszenieDoKwadratu(n, p);
                    int b = potegowanieSzybkie(c, (int) Math.pow(2, K - k2 - 1), p);
                    K = k2;
                    c = (int) (Math.pow(b, 2) % p);
                    n = (c * n) % p;
                    r = (b * r) % p;
                }
            } while (true);
        }
    }

    static int algorytmBabyStepGiantStep(int a, int b, int n, int p) {
        int m = (int) Math.ceil(Math.sqrt(n));
        int[][] tabela = new int[m][2];
        for (int j = 0; j < m; j++) {
            tabela[j][0] = j;
            tabela[j][1] = potegowanieSzybkie(a, j, p);
        }
        int odwrotnoscA = elementOdwrotny(a, p);
        int am = potegowanieSzybkie(odwrotnoscA, m, p);
        int c = b;
        do {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if (tabela[j][1] == c) {
                        return i * m + j;
                    }
                }
                c = (c * am) % p;
            }
        } while (true);
    }
}