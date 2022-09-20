public class Player {

    public String name;
    public Card[] hand;
    public int numOfCards;
    public int money;
    public int value;
    public boolean busted;
    public boolean won;
    public boolean handFull;

    public Player(String playerName) {
        name = playerName;
        numOfCards = 0;
        value = 0;
        busted = false;
        won = false;
        handFull = false;
        hand = new Card[13];
    }

    public void printHand() {
        System.out.println(name + "'s Deck:");
        for (int n = 0; n < numOfCards; n++) {
            System.out.println(hand[n].name);
        }
        System.out.println("Points: " + value);
        System.out.println();
    }

}
