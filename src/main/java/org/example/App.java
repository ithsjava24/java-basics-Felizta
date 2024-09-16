package org.example;

import java.sql.SQLOutput;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //Array för att lagra elpriserna
        ArrayList<Integer> elpriser = new ArrayList<>();
        char alternativ;

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

        while(true) {
            System.out.print(new String(menuBytes));

            //Tar emot användarens input
            alternativ = in.next().charAt(0);

            switch(alternativ) {
                case '1': //Inmatning
                    elpriser.clear(); //Tömmer listan på gamla numemr innan nya matas in
                    System.out.println("Skriv in elpriser för varje timme (kl 00.00-01.00 till 23-00) i hela öre: \r\n");

                    //Loopar igenom alla dygnets timmar
                    for(int hour = 0; hour < 24; hour++) {
                        boolean korrektInmatning = false;

                        do{
                            try {
                                System.out.print(String.format("%02d-%02d: ", hour, (hour + 1) % 24));
                                int pris = Integer.parseInt(in.next());
                                if (pris > 0) {
                                    elpriser.add(pris);
                                    korrektInmatning = true;
                                } else {
                                    System.out.println("Angivet pris måste vara positivt heltal i öre. Skriv igen\r\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Fel inmatning. Ange ett heltal.\r\n");
                            }
                        } while(!korrektInmatning);
                    }
                    System.out.println("Elpriser för hela dygnet angivna.\r\n");
                    break;

                case 'e':
                case 'E':
                    in.close();
                    return;

                default:
                    System.out.println("Ogiltligt val. Ange ett av alternativen i listan.\r\n");
                    }
        }
    }
}