package pl.edu.agh.iwium.blackjack.bot;

import algorithms.ISelector;
import algorithms.RandomSelector;

public abstract class Bots {
    public static final ISelector RANDOM = new RandomSelector();
    public static final ISelector DEALER_STRATEGY = new BlackjackDealerStrategy();
    public static final ISelector NEVER_BUST_STRATEGY = new BlackjackNeverBustStrategy();
}
