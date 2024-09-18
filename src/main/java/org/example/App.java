package org.example;

import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //Array för att lagra elpriserna
        int[] elpriser = new int[24];
        char alternativ;



        while(true) {
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
            //Omvandlar menyn till en Byte array
            byte[] menuBytes = menu.getBytes();
            System.out.print(new String(menuBytes));

            //Tar emot användarens input
            alternativ = in.next().charAt(0);

            switch(alternativ) {
                case '1': //Inmatning
                    inmatning(in, elpriser);
                    break;

                case '2':
                    statistik(elpriser);
                    break;

                case 'e':
                case 'E':
                    in.close();
                    return; //Avslutar programmet

                default:
                    System.out.println("Ogiltligt val. Ange ett av alternativen i listan.\r\n");
            }

            in.nextLine();
        }
    }

    private static void inmatning(Scanner in, int[] elpriser) {
        // Återställer alla priser till noll
        Arrays.fill(elpriser, 0);


        //Loopar igenom alla dygnets timmar
        for(int hour = 0; hour < 24; hour++) {
            while(true){
                if (in.hasNextInt()){
                    int pris = in.nextInt();
                    elpriser[hour] = pris;
                    break;
                } else {
                    System.out.println("Ikke godkänd inmatning. Ange öre i heltal.");
                    in.next();
                }
            }
        }
    }

    private static void statistik(int[] elpriser) {
        if (elpriser[0] == 0 && ingaPriser(elpriser)) {
            System.out.println("Inmatning inte gjord än");
        }

        int minPris = elpriser[0];
        int maxPris = elpriser[0];
        int minTimmar = 0;
        int maxTimmar = 0;
        int totalPris = 0;

        for (int hour = 0; hour < 24; hour++) {
            if(elpriser[hour] < minPris) {
                minPris = elpriser[hour];
                minTimmar = hour;
            }
            if(elpriser[hour] > maxPris) {
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
                """,
                minTimmar, (minTimmar + 1) % 24, minPris,
                maxTimmar, (maxTimmar + 1) % 24, maxPris,
                medelPrisFormatted);

        // Display the results with desired formatting
        System.out.println(response);
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