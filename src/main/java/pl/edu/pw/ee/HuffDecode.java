package pl.edu.pw.ee;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class HuffDecode {
    private static Map<Character, String> kodyMap = new HashMap<>();
    static int limit = 0;

    public static void main(String[] args) throws IOException {
        String nazwaPlikuIn = args[0]; // plik kompresowany
        String nazwaPlikuOut = args[0].substring(0,args[0].length()-5); //plik odkompresowany
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(nazwaPlikuIn));
        BufferedOutputStream plikOut = new BufferedOutputStream(new FileOutputStream(nazwaPlikuOut));
        Wezel wezel = readTree(in);
        generujKody(wezel, "");
        dekoduj(in, plikOut);
    }

    public static Wezel readTree(BufferedInputStream in) throws IOException {
        int r = in.read();
        if (r == 1) {
            Wezel wezel = new Wezel();
            wezel.znak = (char) in.read();
            return wezel;
        } else {
            Wezel wezel = new Wezel();
            wezel.lewy = readTree(in);
            wezel.prawy = readTree(in);
            return wezel;
        }
    }

    private static void generujKody(Wezel wezel, String kod){
        if(wezel.lewy == null && wezel.prawy == null){
            kodyMap.put(wezel.znak, kod);
        }else{
            generujKody(wezel.lewy, kod + "0");
            generujKody(wezel.prawy, kod + "1");
        }
    }

    public static int znajdNajDlugoscKodu(){
        int max = 0;
        for (Object key : kodyMap.keySet()) {
            if (kodyMap.get(key).length() > max) {
                max = kodyMap.get(key).length();
            }
        }
        return max;
    }

    public static void dekoduj(BufferedInputStream in, BufferedOutputStream plikOut) throws IOException {
        byte[] signature = new byte[4];
        in.read(signature);
        if(signature[0] != 'H' || signature[1] != 'U' || signature[2] != 'F' || signature[3] != 'F'){
            System.out.println("Plik nie jest poprawnie skompresowany");
            return;
        }
        byte[] bytes = new byte[4];
        in.read(bytes);
        limit = ByteBuffer.wrap(bytes).getInt();
        String bajt;
        String kod = "";
        int maks = znajdNajDlugoscKodu();
        int total = limit;
        int r;
        try {
            while ((r = in.read()) != -1) {
                bajt = String.format("%8s", Integer.toBinaryString(r)).replace(' ', '0');
                kod += bajt;
                while (kod.length() > maks && limit > 0) {
                    for (Object key : kodyMap.keySet()) {
                        if (kod.startsWith(kodyMap.get(key))) {
                            plikOut.write((char) key);
                            kod = kod.substring(kodyMap.get(key).length());
                            limit -= kodyMap.get(key).length();
                            ProgressBar.printProgress(total - limit, total, total - limit + kodyMap.get(key).length());
                            break;
                        }
                    }
                }
            }
            while (limit > 0) {
                for (Object key : kodyMap.keySet()) {
                    if (kod.startsWith(kodyMap.get(key))) {
                        plikOut.write((char) key);
                        kod = kod.substring(kodyMap.get(key).length());
                        limit -= kodyMap.get(key).length();
                        ProgressBar.printProgress(total - limit, total, total - limit + kodyMap.get(key).length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("blad");
        }
        ProgressBar.printProgress(1, 1,0);
        System.out.println();
        plikOut.close();
    }
}
