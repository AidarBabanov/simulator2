package entities.cards;

import com.google.gson.JsonObject;
import spellEffects.SpellEffect;
import player.Player;

public class Spell extends Card {

    private Player player;
    private SpellEffect spellEffect;

    public Spell(Player player, JsonObject jsonCard) {
        int baseManacost = jsonCard.get("baseManaCost").getAsInt();
        String name = jsonCard.get("name").getAsString();
        String description = jsonCard.get("description").getAsString();
        JsonObject jsonSpellEffect = jsonCard.get("spell").getAsJsonObject();

//        System.out.println(jsonSpellEffect.toString());

        this.setPlayer(player);
        this.setName(name);
        this.setBaseManacost(baseManacost);
        this.setCurrentManacost(baseManacost);
        this.setDescription(description);
        this.setSpellEffect(SpellEffect.defineSpellEffect(jsonSpellEffect));
    }

    @Override
    public void playCard() {
        spellEffect.getArguments(player);
        spellEffect.performAction();
    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonCard = new JsonObject();
        jsonCard.addProperty("type", "spell");
        jsonCard.addProperty("name", super.getName());
        jsonCard.addProperty("baseManaCost", super.getBaseManacost());
        jsonCard.addProperty("currentManaCost", super.getCurrentManacost());
        jsonCard.addProperty("description", super.getDescription());
//        System.out.println(this.getName());
        jsonCard.add("spellEffect", this.getSpellEffect().jsonStateInformation());
        return jsonCard;
    }

    @Override
    public String toString() {
        return jsonStateInformation().toString();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SpellEffect getSpellEffect() {
        return this.spellEffect;
    }

    public void setSpellEffect(SpellEffect spellEffect) {
        this.spellEffect = spellEffect;
    }
}
