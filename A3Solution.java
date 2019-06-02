/*******************************************************************
 *
 * Class A3Solution: The purpose of this program is to play a simple 
 *                   card game called War, using stacks and queues.
 * COMP 2140 Section: A01 or A02
 * INSTRUCTORS     Helen Cameron and Rob Guderian
 * ASSIGNMENT     3
 * AUTHOR         Helen Cameron and Rob Guderian
 * VERSION        26 October 2018
 *
 *********************************************************************/

import java.io.*;
import java.util.*;




public class A3Solution {

    private static final int NUMBER_DECKS = 1; // The number of card decks to use in the game.

    public static void main( String[] args ) {
	System.out.println( "\n\nCOMP 2140 Assginment 3 Fall 2018\nPlaying the War card game...\n" );

	War.playGame( NUMBER_DECKS );

	System.out.println( "\n\nProgram ends normally" );
    }
    
} // end class A3Solution


//===========================================================================

/*******************************************************************
 *  Class Queue
 *
 *  An ordinary queue implemented as a circular array with a front 
 *  index (of the first item in the queue) and an end index (of the
 *  empty position after the last item in the queue).
 *
 *  To allow us to differentiate between an empty and a full queue,
 *  one position is always empty in the array.
 *
 *  Note:
 *   If the queue is full, enter() silently does nothing.
 *   If the queue is empty, leave() and front() return Integer.MIN_VALUE
 *   and otherwise silently do nothing.
 *
 *********************************************************************/
class Queue {
    private static final int MAX_SIZE = 53;

    private int[] queueArray;
    private int front; // The index of the first item in the queue (if any)
    private int end; // The index of the next empty position after the
                     // last item in the queue (if any)

    // Create an empty queue (assumes no more than MAX_SIZE-1 items will be stored)
    public Queue() {
	queueArray = new int[ MAX_SIZE ];
	front = 0;
	end = 0;
    }

    // Create an empty queue (is given the maximum number of items to be stored)
    public Queue( int size ) {
	queueArray = new int[ size+1 ]; // one extra array position is always empty
	front = 0;
	end = 0;
    }

    // Return true if the queue is empty, and returns false otherwise.
    public boolean isEmpty() {
	return front == end;
    }

    // Returns true if the queue is full (cannot enter() a new item) and false otherwise.
    public boolean isFull() {
	return (end+1) % queueArray.length == front;
    }

    // Add a new item to the end of the queue if there is any room.
    // (If there is no room, silently do nothing.)
    public void enter( int newCard ) {
	if ( !isFull() ) {
	    queueArray[ end ] = newCard;
	    end = (end+1) % queueArray.length;
	}
    } // end enter

    // Remove and return the item at the front of the queue.
    // (If there are no items on the queue, silently return Integer.MIN_VALUE.)
    public int leave() {
	int firstCard = Integer.MIN_VALUE;

	if ( !isEmpty() ) {
	    firstCard = queueArray[ front ];
	    front = (front + 1) % queueArray.length;
	}
	
	return firstCard;
    } // end leave

    // Return the item at the front of the queue, without changing the queue.
    // (If there are no items on the queue, silently return Integer.MIN_VALUE.)
    public int front() {
	int firstCard = Integer.MIN_VALUE;

	if ( !isEmpty() ) {
	    firstCard = queueArray[ front ];
	}

	return firstCard;
    }
	
} // end class Queue


//===========================================================================

/*******************************************************************
 *  Class Stack
 *
 *  An ordinary queue implemented as an array with a top
 *  index (of the top item on the stack).  The bottom item, if there
 *  are any items on the stack, is always at index 0.
 *
 *  Note:
 *   If the stack is full, push() silently does nothing.
 *   If the stack is empty, pop() and top() return Integer.MIN_VALUE
 *   and otherwise silently do nothing.
 *
 *********************************************************************/
class Stack {
    
    private static final int MAX_SIZE = 52;

    private int[] stackArray;
    private int top; // index of the top item

    // Creates an empty stack (assumes no more than MAX_SIZE items will be stored).
    public Stack() {
	stackArray = new int[ MAX_SIZE ];
	top = -1;
    }

    // Creates an empty stack using the number (size) of items to be stored on the stack.
    public Stack( int size ) {
	stackArray = new int[ size ];
	top = -1;
    }

    // Returns true if the stack is empty, false otherwise.
    public boolean isEmpty() {
	return top == -1;
    }

    // Add a new item to the top of the stack.
    // If the stack is full, then push() silently does nothing.
    public void push( int newCard ) {
	if ( top < stackArray.length-1 ) {
	    top++;
	    stackArray[ top ] = newCard;
	}
    }

    // Returns the top item stored on the stack (if any) --- the stack is not changed.
    // If the stack is empty, it silently return Integer.MIN_VALUE.
    public int top() {
	int topCard = Integer.MIN_VALUE;

	if ( top != -1 ) {
	    topCard = stackArray[ top ];
	}

	return topCard;
    } // end top

    // Removes and returns the top item stored on the stack (if any).
    // If the stack is empty, it silently return Integer.MIN_VALUE without doing anything else.
    public int pop() {
	int topCard = Integer.MIN_VALUE;

	if ( top != -1 ) {
	    topCard = stackArray[ top ];
	    top--;
	}

	return topCard;
    }
    
} // end class Stack


//===========================================================================

/*******************************************************************
 *  Class War
 *
 *  Contains all information about cards and decks of cards, and the
 *  method that plays the game (and its helper methods).
 *
 *  Assumes:
 *    - The cards in the game are represented by the integers
 *      0, ..., DECK_SIZE * numberDecks - 1.
 *    - The card represented by integer c:
 *          - has suit ( c / CARDS_PER_SUIT ) % NUMBER_SUITS,
 *            where 0 = spades, 1 = hearts, 2 = clubs, and 3 = diamonds
 *          - has rank c % CARD_PER_SUIT, where 0 = Ace, 1 = two,
 *            2 = three, 3 = four, ..., 9 = ten, 10 = Jack, 11 = Queen,
 *            12 = King (and that's the order of the ranks from lowest 
 *            rank to highest rank)
 *
 *********************************************************************/

class War {

    // Constants controlling what cards are in a deck of cards
    private static final int CARDS_PER_SUIT = 13;
    private static final int NUMBER_SUITS = 4;
    private static final int DECK_SIZE = CARDS_PER_SUIT * NUMBER_SUITS;

    // Constants controlling what is printed for each card
    // For printing (if they don't work on your computer, replace with 'S', 'H', 'C', 'D')
    private static final char[] SUITS = { 9824, 9829, 9830, 9827 }; // spades, hearts,
                                                                    // clubs, diamonds
    private static final String[] RANKS = { " A", " 2", " 3", " 4", " 5", " 6",
					    " 7", " 8", " 9", "10", " J", " Q", " K" };

    // Constant controlling the number of cards that a player must ante up in a war.
    private static final int NUM_ANTE = 2;

    // Whenever randomness is needed ... (shuffling the deck, randomly choosing
    //  a winner when both players run out of cards at the same time in a war)
    private static Random generator = new Random( );

    /*******************************************************************
     *  playGame
     *
     *  Plays one game of the card game War, printing out the game play
     *  as it happens.
     *
     *  Allows the user to specify the number of cards decks to use.
     *  (You can assume that exactly one deck will be used in your program.)
     *  
     *  Game set up: The deck (or decks) is thoroughly shuffled (randomized).
     *               Then each player is dealt half the cards.
     *               The cards are left face down --- the players are not 
     *               allowed to look at their hands.
     *  Game play overview: The game consists of a sequence of rounds.  
     *               In a round, the players reveal some of their cards.
     *               One of the players wins the round (based on comparing 
     *               the ranks of some of the revealed cards).
     *               The round winner gets all of the revealed cards, which 
     *               are added (face down) to end of the round winner's hand. 
     *  Game end: The game ends when one player has all of the cards --- that 
     *               player is the winner of the game.
     *
     *  One round of the game:
     *      The players flip over (reveal) the front card of their hands and
     *      compare the ranks of the revealed cards to see who wins the round:
     *         - If the two revealed cards are not equal rank, then the player 
     *           with the highest ranked card wins the round.
     *         - If the revealed cards are equal rank, then it is WAR!
     *           The winner of the war is the winner of the round.
     *      The winner of the round gets all of the revealed cards (including 
     *      all cards revealed in a war), which are added (face down) to end 
     *      of the round winner's hand. 
     *
     *********************************************************************/
    public static void playGame( int numberDecks ) {
	Queue player1Hand = new Queue( DECK_SIZE * numberDecks );
	Queue player2Hand = new Queue( DECK_SIZE * numberDecks );
	int player1Card, player2Card;

	// Shuffle and then deal half the cards to each player.
	dealCards( player1Hand, player2Hand, DECK_SIZE * numberDecks );

	// Play rounds until one player has all the cards
	while ( !player1Hand.isEmpty() && !player2Hand.isEmpty() ) {
	    // Each player flips over (reveals) one card
	    player1Card = player1Hand.leave();
	    player2Card = player2Hand.leave();
	    System.out.print( "Player 1: " + cardToString( player1Card )
			      + "  Player 2: " + cardToString( player2Card ) );

	    // The revealed cards are compared to see who wins
	    if ( isHigherRank( player1Card, player2Card ) ) { // Player1 gets both cards
		player1Hand.enter( player2Card );
		player1Hand.enter( player1Card );
		System.out.println( "   Player1 gets both cards." );
	    } else if ( isHigherRank( player2Card, player1Card ) ) { // Player2 gets both cards
		player2Hand.enter( player1Card );
		player2Hand.enter( player2Card );
		System.out.println( "   Player2 gets both cards." );
	    } else { // The cards have equal rank: it is WAR!
		System.out.println( "\n ** It is WAR!" );
		handleWar( player1Hand, player2Hand, player1Card, player2Card );
	    }
	
	} // end while

	// Figure out who won the game
	if ( !player1Hand.isEmpty() ) {
	    System.out.println( "\n ** Player 2 is out of cards." );
	    System.out.println( "\nPlayer 1 won the game!\n" );
	} else {
	    System.out.println( "\n ** Player 1 is out of cards." );
	    System.out.println( "\nPlayer 2 won the game!\n" );
	}

    } // end playGame

    /*******************************************************************
     *  dealCards
     *
     *  Shuffle the cards thoroughly, and then alternately deal the cards
     *  to the players until all cards are dealt.
     *
     *  Assumes:
     *    - The cards in the game are represented by the integers
     *      0, ..., numCards - 1.
     *
     *********************************************************************/
    private static void dealCards( Queue player1Hand, Queue player2Hand, int numCards ) {
	int[] cards = new int[ numCards ];
	int index1, index2, temp; // Used for shuffling the deck

	// Create the decks of cards in an array
	for ( int i = 0; i < numCards; i++ ) {
	    cards[i] = i;
	}

	// Shuffle the deck
	for ( int i = 0; i < numCards * 4; i++ ) {
	    // randomly swap two cards
	    index1 = generator.nextInt( numCards );
    	    index2 = generator.nextInt( numCards );
	    temp = cards[ index1 ];
	    cards[ index1 ] = cards[ index2 ];
	    cards[ index2 ] = temp;
	}

	// Alternately deal cards to the two players
	for ( int i = 0; i < numCards; i++ ) {
	    if ( i % 2 == 0 ) // cards in even positions go to player 1
		player1Hand.enter( cards[ i ] );
	    else // cards in odd positions go to player 2
		player2Hand.enter( cards[ i ] );
	}
    } // end dealCards

    /*******************************************************************
     *  handleWar
     *
     *  The two players have revealed cards of equal rank (player1Card
     *  and player2Card).  So it is WAR!
     *
     *  The players repeat the following steps until one player wins the 
     *  war or one player runs out of cards (the other player --- the one 
     *  with cards --- wins the war):
     *
     *  - Each player flips over (reveals) two more cards in a stack on 
     *    top of the equal-ranked already revealed card.
     *    These two cards are called the ``ante'' --- they are just there 
     *    to make the win bigger.
     *
     *  - Then each player flips over (reveals) one more card, which goes 
     *    on the top of the stack. These are the cards that are compared.
     *
     *  - The ranks of the last revealed card from each player are compared 
     *    to determine who wins the war:
     *      - If the top two revealed cards are not equal rank, then the 
     *        player with the highest ranked card wins the war. 
     *      - If the top two revealed cards are equal rank, then it is 
     *        still WAR! (The players repeat the war steps.)
     *
     *   - Note: If a player runs out of cards before being able to reveal
     *           the required number of cards, then that player loses.
     *           (Essentially, the losing player is playing the empty card 
     *           and the empty card is lower rank than any real card).
     *
     *********************************************************************/
    private static void handleWar( Queue player1Hand, Queue player2Hand,
				   int player1Card, int player2Card ) {
	Stack player1Visible = new Stack();
	Stack player2Visible = new Stack();
	int player1Ante, player2Ante; // For counting how many cards a player antes up!
	boolean player1Won, player2Won; // who won this war?

	// Nobody won the war yet
	player1Won = player2Won = false;

	// Put the already-revealed equal-ranked cards into the players' stacks.
	player1Visible.push( player1Card );
	player2Visible.push( player2Card );

	// Now it's war!
	while ( !player1Won && !player2Won ) {
		
	    // Pull two cards (the ante) from each player, if possible.
	    // (Still have to compare the 3rd card afterward.)
	    System.out.println("Player 1 ante:");
	    for ( player1Ante = 0; player1Ante < NUM_ANTE && !player1Hand.isEmpty();
		 player1Ante++) {
		int tempCard =  player1Hand.leave();
		System.out.println(cardToString( tempCard ));
		player1Visible.push(tempCard);
	    }
	    System.out.println("Player 2 ante:");
	    for ( player2Ante = 0 ; player2Ante < NUM_ANTE && !player2Hand.isEmpty();
		 player2Ante++) {
		int tempCard =  player2Hand.leave();
		System.out.println( cardToString( tempCard ));
		player2Visible.push(tempCard);
	    }

	    // Check for problems (did somebody run out of cards?)
	    // If no problems, get a third card from each player and compare.
	    if ( player1Ante == player2Ante &&
		 (player1Ante < 2 || ( player1Hand.isEmpty() && player2Hand.isEmpty() )) ) {
		// Players ran out of cards at the same time!!!
		// This is so unlikely that there's no rule for it.
		// Who cares what you do!
		System.out.println( " **** Unlikely event: Both players ran out of"
				    + " cards during a war at the same time!" );
		System.out.print( "      Randomly choosing the winner:" );
		if ( generator.nextInt( 100 ) < 50 ) {
		    player1Won = true;
		    System.out.println( " ** Player 1 won this battle." );
		    appendVisibleStacksToHand( player1Visible, player2Visible,
					       player1Hand );
		} else {
		    player2Won = true;
		    System.out.println( " ** Player 2 won this battle." );
		    appendVisibleStacksToHand( player2Visible, player1Visible,
					       player2Hand );
		}
	    } else if ( player1Ante < player2Ante
			|| ( player1Hand.isEmpty() && !player2Hand.isEmpty() ) ) {
		// player1 ran out of cards before player2
		player2Won = true;
		System.out.println( " ** Player 2 won this battle." );
		appendVisibleStacksToHand( player2Visible, player1Visible, player2Hand );
	    } else if ( player2Ante < player1Ante
			|| ( !player1Hand.isEmpty() && player2Hand.isEmpty() )  ) {
		// player2 ran out of cards before player1
		player1Won = true;
		System.out.println( " ** Player 1 won this battle." );
		appendVisibleStacksToHand( player1Visible, player2Visible, player1Hand );
	    } else {
		// Everybody made ante and still has at least the third card to compare
		player1Visible.push( player1Hand.leave() );
		System.out.print( "Player 1: " + cardToString( player1Visible.top() ) );
		player2Visible.push( player2Hand.leave() );
		System.out.println( "  Player 2: " + cardToString( player2Visible.top() ) );
		if ( isHigherRank( player1Visible.top(), player2Visible.top() ) ) {
		    player1Won = true;
		    System.out.println( " ** Player 1 won this battle." );
		    appendVisibleStacksToHand( player1Visible, player2Visible, player1Hand );
		} else  if ( isHigherRank( player2Visible.top(), player1Visible.top() ) ) {
		    player2Won = true;
		    System.out.println( " ** Player 2 won this battle." );
		    appendVisibleStacksToHand( player2Visible, player1Visible, player2Hand );
		} else { // the war continues because the top cards were equal
		    System.out.println( " ** WAR continues!" );
		}
	    } // end if - else if - else
	} // end while
	
    } // end handleWar

    /*******************************************************************
     *  appendVisibleStacksToHand
     *
     *  One player, "player A", won the war (the player with hand playerAHand).
     *  So player A gets all cards in the war stacks of both players.
     *
     *  Move all the cards from both stacks into player A's hand.
     *
     *********************************************************************/
    private static void appendVisibleStacksToHand( Stack visibleA, Stack visibleB,
						   Queue playerAHand ){
	while ( !visibleB.isEmpty() ) {
	    playerAHand.enter( visibleB.pop() );
	}
	while ( !visibleA.isEmpty() ) {
	    playerAHand.enter( visibleA.pop() );
	}
    } // end appendVisibleStacksToHand

    /*******************************************************************
     *  isHigherRank
     *
     *  Returns true if card1 has higher rank than card2;
     *  returns false otherwise.
     *
     *********************************************************************/
    private static boolean isHigherRank( int card1, int card2 ) {
	return (card1 % CARDS_PER_SUIT) > (card2 % CARDS_PER_SUIT );
    }

    /*******************************************************************
     *  cardToString
     *
     *  Returns a string containing a text representation of the card
     *  represented by the integer parameter card.
     *
     * Int   Suit     (Ranks)
     * 0-12  Spades   (in order of Ace, 2, 3, ..., 9, 10, Jack, Queen, King)
     * 13-25 Hearts   (in order of Ace, 2, 3, ..., 9, 10, Jack, Queen, King)
     * 26-38 Diamonds (in order of Ace, 2, 3, ..., 9, 10, Jack, Queen, King)
     * 39-52 Clubs    (in order of Ace, 2, 3, ..., 9, 10, Jack, Queen, King)
     *
     * For example, if card = 22, then it returns the String containing
     * "10H".
     *
     *********************************************************************/
    private static String cardToString( int card ) {
	int suitIndex, rankIndex;
	String cardString;

	card = card % DECK_SIZE; // In case we're playing with more than one deck.
	suitIndex = card / CARDS_PER_SUIT;
	rankIndex = card % CARDS_PER_SUIT;

	return  RANKS[ rankIndex ] + SUITS[ suitIndex ];
    }
} // end class War
