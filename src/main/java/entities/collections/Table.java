package entities.collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.cards.Card;
import entities.cards.Minion;
import player.Player;

import java.util.LinkedList;

public class Table extends LinkedList<Minion> {
    private Player player;

    public Table(Player player) {
        this.setPlayer(player);
    }

    public void playMinion(Minion minion, int position) {
        summonMinion(minion, position);
    }

    public void summonMinion(Minion minion, int position) {
        if (position > this.size()) position = this.size();
        this.add(position, minion);
    }

    public void killMinion(Minion minion) {
        this.remove(minion);
    }

    public Minion returnMinion(int position) {
        return this.remove(position);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
}
