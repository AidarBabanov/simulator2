package entities.cards;

import com.google.gson.JsonObject;
import player.Player;
import spellEffects.SpellEffect;

public class HeroPower extends Card {
    private String name;
    private SpellEffect spellEffect;
    private String description;
    private Player player;
    private boolean isUsed;

    public HeroPower(Player player, JsonObject jsonCard) {
        this.setPlayer(player);
        this.setDescription(jsonCard.get("description").getAsString());
        this.setName(jsonCard.get("name").getAsString());
        this.setBaseManacost(jsonCard.get("baseManaCost").getAsInt());
        this.setCurrentManacost(this.getBaseManacost());
        JsonObject jsonSpellEffect = jsonCard.get("spell").getAsJsonObject();
        spellEffect = SpellEffect.defineSpellEffect(jsonSpellEffect);
    }

    public void use() {
        if (this.isUsed()) {
            player.write("\"message\": \"hero power is used\"");
        }
        spellEffect.getArguments(player);
        this.setUsed(true);
        spellEffect.performAction();
    }

    @Override
    public void playCard() {

    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonHeroPower = new JsonObject();
        jsonHeroPower.addProperty("type", "HERO_POWER");
        jsonHeroPower.addProperty("baseManaCost", this.getBaseManacost());
        jsonHeroPower.addProperty("currentManaCost", this.getCurrentManacost());
        jsonHeroPower.addProperty("description", this.getDescription());
        jsonHeroPower.addProperty("name", this.getName());
        jsonHeroPower.add("spell", spellEffect.jsonStateInformation());

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
