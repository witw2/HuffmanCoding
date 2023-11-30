package pl.edu.pw.ee;

public class ProgressBar {
    public static void main(String[] args) {
        int total = 100; // total steps
        for (int i = 0; i <= total; i++) {
            printProgress(i, total, i-1);
            try {
                Thread.sleep(100); // simulate work with sleep
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printProgress(int current, int total, int old) {
        StringBuilder bar = new StringBuilder("[");
        int percent = (int) ((current * 100.0f) / total);
        int oldPercent = (int) ((old * 100.0f) / total);
        int progress = (int) ((current * 50.0f) / total);
        if(oldPercent != percent){
            for (int i = 0; i < 50; i++) {
                if (i < progress) {
                    bar.append("=");
                } else if (i == progress) {
                    bar.append(">");
                } else {
                    bar.append(" ");
                }
            }
            bar.append("] ").append(percent).append("%");
            System.out.print("\r" + bar.toString());
        }
    }
}