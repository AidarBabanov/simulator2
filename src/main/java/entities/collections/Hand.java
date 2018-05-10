package entities.collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.cards.Card;
import player.Player;

import java.util.LinkedList;
import java.util.Random;

public class Hand extends LinkedList<Card> {
    private Player player;

    public Hand(Player player) {
        this.setPlayer(player);
    }

    public void drawCard() {
        Card drawnCard = this.getPlayer().getDeck().drawCard();
        if (this.size() < 10) this.add(drawnCard);
        else burnCard(drawnCard);
    }

    public void burnCard(Card card) {
    }

    private int randomHandPosition() {
        Random random = new Random();
        return Math.abs(random.nextInt() % this.size());
    }

    public Card discard() {
        return this.remove(randomHandPosition());
    }

    public JsonObject jsonStateInformation() {
        JsonObject jsonHand = new JsonObject();
        jsonHand.addProperty("numberOfCards", this.size());
        JsonArray jsonArray = new JsonArray();
        for (Card card : this) {
            jsonArray.add(card.jsonStateInformation());
        }
        jsonHand.add("cards", jsonArray);
        return jsonHand;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
