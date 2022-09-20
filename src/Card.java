public class Card {

    public String name;
    public String suit;
    public String number;
    public int value;
    public int x1, y1, x2, y2;

    public Card(int cardSuit, int cardNumber) {

        //card values
        value = cardNumber;
        if (cardNumber == 1) {
            value = 11;
        }
        if (cardNumber > 10) {
            value = 10;
        }

        //card names
        if (cardNumber == 1) {
            number = "Ace";
        } else if (cardNumber >= 2 && cardNumber <= 10) {
            number = String.valueOf(cardNumber);
        } else if (cardNumber == 11) {
            number = "Jack";
        } else if (cardNumber == 12) {
            number = "Queen";
        } else if (cardNumber == 13) {
            number = "King";
        }

        //card suits
        if (cardSuit == 1) {
            suit = "Hearts";
            y1 = 0;
            y2 = 315;
        } else if (cardSuit == 2) {
            suit = "Spades";
            y1 = 315;
            y2 = 630;
        } else if (cardSuit == 3) {
            suit = "Diamonds";
            y1 = 630;
            y2 = 945;
        } else if (cardSuit == 4) {
            suit = "Clubs";
            y1 = 945;
            y2 = 1260;
        }

        name = number + " of " + suit;

        //width and height
        int w1 = 0;
        int w2 = 225;

        for (int n = 1; n <= 13; n++) {

            if (cardNumber == n) {
                x1 = w1;
                x2 = w2;
            }

            w1 = w1 + 225;
            w2 = w2 + 225;

        }

    }

    public void printCard() {
        System.out.println(name);
    }

}
