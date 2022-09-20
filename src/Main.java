public class Main {

    public static void main(String[] args) {

        Blackjack game1 = new Blackjack();
        new Thread(game1).run();

    }

}
