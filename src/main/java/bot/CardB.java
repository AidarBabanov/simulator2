package bot;

import com.google.gson.JsonObject;
import entities.cards.Card;
import player.Player;

public class CardB {
    private int manaCost;
    private String name;
    private String description;
    private boolean used;

    public CardB(JsonObject jsonCard){
        int baseManaCost = jsonCard.get("baseManaCost").getAsInt();
        String name = jsonCard.get("name").getAsString();
        String description = jsonCard.get("description").getAsString();

        this.setDescription(description);
        this.setManaCost(baseManaCost);
        this.setName(name);
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static CardB createCardFromJson(JsonObject jsonCard) {
        CardB card = null;
        String cardType = jsonCard.get("type").getAsString();
        switch (cardType) {
            case "MINION":
                card = new MinionB(jsonCard);
                break;
            case "SPELL":
                card = new SpellB(jsonCard);
                break;
            case "HERO_POWER":
                card = new SpellB(jsonCard);
                break;
        }
        return card;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
