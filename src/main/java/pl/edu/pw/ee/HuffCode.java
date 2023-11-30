package pl.edu.pw.ee;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
public class HuffCode {
    static PriorityQueue<Wezel> queue = new PriorityQueue<>();
    private static Map<Character, String> kodyMap = new HashMap<>();
    static int limit = 0;
    public static void main(String[] args) throws IOException {
        String nazwaPlikuIn = args[0]; // plik kompresowany
        String nazwaPlikuOut = args[0]+".huff"; //plik zkompresowany
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(nazwaPlikuIn));
        BufferedOutputStream plikOut = new BufferedOutputStream(new FileOutputStream(nazwaPlikuOut));

        czytajPlik(nazwaPlikuIn);
        if(queue.size()==1){
            Wezel wezel = queue.top();
            queue.push(wezel, wezel.priorytet+1);
        }
        laczWezly();
        generujKody(queue.top(), "");
        writeTree(queue.top(), plikOut);
        koduj(in, plikOut);
    }

    public static void writeTree(Wezel wezel, BufferedOutputStream out) throws IOException {
        if (wezel.lewy == null && wezel.prawy == null) {
            out.write(1);
            out.write(wezel.znak);
        } else {
            out.write(0);
            writeTree(wezel.lewy, out);
            writeTree(wezel.prawy, out);
        }
    }

    public static void koduj(BufferedInputStream in, BufferedOutputStream plikOut) throws IOException {
        String bajt = "";
        int r=-1;
        int licz=0;
        byte[] signature = new byte[]{'H', 'U', 'F', 'F'};
        plikOut.write(signature);
        byte[] bytes = ByteBuffer.allocate(4).putInt(limit).array();
        plikOut.write(bytes);
        try {
            while ((r = in.read()) != -1) {
                bajt += kodyMap.get((char)r);
                while (bajt.length() >= 8) {
                    plikOut.write((char) Integer.parseInt(bajt.substring(0, 8), 2));
                    licz+=8;
                    ProgressBar.printProgress(licz+limit, limit*2,licz+limit-8);
                    bajt = bajt.substring(8);
                }
            }
            if (bajt.length() > 0) {
                while (bajt.length() < 8) {
                    bajt += "0";
                }
                plikOut.write(Integer.parseInt(bajt, 2));
                licz+=8;
                ProgressBar.printProgress(licz+limit, limit,licz+limit-8);
            }
        }
        catch (IOException e){
            System.out.println("blad");
        }
        ProgressBar.printProgress(1, 1,0);
        System.out.println();
        plikOut.close();
    }

    private static void generujKody(Wezel wezel, String kod){
        if(wezel.lewy == null && wezel.prawy == null){
            kodyMap.put(wezel.znak, kod);
            limit+=kod.length()*wezel.priorytet;
        }else{
            generujKody(wezel.lewy, kod + "0");
            generujKody(wezel.prawy, kod + "1");
        }
    }

    public static void laczWezly(){
        while(queue.size() > 1){
            Wezel wezel1 = queue.pop();
            Wezel wezel2 = queue.pop();
            Wezel nowyWezel = new Wezel();
            nowyWezel.lewy = wezel1;
            nowyWezel.prawy = wezel2;
            nowyWezel.priorytet = wezel1.priorytet + wezel2.priorytet;
            queue.push(nowyWezel, nowyWezel.priorytet);
        }
    }
    public static void czytajPlik(String fileIn) throws IOException {
        Map<Character, Wezel> czesto = new HashMap<>();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileIn));
        int total = in.available();
        int akt=0;
        try{
            int r=-1;
            while ((r = in.read()) != -1) {
                akt++;
                ProgressBar.printProgress(akt, total*2,akt-1);
                char znak = (char) r;
                if (czesto.get(znak) == null) {
                    czesto.put(znak, new Wezel());
                    (czesto.get(znak)).znak = znak;
                    (czesto.get(znak)).priorytet = 1;
                } else {
                    (czesto.get(znak)).priorytet++;
                }
            }
        }
        catch (IOException e){
            System.out.println("blad");
        }
        for (Object key : czesto.keySet()) {
            queue.push(czesto.get(key), (czesto.get(key)).priorytet);
        }
    }
}
