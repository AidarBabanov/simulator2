package spellEffects;

import com.google.gson.JsonObject;
import targetting.Target;
import player.Player;

import java.util.List;

public class DrawCardSpell extends SpellEffect {

    private Player player;
    private int numberOfCards;

    public DrawCardSpell(JsonObject effect){
        this.setNumberOfCards(effect.get("value").getAsInt());
    }

    @Override
    public void getArguments(Player player) {
        this.player = player;
    }

    @Override
    public void forceTargets(List<Target> targets) {

    }

    @Override
    public void performAction() {
        for (int i = 0; i < numberOfCards; i++) player.drawCard();
    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "DrawCardSpell");
        jsonObject.addProperty("value", numberOfCards);
        return jsonObject;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}
