package entities.collections;

import com.google.gson.JsonObject;
import entities.cards.Card;
import player.Player;

import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.abs;

public class Deck extends LinkedList<Card> {
    private Player player;

    public Deck(Player player) {
        this.setPlayer(player);
    }

    public Card drawCard() {
        return this.remove(0);
    }

    public void shuffleCard(Card card) {
        this.add(randomDeckPosition(), card);
    }

    private int randomDeckPosition() {
        Random random = new Random();
        int randPos;
        if (this.size() == 0) randPos = 0;
        else randPos = abs(random.nextInt() % this.size());
        return randPos;
    }

    public JsonObject jsonStateInformation() {
        JsonObject jsonDeck = new JsonObject();
        jsonDeck.addProperty("cardsLeft", this.size());
        return jsonDeck;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void shuffleDeck() {
        Deck shuffledDeck = new Deck(player);
        while (this.size() > 0) {
            Card card = this.remove();
            shuffledDeck.shuffleCard(card);
        }
        this.addAll(shuffledDeck);
    }
}
