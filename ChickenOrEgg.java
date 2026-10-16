import java.util.Random;

public class ChickenOrEgg {

    static class Debater extends Thread {
        private String word;
        private Random random = new Random();

        public Debater(String word) {
            this.word = word;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(word);
            }
        }
    }

    public static void main(String[] args) {
        Debater chicken = new Debater("Курица");
        Debater egg = new Debater("Яйцо");

        System.out.println("Спор начался!");
        chicken.start();
        egg.start();

        // ждем пока один из потоков закончит
        while (chicken.isAlive() && egg.isAlive()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // если какой-то поток еще жив, значит последнее слово за ним
        String result;
        if (chicken.isAlive()) {
            result = "Первой появилась курица!";
        } else if (egg.isAlive()) {
            result = "Первым появилось яйцо!";
        } else {
            result = "Ничья, оба закончили одновременно";
        }

        // дожидаемся завершения обоих потоков
        try {
            chicken.join();
            egg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Спор окончен. " + result);
    }
}