package entities.cards;

import com.google.gson.JsonObject;
import player.Player;
import spellEffects.SpellEffect;

public class HeroPower extends Card {
    private SpellEffect spellEffect;
    private String description;
    private Player player;

    public HeroPower(Player player, JsonObject jsonCard) {
        this.setPlayer(player);
        this.setDescription(jsonCard.get("description").getAsString());
        this.setBaseManacost(jsonCard.get("baseManaCost").getAsInt());
        this.setCurrentManacost(this.getBaseManacost());
        JsonObject jsonSpellEffect = jsonCard.get("spell").getAsJsonObject();
        spellEffect = SpellEffect.defineSpellEffect(jsonSpellEffect);
    }

    public void use() {
        spellEffect.getArguments(player);
        spellEffect.performAction();
    }

    @Override
    public void playCard() {

    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonHeroPower = new JsonObject();
        jsonHeroPower.addProperty("type", "heroPower");
        jsonHeroPower.addProperty("baseManaCost", this.getBaseManacost());
        jsonHeroPower.addProperty("currentManaCost", this.getCurrentManacost());
        jsonHeroPower.addProperty("description", this.getDescription());

        return jsonHeroPower;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.jsonStateInformation().toString();
    }
}
