package net.wodie;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCount {
    static HashMap<String, Integer> wordHashMap = new HashMap<>();
    static long anzahlZeilen = 0;
    static long anzahlZeichen = 0;

    public static void fillWordHashMap(String line) {
        //  Zerlegt eine übergebene Zeile (String) in ein Array aus Wörtern.
        //  Danacch wird der Inhalt des Arrays in eine public Hashmap übergebben
        //  in der alle vorkommenden Wörter und deren Häufigkeit gespeichert wird.
        String[] wordArray = line.split("[^a-zA-ZäöüßÄÖÜ']+");
        for (String word : wordArray) {
            if (word.length() > 0) {
//                if (count != null) {
//                    wordHashMap.put(word, count + 1);
//                } else {
//                    wordHashMap.put(word, 1);
//                }
                wordHashMap.merge(word, 1, Integer::sum);
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        BufferedReader bufferedReader = null;
        //Der Pfad zur Textdatei
        String filePath = "src/main/resources/beispiel.txt";
//        String filePath = args[0];
        File file = new File(filePath);
        try {
            //Der BufferedReader erwartet einen FileReader.
            //Diesen kann man im Konstruktoraufruf erzeugen.
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            //null wird bei EOF oder Fehler zurueckgegeben
            while (null != (line = bufferedReader.readLine())) {
                anzahlZeilen++;
                anzahlZeichen += line.length();
//              Spezialaufruf für die Lutherbibel (Ausschluss der Quellenbezeichnung)
                fillWordHashMap(line.substring(4).toLowerCase());
//                fillWordHashMap(line.toLowerCase());
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long zeit1 = System.currentTimeMillis() - startTime;

//        Code für Debug
//        wordHM.forEach((k, v) -> {
//            if (v > 0) System.out.println("Key: " + k + "  ----- Value: " + v);
//        });
        System.out.printf("Anzahl der eingelesenen Zeichen: %d %n", anzahlZeichen);
        System.out.printf("Anzahl der verarbeiteten Zeilen: %d %n", anzahlZeilen);
        System.out.printf("Anzahl der unterschiedlichen Wörter: %d %n", wordHashMap.size());
        System.out.printf("Laufzeit bis zum Ende des Einlesens: %d (in msec) %n",zeit1);
        System.out.printf("Laufzeit bis zum Ende der Ausgabe: %d (in msec) %n",System.currentTimeMillis() - startTime);

        wordHashMap.entrySet()
                .stream()
                .filter(x->x.getKey().equals("und"))
//                .filter(x->x.getValue() > 4000)
//                .filter(x->x.getValue() == 1)
//                .sorted(Map.Entry.<String, Integer>comparingByKey())
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }
}
