package entities.cards;

import com.google.gson.JsonObject;
import player.Player;

public abstract class Card {
    private String name;
    private int baseManacost;
    private int currentManacost;
    private String description;

    public Card() {
    }

    public int getBaseManacost() {
        return baseManacost;
    }

    public void setBaseManacost(int baseManacost) {
        this.baseManacost = baseManacost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCurrentManacost() {
        return currentManacost;
    }

    public void setCurrentManacost(int currentManacost) {
        this.currentManacost = currentManacost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void playCard();

    public abstract JsonObject jsonStateInformation();

    public static Card createCardFromJson(Player player, JsonObject jsonCard) {
        Card card = null;
        String cardType = jsonCard.get("type").getAsString();
        switch (cardType) {
            case "MINION":
                card = new Minion(player, jsonCard);
                break;
            case "SPELL":
                card = new Spell(player, jsonCard);
                break;
            case "HERO":
                card = new Hero();
                break;
            case "HERO_POWER":
                card = new HeroPower(player, jsonCard);
                break;
        }
        return card;
    }
}
