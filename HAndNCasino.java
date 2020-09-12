package schoolwork;

import java.util.Scanner;
import java.util.Random;

public class HAndNCasino {


    //Global variables, can be accessed by all methods

    static int credits = 100;
    //credits, starting number of credits is 100
    static int rnd1, rnd2, rnd3, rnd4, placeX;
    //rnd1 and rnd2 are used to store the randomly generated coordinates of the $, rnd3 and rnd 4 are used to store the random coordinates of X mines
    //placeX is used to determine whether or not an X should be placed on the grid at a 50% chance 
    static int xhead = 8, yhead = 9, xtail = 0, ytail = 1;
    //stores the tail and head coordinates (made it easier to understand during the coding process)
    static String headDirection = "d", direction, playAgain;
    // headDirection determines which direction the snake is facing and direction is used to receive input from scanner regarding which way they would like to move forward
    //playAgain is used as an input variable from scanner to determine if a player would like to play again once they lose
    static boolean alive = true;
    // determines if the player is still alive to keep looping the game
    static int[] body = new int[11];
    //stores all x and y coordinates of the snake, even indices are x coordinates of body and odd indices are y coordinates of body
    static final int ARRAY_SHUFFLE = 8;
    // makes sure to shuffle through all array elements in the body when shifting coordinates

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        //Variables
        int userBet; //the amount of credits the user bets
        char userPlay;
        //Name and suit of each card
        String[][] cardDeckStrs = new String[4][12];//Cards do not need to be kept track of because in most blackjack games, dealers use 5-6 decks, assume that the deck is reshuffled during each play
        cardDeckStrs = new String[][]{
            {"Ace♠", "Two♠", "Three♠", "Four♠", "Five♠", "Six♠", "Seven♠", "Eight♠", "Nine♠", "Jack♠", "Queen♠", "King♠"},
            {"Ace♥", "Two♥", "Three♥", "Four♥", "Five♥", "Six♥", "Seven♥", "Eight♥", "Nine♥", "Jack♥", "Queen♥", "King♥"},
            {"Ace♣", "Two♣", "Three♣", "Four♣", "Five♣", "Six♣", "Seven♣", "Eight♣", "Nine♣", "Jack♣", "Queen♣", "King♣"},
            {"Ace♦", "Two♦", "Three♦", "Four♦", "Five♦", "Six♦", "Seven♦", "Eight♦", "Nine♦", "Jack♦", "Queen♦", "King♦"}
        };

        //Value of each card
        int[][] cardDeckVals = new int[4][12];
        cardDeckVals = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10},};

        //Colourful and attractive welcome message
        System.out.print(ANSI_RED + "We" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "lc" + ANSI_RESET);
        System.out.print(ANSI_GREEN + "om" + ANSI_RESET);
        System.out.print(ANSI_CYAN + "e " + ANSI_RESET);
        System.out.print(ANSI_BLUE + "to" + ANSI_RESET);
        System.out.print(ANSI_PURPLE + " t" + ANSI_RESET);
        System.out.print(ANSI_PURPLE + "he" + ANSI_RESET);
        System.out.print(ANSI_RED + " H" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + " a" + ANSI_RESET);
        System.out.print(ANSI_GREEN + "nd" + ANSI_RESET);
        System.out.print(ANSI_CYAN + " N" + ANSI_RESET);
        System.out.print(ANSI_BLUE + " C" + ANSI_RESET);
        System.out.print(ANSI_PURPLE + "as" + ANSI_RESET);
        System.out.print(ANSI_PURPLE + "in" + ANSI_RESET);
        System.out.println(ANSI_RED + "o!" + ANSI_RESET);

        //Games start, continues to repeat until credits reach 0
        //If the user spends all their credits the game ends
        while (credits > 0) {
            //user is prompted for the game they would like to play
            System.out.println("Would you like to play Blackjack (B), Snake (S), or visit the prize booth(!)?");
            userPlay = scan.next().charAt(0);

            if (userPlay == 'B' || userPlay == 'b') {//runs blackjack Game
                System.out.println("You have " + credits + " credits.");
                System.out.println("How many credits will you bet?");
                userBet = scan.nextInt();

                if (userBet < 1 || userBet > credits) {//If the user bet is not viable
                    System.out.println("Please enter a bet between 1 and " + credits);
                } else if (userBet > 0 && userBet <= credits) {//If the bet is viable, the game can start
                    //following method runs the game
                    credits = runGame(cardDeckVals, cardDeckStrs, credits, userBet);
                } else {
                    System.out.println("Please enter a viable bet");
                }
            } else if (userPlay == '!') {//prize booth runs if user inputs "!"
                credits = prizeBooth(credits); //passes off number of credits to prizeBooth method as a parameter
            } else if (userPlay == 'S' || userPlay == 's') {
                snake(); //snake game runs if user inputs an "s"
            } else {//If the user is unable to enter a char that will take them to a game/prize booth
                System.out.println("Please enter \'B\', \'S\', or \'!\' to play a game or go to the prize booth");
            }
        }//end of 1st while loop
        System.out.println("You're out of credits! Please reload the game and try again!");
    }//end of public static void

    public static int prizeBooth(int credits) { //Runs the prize booth system. This is a method with credits passed off as a parameter
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the prize booth! Please select a prize you want, Hannah and Nathan will give you the prize!");
        String[][] prizes = new String[2][7]; //declaration of prizes and their descriptions
        prizes = new String[][]{
            {"Gum", "Chocolate", "Timbits", "Candies", "Golden Crown", "Giant Teddy Bear", "Impossible Prize"},//Prize
            {"It's really chewy", "Chocolate has numerous health benefits and is made from cocao", "Fresh from Tim Horton's!", "A tasty treat", "You will feel like royalty!", "We aren't giving this to you guys in real life", "It is impossible to recieve"}//Description of prize
        };

        int[] prizePrice = new int[7];//array containing each corresponding prize's price
        prizePrice = new int[]{200, 400, 600, 400, 2000, 10000, 99999};
        
        //Outprint of menu and possible selection
        System.out.println(ANSI_CYAN + "You have " + credits + " credits. Please select a prize from the list below!" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + prizes[0][0] + " " + prizePrice[0] + " credits\t\t\t\t" + prizes[1][0] + ANSI_RESET);
        System.out.println(ANSI_BLUE + prizes[0][1] + " " + prizePrice[1] + " credits\t\t\t" + prizes[1][1] + ANSI_RESET);
        System.out.println(ANSI_PURPLE + prizes[0][2] + " " + prizePrice[2] + " credits\t\t\t" + prizes[1][2] + ANSI_RESET);
        System.out.println(ANSI_BLUE + prizes[0][3] + " " + prizePrice[3] + " credits\t\t\t" + prizes[1][3] + ANSI_RESET);
        System.out.println(ANSI_PURPLE + prizes[0][4] + " " + prizePrice[4] + " credits\t\t" + prizes[1][4] + ANSI_RESET);
        System.out.println(ANSI_BLUE + prizes[0][5] + " " + prizePrice[5] + " credits\t\t" + prizes[1][5] + ANSI_RESET);
        System.out.println(ANSI_PURPLE + prizes[0][6] + " " + prizePrice[6] + " credits\t\t" + prizes[1][6] + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Please enter the name of the prize you would like to buy!" + ANSI_RESET);
        
        String buyPrize = scan.nextLine();
        // prompts for what the user would like to purchase
        int prizeIndex = prizeCost(prizes, buyPrize);
        //passes off user input string and prizes array to prizeCost method

        if (prizeIndex == -1) {
            //if the user did not select a valid prize, the initial value of -1 was returned and triggers this message
            System.out.println(ANSI_RED + "Sorry, that prize doesn't exist" + ANSI_RESET);
        } else if (prizeIndex != -1) {
            // if the user selected something, the value of -1 is changed and a valid prize was selected. The index is then referenced to the price array to get the price of the item
            // the price is then assigned to prizeCost1 in order to check if the user has enough credits to buy the item
            int prizeCost1 = prizePrice[prizeIndex];

            if (prizeCost1 > credits) {
                //if the price of the prize is greater than what you have, you can't buy it and output this message
                System.out.println(ANSI_RED + "Sorry, that prize is too expensive! Come again!" + ANSI_RESET);
            } else if (prizeCost1 <= credits) {
                //if the price is less than or equal to your credits, you have enough to buy it! subtract the price from your credits, and voila! You've got the prize! 
                credits = credits - prizeCost1;
                System.out.println(ANSI_GREEN + "Enjoy your " + prizes[0][prizeIndex] + "! Come again!" + ANSI_RESET);
                //outprint this message with the name of the prize
            }
        }

        return credits; //returns the value of credits to be assigned to global ariable credits
    }

    public static int prizeCost(String[][] prizes, String buyPrize) {
        //this method is used to determine which selection the user made by using a for loop to look through all the possible prizes and compare the user string to each one
        // if the prize is found, its index is returned, else the the initial value of -1 is returned 
        int prizeIndex = -1;
        //initial value of -1. This will variable will determine if the user selected something valid, and if so, what they selected
        for (int i = 0; i < 7; i++) {
            String prizeTemp = prizes[0][i];
            if (prizeTemp.equalsIgnoreCase(buyPrize)) {
                prizeIndex = i;
                break;
            }
        }
        return prizeIndex;
    }

    public static int runGame(int[][] cardDeckVals, String[][] cardDeckStrs, int credits, int userBet) {
        String dealerCard1; //First card dealer recieves
        int dealerCard1Val; //Value of first dealer card
        String userCard1; //First card user recieves
        int userCard1Val; //Value of first user card
        String userCard2; //Second card user recieves
        int userCard2Val; //Value of second user card

        //Generating dealer's hand
        dealerCard1 = generateCard(cardDeckStrs);
        dealerCard1Val = findCardVal(dealerCard1, cardDeckStrs, cardDeckVals);
        int dealerHandVal = dealerCard1Val;
        int dealerHandVal2 = dealerHandVal + 10;
        
        //Output to the user the dealer's hand
        if (dealerCard1Val == 1) {//If dealer's hand contains an ace
            System.out.println(ANSI_RED + "Dealer is dealt " + dealerCard1 + ". The value of their hand is " + dealerHandVal + " or " + dealerHandVal2 + ANSI_RESET);
        } else {//If dealer's hand does not contain ace
            System.out.println(ANSI_RED + "Dealer is dealt " + dealerCard1 + ". The value of their hand is " + dealerHandVal + ANSI_RESET);
        }
        //generating user's hand
        userCard1 = generateCard(cardDeckStrs);
        userCard1Val = findCardVal(userCard1, cardDeckStrs, cardDeckVals);
        userCard2 = generateCard(cardDeckStrs);
        userCard2Val = findCardVal(userCard2, cardDeckStrs, cardDeckVals);

        //CONSTANTS
        final int DEALERMAXHANDVAL = 17; //If dealer deals up to 17, they must stop, or until they bust
        final int BUST = 21; //If user or dealer is dealt over 21 they lose

        //determining user's hands
        int userHandVal = userCard1Val + userCard2Val; //If hand has no ace or if ace causes a hand value to go over 21
        int userHandVal2 = userHandVal + 10; //If hand contains ace
        String hitStand = "";

        if (userCard1Val == 1 || userCard2Val == 1) {//if user's hand contains ace
            System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + " and " + userCard2 + ". The value of your hand is " + userHandVal + " or " + userHandVal2 + ANSI_RESET);//Aces can be worth either 11 or 1

            if (userHandVal2 == 21) {
                System.out.println("Blackjack! You win");
                credits += (userBet * 2);
            } else {
                String finalState = aceHand(userCard1, userCard1Val, userHandVal, userHandVal2, hitStand, cardDeckStrs, cardDeckVals, dealerHandVal, dealerCard1, dealerCard1Val, dealerHandVal2);
                //Run aceHand method, results in win state, lose state, pushed state, or blackjack state

                //Each state is evaluated and outputted to the user, credits are increased or decreased accordingly
                if (finalState.equalsIgnoreCase("win")) {
                    System.out.println(ANSI_GREEN + "You win!" + ANSI_RESET);
                    credits += (userBet);
                } else if (finalState.equalsIgnoreCase("lose")) {
                    System.out.println("You lose!");
                    credits -= (userBet);
                } else if (finalState.equalsIgnoreCase("pushed")) {
                    System.out.println("Tie, no winner!");
                } else if (finalState.equalsIgnoreCase("blackjack")) {
                    System.out.println(ANSI_GREEN + "Blackjack! You win!" + ANSI_RESET);
                    credits += (userBet * 2);
                }
            }
        } else {//if user's hand does not contain ace
            System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + " and " + userCard2 + ". The value of your hand is " + userHandVal);//Hands without aces are worth normal
            String finalState = normalHand(userCard1, userCard1Val, userHandVal, userHandVal2, hitStand, cardDeckStrs, cardDeckVals, dealerHandVal, dealerCard1, dealerCard1Val, dealerHandVal2);

            //Generate state of user (win, lose, tied, or blackjack)
            if (finalState.equalsIgnoreCase("win")) {
                System.out.println(ANSI_GREEN + "You win!" + ANSI_RESET);
                credits += (userBet);
            } else if (finalState.equalsIgnoreCase("lose")) {
                System.out.println(ANSI_RED + "You lose!" + ANSI_RESET);
                credits -= (userBet);
            } else if (finalState.equalsIgnoreCase("pushed")) {
                System.out.println(ANSI_BLUE + "Tie, no winner!" + ANSI_RESET);
            } else if (finalState.equalsIgnoreCase("blackjack")) {
                System.out.println(ANSI_GREEN + "Blackjack! You win!" + ANSI_RESET);
                credits += (userBet * 2);
            }
        }
        return credits;
    }

    //method for when user's hand does not contain ace, or does contain ace but ace is not worth 11
    public static String normalHand(String userCard1, int userCard1Val, int userHandVal, int userHandVal2, String hitStand, String[][] cardDeckStrs, int[][] cardDeckVals, int dealerHandVal, String dealerCard1, int dealerCard1Val, int dealerHandVal2) {
        Scanner scan = new Scanner(System.in);
        String finalState = "";//"lose" state, "pushed" state, "win" state, "blackjack" state

        System.out.println("Will you hit or stand? (Please enter \'hit\' or \'stand\')");
        hitStand = scan.nextLine();

        do {//do while loop to keep user hitting or standing

            if (hitStand.equalsIgnoreCase("hit")) {//if player choses to hit
                userCard1 = generateCard(cardDeckStrs);
                userCard1Val = findCardVal(userCard1, cardDeckStrs, cardDeckVals);
                userHandVal += userCard1Val;
                userHandVal2 += userCard1Val;

                if (userCard1Val == 1 && userHandVal2 < 21) {//if statement if an ace is drawn and userHandVal2 does not go over 21 -> run acehand method
                    hitStand = "Ace";
                    finalState = aceHand(userCard1, userCard1Val, userHandVal, userHandVal2, hitStand, cardDeckStrs, cardDeckVals, dealerHandVal, dealerCard1, dealerCard1Val, dealerHandVal2);
                } else if (userHandVal > 21) {//if statement if userHandVal goes above 21
                    System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + ". The value of your hand is " + userHandVal + ANSI_RESET);
                    hitStand = "lose";
                    finalState = "lose";
                } else if (userHandVal == 21) {//if userHandVal == 21
                    System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + ". The value of your hand is " + userHandVal + ANSI_RESET);
                    hitStand = "blackjack";
                    finalState = "blackjack";
                } else if (userHandVal < 21) {//if userHandVal < 21
                    System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + ". The value of your hand is " + userHandVal + ANSI_RESET);
                    System.out.println("Will you hit or stand? (Please enter \'hit\' or \'stand\')");
                    hitStand = scan.nextLine();
                }
            }
            if (hitStand.equalsIgnoreCase("stand")) {
                dealerHandVal = dealerPlays(cardDeckStrs, cardDeckVals, dealerHandVal, dealerCard1, dealerCard1Val, dealerHandVal2, userHandVal); //run dealer plays method
                if (dealerHandVal > 21) {//if dealer busts -> win state
                    finalState = "win";
                } else if (dealerHandVal > userHandVal) {//if dealer plays is greater than user's hand -> lose state
                    finalState = "lose";
                } else if (dealerHandVal < userHandVal) {//if dealer plays is less than user's hand -> win state
                    finalState = "win";
                } else if (dealerHandVal == userHandVal) {//if dealer plays is equal to user's hand -> pushed state
                    finalState = "pushed";
                }
            } else {
                System.out.println("Please enter \'hit\' or \'stand\'");
            }
        } while (hitStand.equalsIgnoreCase("hit"));

        return finalState;
    }

    //method for when user's hand contains ace
    public static String aceHand(String userCard1, int userCard1Val, int userHandVal, int userHandVal2, String hitStand, String[][] cardDeckStrs, int[][] cardDeckVals, int dealerHandVal, String dealerCard1, int dealerCard1Val, int dealerHandVal2) {
        Scanner scan = new Scanner(System.in);
        String finalState = "";//"lose" state, "pushed" state, "win" state, "blackjack" state

        System.out.println("Will you hit or stand? (Please enter \'hit\' or \'stand\')");
        hitStand = scan.nextLine();

        do {//do while loop as long as user decides to hit
            if (hitStand.equalsIgnoreCase("hit")) {//if player choses to hit, they are dealt another card
                userCard1 = generateCard(cardDeckStrs);
                userCard1Val = findCardVal(userCard1, cardDeckStrs, cardDeckVals);
                userHandVal += userCard1Val;
                userHandVal2 += userCard1Val;
                
                //If statements to determine if the player has won, has gone above 21, or is eligible to hit again
                if (userHandVal2 > 21 && userHandVal < 21) {//if userHandVal2 goes over 21, but userHandVal remains normal
                    System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + ". The value of your hand is " + userHandVal + ANSI_RESET);
                    System.out.println("Will you hit or stand? (Please enter \'hit\' or \'stand\')");
                    hitStand = scan.nextLine();
                }
                if (userHandVal > 21) {//if userHandVal goes over 21 (user loses game)
                    hitStand = "lose";
                    finalState = "lose";
                }
                if (userHandVal == 21 || userHandVal2 == 21) {//if userHandVal or userHandVal2 is equal to 21 (blackjack)
                    hitStand = "blackjack";
                    finalState = "blackjack";
                }
                if (userHandVal < 21 && userHandVal2 < 21) {//if userHandVal and userHandVal 2 remain below 21 (nothing, ask to hit or stand again)
                    System.out.println(ANSI_BLUE + "You are dealt " + userCard1 + ". The value of your hand is " + userHandVal + " or " + userHandVal2 + ANSI_RESET);
                    System.out.println("Will you hit or stand? (Please enter \'hit\' or \'stand\')");
                    hitStand = scan.nextLine();
                }
            }
            if (hitStand.equalsIgnoreCase("stand")) {//User stands, the dealer method is run and the outcome of the dealer's hand is the determining factor in who wins or loses
                dealerHandVal = dealerPlays(cardDeckStrs, cardDeckVals, dealerHandVal, dealerCard1, dealerCard1Val, dealerHandVal2, userHandVal); //run dealer plays method
                if (userHandVal2 > userHandVal) {//Makes userHandVal equal to one value
                    userHandVal = userHandVal2;
                }
                if (dealerHandVal < userHandVal) {//if dealer plays is less than user's hand -> win state
                    finalState = "win";
                } else if (dealerHandVal == userHandVal) {//if dealer plays is equal to user's hand -> pushed state
                    finalState = "pushed";
                } else if (dealerHandVal > userHandVal && dealerHandVal < 21) {//if dealer plays is greater than user's hand -> lose state
                    finalState = "lose";
                } else if (dealerHandVal > 21) {//if dealer busts -> win state
                    finalState = "win";
                }
            } else {
                System.out.println("Please enter \'hit\' or \'stand\'");//If the user enters a string that is not hit or stand, they can try again
            }
        } while (hitStand.equalsIgnoreCase("hit"));

        return finalState;
    }

    //Dealer plays their hand, the dealer must hit until their cards reach a value of 17, they surpass the user's hand, or they bust
    public static int dealerPlays(String[][] cardDeckStrs, int[][] cardDeckVals, int dealerHandVal, String dealerCard1, int dealerCard1Val, int dealerHandVal2, int userHandVal) {//dealer plays against the user, must hit until they reach 17 or above
        while (dealerHandVal < 17 && dealerHandVal < userHandVal) {
            dealerCard1 = generateCard(cardDeckStrs);
            dealerCard1Val = findCardVal(dealerCard1, cardDeckStrs, cardDeckVals);
            dealerHandVal += dealerCard1Val;
            dealerHandVal2 += dealerCard1Val;

            if (dealerCard1Val == 1 && dealerHandVal2 <= 21) {//If dealer has an ace
                System.out.println(ANSI_RED + "Dealer is dealt " + dealerCard1 + ". The value of their hand is " + dealerHandVal + " or " + dealerHandVal2 + ANSI_RESET);

                if (dealerHandVal2 == 21) {
                    dealerHandVal = dealerHandVal2;
                }
            } else {
                System.out.println(ANSI_RED + "Dealer is dealt " + dealerCard1 + ". The value of their hand is " + dealerHandVal + ANSI_RESET);
            }
        }
        return dealerHandVal;
    }

    //A card is pseudorandomly generated for the user
    public static String generateCard(String[][] cardDeckStrs) {
        Random rand = new Random();
        int cardID1 = rand.nextInt(4);
        int cardID2 = rand.nextInt(12);
        String card = cardDeckStrs[cardID1][cardID2];
        return card;
    }

    //The value of the card string is found using a forloop which cycles through each card name to recieve its equivalent value
    public static int findCardVal(String card, String[][] cardDeckStrs, int[][] cardDeckVals) {
        int cardVal = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                if (cardDeckStrs[i][j].equals(card)) {
                    cardVal = cardDeckVals[i][j];
                }
            }
        }
        return cardVal;
    }

    public static String[][] initialGame(String matrix[][]) {
        // this method will set up the board at the start of the game. It receives the main matrix that the snake game occurs on and modifies it
        //by putting the starting snake position and setting the coordinates of each of the body parts' coordinates, and then returns it.

        generateApple(body, matrix);
        // passes of the body array with its coordinates and the matrix to generateApple method. Calling this method here will put the first apple/food on the matrix

        for (int i = 2; i < 7; i++) {
            matrix[8][i] = ANSI_YELLOW + "o" + ANSI_RESET;
        }
        // This for loop sets implements the starting position of the snake by using a for loop to assign string "o" on the matrix to 5 different coordinates with the same y-value of 8

        body[xtail] = 2;
        body[ytail] = 8;
        body[2] = 3;
        body[3] = 8;
        body[4] = 4;
        body[5] = 8;
        body[6] = 5;
        body[7] = 8;
        body[xhead] = 6;
        body[yhead] = 8;

        // initial coordinates of the body parts are stored in the body array
        return matrix;
        // the modified matrix is returned
    }

    public static void gameOver() {
        //this method is called on when a player loses (you bumped into a mine, yourself, or a boarder)
        
        credits -= 50; //deduct 50 credits for losing (oops)
        System.out.println("G A M E   O V E R!  " + "Oh no! You lost 50 credits, but it's ok because you earned: " + credits + " credits!"); //print game over and number of credits after deduction
        
        alive = false; //sets alive variable false to stop running the program (see do while loop lower in code)
    }

    public static void generateApple(int available[], String matrix[][]) {
        // the generateApple method generates random points for the food on the matrix. This method modifies the matrix directly.

        Random rnd = new Random(); //random object which will generate random coordinates
        rnd1 = rnd.nextInt(14) + 1; //generates a random x coordinate for the apple between 0 and 14 (within borders of the matrix
        rnd2 = rnd.nextInt(14) + 1; //generates a random y coordinate for the apple between 0 and 14 (within borders of the matrix

        if (((ANSI_PURPLE + "X" + ANSI_RESET).equals(matrix[rnd2][rnd1]) || (ANSI_YELLOW + "o" + ANSI_RESET).equals(matrix[rnd2][rnd1]))) {
            //This if statement checks whether or not the generated coordinates of the food overlaps with a mine or a snake bodypart. If there is an overlap, 
            //generate the coordinates for a new food
            generateApple(body, matrix);
        } else {
            // if the generated coordinates of the food did not overlap with a mine or a snake body part, implement it on the matrix directly
            matrix[rnd2][rnd1] = ANSI_GREEN + "$" + ANSI_RESET;
        }

    }

    public static void generateMine(int available[], String matrix[][]) {
        // the generateApple method generates random points for the mine on the matrix. This method modifies the matrix directly.
        Random rnd = new Random(); //random object which will generate random coordinates
        rnd3 = rnd.nextInt(14) + 1; //generates a random x coordinate for the apple between 0 and 14 (within borders of the matrix
        rnd4 = rnd.nextInt(14) + 1; //generates a random x coordinate for the apple between 0 and 14 (within borders of the matrix
        placeX = rnd.nextInt(2); // generates a 0 or a 1 for the 50% chancee of creating a mine on the matrix

        if (placeX == 0) {
            // if a 0 was generated, 
            if (((ANSI_PURPLE + "X" + ANSI_RESET).equals(matrix[rnd4][rnd3]) || (ANSI_GREEN + "$" + ANSI_RESET).equals(matrix[rnd4][rnd3]) || (ANSI_YELLOW + "o" + ANSI_RESET).equals(matrix[rnd4][rnd3]))) {
                //This if statement checks whether or not the generated coordinates of the mine overlaps with another mine, food, or snake bodypart. If there is an overlap, 
                //generate the coordinates for a new food
                generateMine(body, matrix);
            } else {
                // if the generated coordinates of the mine did not overlap, implement it on the matrix directly
                matrix[rnd4][rnd3] = ANSI_PURPLE + "X" + ANSI_RESET;
            }
        }

    }

    public static String[][] moveForward(String matrix[][], String headDirection) {
        //This method implements a the movement portion of the snake. The headDirection of the snake is passed on and the matrix is as well. 
        // The matrix is directly implemented by this method as well
        
        matrix[body[1]][body[0]] = " ";
        //take the last part of the snake and delete it by putting a space value in its place
        
        for (int i = 0; i < ARRAY_SHUFFLE; i++) {
            body[i] = body[i + 2];
        }
        // this for loop is CRUCIAL. It assigns the coordinates of the previous body part to the next body part all the way up to the coordinates of the head
        
        if (headDirection.equalsIgnoreCase("r")) {
            //The following if statement determines the direction of the next coordinates of the head. incrmement or decrement, depending on which way the user wants to go
            //For example, if the user wanted to go left, it would increment the xcoordinate of the head to add one
            body[xhead]++;
        } else if (headDirection.equalsIgnoreCase("l")) {
            body[xhead]--;
        } else if (headDirection.equalsIgnoreCase("u")) {
            body[yhead]--;
        } else {
            body[yhead]++;

        }

        if ((ANSI_PURPLE + "X" + ANSI_RESET).equals(matrix[body[yhead]][body[xhead]]) || (ANSI_YELLOW + "o" + ANSI_RESET).equals(matrix[body[yhead]][body[xhead]]) || (ANSI_BLUE + "-" + ANSI_RESET).equals(matrix[body[yhead]][body[xhead]])) {
            // This if statement checks to see if the coordinates of the head overlap with a mine, a snake body part, or the walls. In which case the player loses and the gameover method is triggered
            gameOver();
        }

        matrix[body[yhead]][body[xhead]] = ANSI_YELLOW + "o" + ANSI_RESET;
            //If the head does not overlap with any of the following implement the new head body part on the matrix 

        if (body[xhead] == rnd1 && body[yhead] == rnd2) {
            //this if statement determines if the head overlaps with a food, in which case the player earns credits and a new food is generated
            credits += 15;
            generateApple(body, matrix);
        }

        credits += 2; //add two credits for every successful move 

        return matrix;
    }

    public static void snake() {
        // this method is crucial for the game, it will call all other methods
        do {
            // This do-while loop repeats is used to implement a play again for the game. It will run if the player says yes and will loop through the setup of the entire game again.
            
            final int BOARDWIDTH = 16;
            final int BOARDHEIGHT = 16;
            //initializing constants for the board dimensions

            Scanner scan = new Scanner(System.in); //initializing scanner object 

            String[][] matrix = new String[BOARDWIDTH][BOARDHEIGHT]; //creates a new matrix with the following dimensions

            for (int i = 0; i < 16; i++) {
                for (int a = 0; a < 16; a++) {
                    //nested for loops to fill the matrix with spaces 
                    matrix[i][a] = " ";
                }
                System.out.println();
                //skips a line after every 16 characters 
            }
            
            for (int i = 0; i < 16; i++) {
                //for loop fills the top and bottom boarder with dashes
                matrix[0][i] = ANSI_BLUE + "-" + ANSI_RESET;
                matrix[15][i] = ANSI_BLUE + "-" + ANSI_RESET;
            }

            for (int i = 1; i < 15; i++) {
                //for loop fills the left and right borders with dashes
                matrix[i][0] = ANSI_BLUE + "-" + ANSI_RESET;
                matrix[i][15] = ANSI_BLUE + "-" + ANSI_RESET;
            }

            initialGame(matrix); //calls on the method that will initialize the game

            while (alive) {
                // this while loop is used to recreate the modified matrix and prompt the user again and again. It is active as long as alive is true. alive is modified
                //when the user loses and the while loop is discontinued
                
                for (int i = 0; i < 16; i++) {
                    for (int a = 0; a < 16; a++) {
                        //recreates the entire matrix using nested for loops and adds a space between every character to reduce clustering
                        System.out.print(matrix[i][a] + " ");
                    }
                    System.out.println();
                    //skips a line after every 16 characters 
                }

                System.out.printf("%-4s%7d\n", "ʕ ・ᴥ・ʔ Your Credits:", credits); //prints a cute bear and the number of credits you have under the matrix!!!

                System.out.println(ANSI_PURPLE + "Please Enter a Direction to make a move!" + ANSI_RESET);
                direction = scan.nextLine();
                //prompts the user for a direction

                //the following if statements determines the direction the user wants to go in using WASD keys and string comparison. The user CANNOT enter a direction opposite 
                //to the head direction (because if you do, it means your snake is going to turn around and eat yourself!). The nested if statements determine if you entered a 
                //direction opposing to the head direction. If the direction enetered was valid, change the head direction and call the moceForward method to implement movement of snake
                if (direction.equalsIgnoreCase("d")) {
                    if (headDirection.equalsIgnoreCase("a")) {
                        gameOver();

                    } else {
                        headDirection = "r";
                        moveForward(matrix, headDirection);
                    }
                }
                if (direction.equalsIgnoreCase("a")) {
                    if (headDirection.equalsIgnoreCase("d")) {
                        gameOver();
                    } else {
                        headDirection = "l";
                        moveForward(matrix, headDirection);
                    }
                }
                if (direction.equalsIgnoreCase("w")) {
                    if (headDirection.equalsIgnoreCase("s")) {
                        gameOver();
                    } else {
                        headDirection = "u";
                        moveForward(matrix, headDirection);
                    }
                }
                if (direction.equalsIgnoreCase("s")) {
                    if (headDirection.equalsIgnoreCase("w")) {
                        gameOver();
                    } else {
                        headDirection = "d";
                        moveForward(matrix, headDirection);
                    }
                }
                
                headDirection = direction; //sets head direction equal to direction for next move for nested if statements
                generateMine(body, matrix); //generates a mine AT A CHANCE for every move made
            }
            
            System.out.println("would you like to play again? (Y or N)");
            playAgain = scan.nextLine();
           // prompts for y or n to determine if the player would like to play again
           
            alive = ("Y".compareToIgnoreCase(playAgain) == 0);
            //comparison, if y then alive is true and the game is reset

        } while (alive);

    }

    //Fun colours for the game! woohoo!
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

}


