package net.wodie;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public static ArrayList<String> wordList = new ArrayList<>();
    public static HashMap<String, Integer> wordHashMap = new HashMap<>();

    //  Zerlegt eine übergebene Zeile (String) in ein Array aus Wörtern.
    //  Danacch wird der Inhalt des Arrays in eine public Hashmap übergebben
    //  in der alle vorkommenden Wörter und deren Häufigkeit gespeichert wird.
    public static void toWords(String line) {
        String[] wordArray = line.split("[^a-zA-ZäöüßÄÖÜ']+");
        for (String word : wordArray) {
            if (word.length() > 0) {

                Integer count = wordHashMap.get(word);
                if (count != null) {
                    wordHashMap.put(word, count + 1);
                } else {
                    wordHashMap.put(word, 1);
                }
            }
        }
    }


    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        long anzahlZeilen = 0;
        long anzahlWoerter = 0;
        long anzahlZeichen = 0;

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
                toWords(line.substring(4).toLowerCase());
//                toWords(line.toLowerCase());
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
                .filter(x->x.getKey().equals("gott"))
//                .filter(x->x.getValue() > 4000)
//                .filter(x->x.getValue() == 1)
//                .sorted(Map.Entry.<String, Integer>comparingByKey())
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .forEach(System.out::println);
    }
}
