package org.example;

import java.util.*;

public class App {
    static Scanner in = new Scanner(System.in);
    //Array för att lagra elpriserna
    static final int hours = 24;
    static final int[] elpriser = new int[hours];
    //static int[] elpriser = new int[24];
    static char alternativ;

    public static void main(String[] args) {


        while (true) {
            skrivMenu();

            //Tar emot användarens input
            alternativ = in.next().charAt(0);

            switch (alternativ) {
                case '1': //Inmatning
                    inmatning();
                    break;

                case '2':
                    statistik();
                    break;

                case '3':
                    sortera();
                    break;

                case '4':
                    laddningsTid();
                    break;

                case 'e':
                case 'E':
                    //in.close();
                    System.out.println("Avslutar programmet...\n");
                    return; //Avslutar programmet

                default:
                    System.out.println("Ogiltligt val. Ange ett av alternativen i listan.\r\n");
            }

            in.nextLine();
        }
    }

    private static void skrivMenu() {
        //Skapar menyn som en String
        String menu = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """;
        System.out.println(menu);
    }

    private static void inmatning() {
        System.out.print("Ange elpriser för varje timma på dygnet i hela öre/kWh:\r\n");

        //Loopar igenom alla dygnets timmar
        for (int hour = 0; hour < 24; hour++) {
            System.out.print(hour + "-" + (hour + 1) + ": ");
            if (in.hasNextInt()) {
                int pris = in.nextInt(); //Läser in varje elpris
                elpriser[hour] = pris;
            } else {
                System.out.println("Ikke godkänd inmatning. Ange öre i heltal.\r\n");
                in.next();
                hour--;
            }
        }
    }

    //Räknar ut högsta och lägsta pris under dygnet
    private static void statistik() {
        if (elpriser[0] == 0 && ingaPriser(elpriser)) {
            System.out.println("Inmatning inte gjord än\r\n");
        }

        int minPris = elpriser[0];
        int maxPris = elpriser[0];
        int minTimmar = 0;
        int maxTimmar = 0;
        int totalPris = 0;

        for (int hour = 0; hour < 24; hour++) {
            if (elpriser[hour] < minPris) {
                minPris = elpriser[hour];
                minTimmar = hour;
            }
            if (elpriser[hour] > maxPris) {
                maxPris = elpriser[hour];
                maxTimmar = hour;
            }
            totalPris += elpriser[hour];
        }
        //Räknar ut medel priset under dygnet
        double medelPris = totalPris / 24.0;

        //Formaterar om decimal tecknet till Svensk standard
        String medelPrisFormatted = String.format("%.2f", medelPris).replace('.', ',');

        String response = String.format("""
                Lägsta pris: %02d-%02d, %d öre/kWh
                Högsta pris: %02d-%02d, %d öre/kWh
                Medelpris: %s öre/kWh
                """, minTimmar, (minTimmar + 1) % 24, minPris, maxTimmar, (maxTimmar + 1) % 24, maxPris, medelPrisFormatted);

        // Visar priserna med rätt formattering
        System.out.println(response);
    }

    // Sorterar priserna från dyrast till billigast
    public static void sortera() {
        int[] sortCosts = Arrays.copyOf(elpriser, elpriser.length);
        Arrays.sort(sortCosts);

        boolean[] written = new boolean[hours];

        System.out.print("Dyrast till billigast tider:\r\n");
        for (int i = sortCosts.length - 1; i >= 0; i--) {
            for (int j = 0; j < elpriser.length; j++) {
                if (elpriser[j] == sortCosts[i] && !written[j]) {
                    System.out.printf("%02d-%02d %d öre\n", j, j + 1, elpriser[j]);
                    written[j] = true;
                    break;
                }
            }
        }
    }

    private static void laddningsTid() {
        int minSum = Integer.MAX_VALUE;
        int startTimma = 0;

        for(int i = 0; i <= hours -4; i++ ) {
            int sum = elpriser[i] + elpriser[i + 1] + elpriser[i + 2] + elpriser[i + 3];
            if (sum < minSum) {
                minSum = sum;
                startTimma = i;
            }
        }

        double medelPris = minSum / 4.0;

        String svar = String.format("""
                Påbörja laddning klockan %d
                Medelpris 4h: %.1f öre/kWh
                """, startTimma, medelPris);
        System.out.println(svar);
    }

    private static boolean ingaPriser(int[] elpriser) {
        for (int pris : elpriser) {
            if (pris != 0){
                return false;
            }
        }
        return true;
    }
}