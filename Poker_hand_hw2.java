// package com.gradescope.cs201;
import java.util.Arrays;

public class Poker_hand_hw2 {
    private String[] cards;

    /* A constructor that gets an array of 5 strings representing 5 cards. In each string, the last
    character represents the suit (H: Heart, D: Diamond, C: Club, S: Spade) and the
    preceding characters represent the card rank (2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A). An
    IllegalArgumentException is thrown in the case the input is not in this format. */

    public Poker_hand_hw2(String[] cards) {        
        if (cards == null || cards.length != 5) {
            throw new IllegalArgumentException("Invalid input: Exactly 5 cards are required");
        }

        for (String card : cards) {
            if (!isValidCardFormat(card)) {
                throw new IllegalArgumentException("Invalid card format: " + card);
            }
        }

        for (int i = 0; i < cards.length; i++) {
            if (cards[i].charAt(0) == '1' && cards[i].charAt(1) == '0'){
                cards[i] = ":" + cards[i].charAt(2);
            } else if (cards[i].charAt(0) == 'J') {
                cards[i] = ";" + cards[i].charAt(1);
            } else if (cards[i].charAt(0) == 'Q') {
                cards[i] = "<" + cards[i].charAt(1);
            } else if (cards[i].charAt(0) == 'K') {
                cards[i] = "=" + cards[i].charAt(1);
            } else if (cards[i].charAt(0) == 'A') {
                cards[i] = ">" + cards[i].charAt(1);
            }
        }
        this.cards = cards;
        Arrays.sort(this.cards);
    }

    private boolean isValidCardFormat(String card) {
      return card.matches("[2-9JQKA][CDHS]") || card.matches("10[CDHS]");
   }
    /* A method “get_category” that has no parameters and returns an integer representing the
    category of this hand (9: straight flush, 8: four of a kind, 7: full house, …, 1: high card). */

    public int get_category() {
        if (isStraightFlush()) return 9;
        if (isFourOfAKind()) return 8;
        if (isFullHouse()) return 7;
        if (isFlush()) return 6;
        if (isStraight()) return 5;
        if (isThreeOfAKind()) return 4;
        if (isTwoPair()) return 3;
        if (isOnePair()) return 2;
        // Default to high card if no other category is matched
        return 1;
    }

    private boolean isStraightFlush() {
        return isStraight() && isFlush();
    }

    private boolean isFourOfAKind() {
        return (cards[0].charAt(0) == cards[3].charAt(0)) ||
               (cards[1].charAt(0) == cards[4].charAt(0));
    }

    private boolean isFullHouse() {
        return (cards[0].charAt(0) == cards[1].charAt(0) && cards[1].charAt(0) != cards[2].charAt(0) && cards[2].charAt(0) == cards[4].charAt(0)) ||
               (cards[0].charAt(0) == cards[2].charAt(0) && cards[2].charAt(0) != cards[3].charAt(0) && cards[3].charAt(0) == cards[4].charAt(0));
    }

    private boolean isFlush() {
        return cards[0].charAt(1) == cards[1].charAt(1) &&
               cards[1].charAt(1) == cards[2].charAt(1) &&
               cards[2].charAt(1) == cards[3].charAt(1) &&
               cards[3].charAt(1) == cards[4].charAt(1);
    }

    private boolean isStraight() {

        for (int i = 1; i < cards.length; i++) {
            if (cards[i].charAt(0) - cards[i - 1].charAt(0) != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isThreeOfAKind() {
        return (cards[0].charAt(0) == cards[2].charAt(0)) ||
               (cards[1].charAt(0) == cards[3].charAt(0)) ||
               (cards[2].charAt(0) == cards[4].charAt(0));
    }

    private boolean isTwoPair() {
        return (cards[0].charAt(0) == cards[1].charAt(0) && cards[2].charAt(0) == cards[3].charAt(0)) ||
               (cards[0].charAt(0) == cards[1].charAt(0) && cards[3].charAt(0) == cards[4].charAt(0)) ||
               (cards[1].charAt(0) == cards[2].charAt(0) && cards[3].charAt(0) == cards[4].charAt(0));
    }

    private boolean isOnePair() {
        return (cards[0].charAt(0) == cards[1].charAt(0)) ||
               (cards[1].charAt(0) == cards[2].charAt(0)) ||
               (cards[2].charAt(0) == cards[3].charAt(0)) ||
               (cards[3].charAt(0) == cards[4].charAt(0));
    }
    
    /* A method “compare_to” that gets another hand (i.e. an object of Poker_hand_hw2 class)
    as a parameter and returns an integer which is either -1, 0, or 1 if this hand is ranked
    lower, equal, or higher than the other hand respectively. */

    public int compare_to(Poker_hand_hw2 otherHand) {
        int thisCategory = this.get_category();
        int otherCategory = otherHand.get_category();

        if (thisCategory != otherCategory) {
            return Integer.compare(thisCategory, otherCategory);
        } else {
            // Handle tiebreakers within the same category
            return compareTiebreaker(otherHand);
        }
    }
    private int compareTiebreaker(Poker_hand_hw2 otherHand) {
        int thisCategory = this.get_category();

        if (thisCategory == 9) {
            return compareStraightFlush(otherHand);
        } else if (thisCategory == 8) {
            return compareFourOfAKind(otherHand);
        } else if (thisCategory == 7) {
            return compareFullHouse(otherHand);
        } else if (thisCategory == 6) {
            return compareFlush(otherHand);
        } else if (thisCategory == 5) {
            return compareStraight(otherHand);
        } else if (thisCategory == 4) {
            return compareThreeOfAKind(otherHand);
        } else if (thisCategory == 3) {
            return compareTwoPair(otherHand);
        } else if (thisCategory == 2) {
            return compareOnePair(otherHand);
        } else {
            return compareHighCard(otherHand);
        }
    }
    private int compareStraightFlush(Poker_hand_hw2 otherHand) {
        return compareHighCard(otherHand);
    }
    private int compareFourOfAKind(Poker_hand_hw2 otherHand) {
        int thisFourOfAKind = getFourOfAKind();
        int otherFourOfAKind = otherHand.getFourOfAKind();

        if (thisFourOfAKind != otherFourOfAKind) {
            return Integer.compare(thisFourOfAKind, otherFourOfAKind);
        } else {
            return compareHighCard(otherHand);
        }
    }
    private int compareFullHouse(Poker_hand_hw2 otherHand) {
        int thisThreeOfAKind = getThreeOfAKind();
        int otherThreeOfAKind = otherHand.getThreeOfAKind();

        if (thisThreeOfAKind != otherThreeOfAKind) {
            return Integer.compare(thisThreeOfAKind, otherThreeOfAKind);
        } else {
            return compareHighCard(otherHand);
        }
    }
    private int compareFlush(Poker_hand_hw2 otherHand) {
        return compareHighCard(otherHand);
    }
    private int compareStraight(Poker_hand_hw2 otherHand) {
        return compareHighCard(otherHand);
    }
    private int compareThreeOfAKind(Poker_hand_hw2 otherHand) {
        int thisThreeOfAKind = getThreeOfAKind();
        int otherThreeOfAKind = otherHand.getThreeOfAKind();

        if (thisThreeOfAKind != otherThreeOfAKind) {
            return Integer.compare(thisThreeOfAKind, otherThreeOfAKind);
        } else {
            return compareHighCard(otherHand);
        }
    }
    private int compareTwoPair(Poker_hand_hw2 otherHand) {
        // Compare the rank of each pair among two pairs since both hands are two pairs
        int thisHighPair = getHighPair();
        int otherHighPair = otherHand.getHighPair();

        if (thisHighPair != otherHighPair) {
            return Integer.compare(thisHighPair, otherHighPair);
        } else {
            int thisLowPair = getLowPair();
            int otherLowPair = otherHand.getLowPair();

            if (thisLowPair != otherLowPair) {
                return Integer.compare(thisLowPair, otherLowPair);
            } else {
                return compareHighCard(otherHand);
            }
        }
    }
    private int compareOnePair(Poker_hand_hw2 otherHand) {
        int thisPair = getPair();
        int otherPair = otherHand.getPair();

        if (thisPair != otherPair) {
            return Integer.compare(thisPair, otherPair);
        } else {
            return compareHighCard(otherHand);
        }
    }
    private int compareHighCard(Poker_hand_hw2 otherHand) {
        for (int i = cards.length - 1; i >= 0; i--) {
            if (cards[i].charAt(0) != otherHand.cards[i].charAt(0)) {
                return Integer.compare(cards[i].charAt(0), otherHand.cards[i].charAt(0));
            }
        }
        return 0;
    }
    private int getFourOfAKind() {
        if (cards[0].charAt(0) == cards[3].charAt(0)) {
            return cards[0].charAt(0);
        } else {
            return cards[1].charAt(0);
        }
    }
    private int getThreeOfAKind() {
        if (cards[0].charAt(0) == cards[2].charAt(0)) {
            return cards[0].charAt(0);
        } else if (cards[1].charAt(0) == cards[3].charAt(0)) {
            return cards[1].charAt(0);
        } else {
            return cards[2].charAt(0);
        }
    }
    private int getHighPair() {
        if (cards[0].charAt(0) == cards[1].charAt(0) && cards[2].charAt(0) == cards[3].charAt(0)) {
            return cards[2].charAt(0);
        } else if (cards[0].charAt(0) == cards[1].charAt(0) && cards[3].charAt(0) == cards[4].charAt(0)) {
            return cards[3].charAt(0);
        } else {
            return cards[3].charAt(0);
        }
    }
    private int getLowPair() {
        if (cards[0].charAt(0) == cards[1].charAt(0) && cards[2].charAt(0) == cards[3].charAt(0)) {
            return cards[0].charAt(0);
        } else if (cards[0].charAt(0) == cards[1].charAt(0) && cards[3].charAt(0) == cards[4].charAt(0)) {
            return cards[0].charAt(0);
        } else {
            return cards[1].charAt(0);
        }
    }
    private int getPair() {
        if (cards[0].charAt(0) == cards[1].charAt(0)) {
            return cards[0].charAt(0);
        } else if (cards[1].charAt(0) == cards[2].charAt(0)) {
            return cards[1].charAt(0);
        } else if (cards[2].charAt(0) == cards[3].charAt(0)) {
            return cards[2].charAt(0);
        } else {
            return cards[3].charAt(0);
        }
    }
}
