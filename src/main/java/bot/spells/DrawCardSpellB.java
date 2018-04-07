package bot.spells;

import com.google.gson.JsonObject;

public class DrawCardSpellB extends SpellEffectB {
    private int numberOfCards;

    public DrawCardSpellB(JsonObject jsonEffect) {
        this.setNumberOfCards(jsonEffect.get("value").getAsInt());
    }

    private void setNumberOfCards(int value) {
        this.numberOfCards = value;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    @Override
    public boolean dealsDamage() {
        return false;
    }

    @Override
    public boolean hitsEnemyHero() {
        return false;
    }

    @Override
    public int getDamage() {
        return 0;
    }
}
