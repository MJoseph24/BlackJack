import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyListener.*;

public class Blackjack implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Card[] deck;

    public Player dealer;
    public Player user;

    public Button hitButton;
    public Button standButton;
    public Button resetButton;

    public int topCounter;
    public int mouseX, mouseY;

    public boolean printCheck;
    public boolean gameOver;
    public boolean tie;

    public Image background;
    public Image fullDeck;
    public Image dealerDeckPic;
    public Image userDeckPic;
    public Image hitButtonPic;
    public Image standButtonPic;
    public Image resetButtonPic;
    public Image dealerWinPic;
    public Image userWinPic;
    public Image tiePic;

    //Constructor method
    public Blackjack() {

        setUpGraphics();

        //construct variables
        topCounter = 0;
        printCheck = false;
        gameOver = false;
        tie = false;

        //construct objects
        dealer = new Player("Dealer");
        user = new Player("User");

        hitButton = new Button(25, 465, 285, 95);
        standButton = new Button(25, 575, 285, 95);
        resetButton = new Button(690, 575, 285, 95);

        //construct arrays
        deck = new Card[52];
        int counter = 0;
        for (int numSuits = 1; numSuits <= 4; numSuits++) {
            for (int numCards = 1; numCards <= 13; numCards++) {
                deck[counter] = new Card(numSuits, numCards);
                counter++;
            }
        }

        //construct images
        background = Toolkit.getDefaultToolkit().getImage("BACKDROP.jpeg");
        fullDeck = Toolkit.getDefaultToolkit().getImage("DECK.jpeg");
        dealerDeckPic = Toolkit.getDefaultToolkit().getImage("DEALER.png");
        userDeckPic = Toolkit.getDefaultToolkit().getImage("PLAYER.png");
        hitButtonPic = Toolkit.getDefaultToolkit().getImage("HIT.PNG");
        standButtonPic = Toolkit.getDefaultToolkit().getImage("STAND.png");
        resetButtonPic = Toolkit.getDefaultToolkit().getImage("RESET.jpeg");
        dealerWinPic = Toolkit.getDefaultToolkit().getImage("DEALERWIN.jpg");
        userWinPic = Toolkit.getDefaultToolkit().getImage("PLAYERWIN.jpg");
        tiePic = Toolkit.getDefaultToolkit().getImage("TIE.jpg");

        start();

    }

    public void printDeck() {
        for (Card c : deck) {
            c.printCard();
            System.out.println(c.value);
        }
    }

    public void shuffleDeck() {

        Card temp = new Card(1, 1);

        int randomNumber;

        for (int n = 0; n < deck.length; n++) {

            randomNumber = (int) (52 * Math.random());

            temp = deck[n];
            deck[n] = deck[randomNumber];
            deck[randomNumber] = temp;

        }

    }

    public void deal(Player dealee) {

        dealee.value = 0;

        dealee.hand[dealee.numOfCards] = deck[topCounter];

        topCounter++;
        dealee.numOfCards++;

        for (int n = 0; n < dealee.numOfCards; n++) {
            dealee.value += dealee.hand[n].value;
        }

        dealee.printHand();

        if (dealee.value == 21) {
            dealee.won = true;
        }

    }

    public void start() {

        shuffleDeck();

        deal(user);
        deal(user);
        deal(dealer);
        deal(dealer);
        System.out.println("Click H to hit, and S to stand");

    }

    public void hit() {

        deal(user);
        user.printHand();

        System.out.println("Click H to hit, and S to stand");

        if (user.value == 21) {
            System.out.println("User1 has got blackjack!");
            user.won = true;
        }

        if (user.value > 21) {
            for (int n = 0; n < user.numOfCards; n++) {
                if (user.hand[n].value == 11) {
                    user.hand[n].value = 1;
                    user.value -= 10;
                }
                if (user.value == 21) {
                    System.out.println("User1 has got blackjack!");
                    user.won = true;
                }
            }
            if (user.value > 21) {
                System.out.println("User1 has busted!");
                dealer.won = true;
            }
        }

    }

    public void stand() {

        while (dealer.value < 16) {

            for (int n = 0; n < dealer.numOfCards; n++) {
                if (dealer.hand[n].number.equals("Ace") && user.value > 21) {
                    dealer.hand[n].value = 1;
                }
            }

            deal(dealer);
            dealer.printHand();
            pause(2000);
        }

        user.handFull = true;
        dealer.handFull = true;

        System.out.println("Dealer's Final:");
        dealer.printHand();

        if (dealer.value > 21) {
            user.won = true;
        }

        if (dealer.value == 21) {
            System.out.println("The dealer has got blackjack!");
            dealer.won = true;
        }

        if (dealer.busted) {
            System.out.println("Dealer has busted!");
            user.won = true;
        }

        if (dealer.handFull && user.handFull) {

            if (user.value > dealer.value) {
                user.won = true;
                System.out.println("The user's hand beats the user's hand.");
            }

            if (dealer.value > user.value) {
                dealer.won = true;
                System.out.println("The dealer's hand beats the dealer's hand.");
            }

            if (dealer.value == user.value) {
                tie = true;
            }

        }

    }

    public void reset() {

        gameOver = false;
        printCheck = false;
        tie = false;

        deck = new Card[52];
        int counter = 0;
        for (int numSuits = 1; numSuits <= 4; numSuits++) {
            for (int numCards = 1; numCards <= 13; numCards++) {
                deck[counter] = new Card(numSuits, numCards);
                counter++;
            }
        }

        dealer = new Player("Dealer");
        user = new Player("User");

        topCounter = 0;

        start();

    }

    public void winStatement() {

        if (dealer.won && !printCheck) {
            System.out.println("The dealer has won!");
            printCheck = true;
            gameOver = true;
        }

        if (user.won && !printCheck) {
            System.out.println("The user has won!");
            printCheck = true;
            gameOver = true;
        }

        if (tie && !printCheck) {
            printCheck = true;
            gameOver = true;
        }

    }


    //paints things on the screen using bufferStrategy
    public void render() {

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Put all your code for drawing things on the screen here

        g.drawImage(background, 0, 0, null);

        //buttons
        g.drawImage(hitButtonPic, hitButton.xpos, hitButton.ypos, hitButton.width, hitButton.height, null);
        g.drawImage(standButtonPic, standButton.xpos, standButton.ypos, standButton.width, standButton.height, null);
        g.drawImage(resetButtonPic, resetButton.xpos, resetButton.ypos, resetButton.width, resetButton.height, null);

        //dealer's deck
        g.drawImage(dealerDeckPic, 25, 25, 200, 40, null);
        for (int d = 0; d < dealer.numOfCards; d++) {
            g.drawImage(fullDeck, 25 + (d * 100), 75, 125 + (d * 100), 215, dealer.hand[d].x1, dealer.hand[d].y1, dealer.hand[d].x2, dealer.hand[d].y2, null);
        }

        //user's deck
        g.drawImage(userDeckPic, 25, 240, 200, 40, null);
        for (int u = 0; u < user.numOfCards; u++) {
            g.drawImage(fullDeck, 25 + (u * 100), 290, 125 + (u * 100), 430, user.hand[u].x1, user.hand[u].y1, user.hand[u].x2, user.hand[u].y2, null);
        }

        if (gameOver && dealer.won) {
            g.drawImage(dealerWinPic, 335, 465, 330, 205, null);
        }

        if (gameOver && user.won) {
            g.drawImage(userWinPic, 335, 465, 330, 205, null);
        }

        if (gameOver && tie) {
            g.drawImage(tiePic, 335, 465, 330, 205, null);
        }

        //leave these two lines of code as the last lines of the render() method
        g.dispose();
        bufferStrategy.show();

    }

    public void run() {

        while (true) {
            //render();
            winStatement();
            render();
            pause(10);
        }

    }

    public void setUpGraphics() {

        frame = new JFrame("Blackjack");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    //keyboard commands

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();     //gets the character of the key pressed
        int keyCode = e.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 72) {
            hit();
        }

        if (keyCode == 83) {
            stand();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    //mouse commands

    @Override
    public void mouseClicked(MouseEvent e) {

        int x, y;
        x = e.getX();
        y = e.getY();

        mouseX = x;
        mouseY = y;

        if (hitButton.rec.contains(x, y)) {
            hit();
        }

        if (standButton.rec.contains(x, y)) {
            stand();
        }

        if (resetButton.rec.contains(x, y)) {
            reset();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
