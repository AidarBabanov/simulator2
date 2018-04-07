package bot;

import com.google.gson.JsonObject;

public class MinionB extends CardB {
    private int attack;
    private int health;

    public MinionB(JsonObject jsonCard) {
        super(jsonCard);
        int baseAttack = jsonCard.get("baseAttack").getAsInt();
        int baseHealth = jsonCard.get("baseHp").getAsInt();
        this.setAttack(baseAttack);
        this.setHealth(baseHealth);
    }

    public int getStats() {
        return this.getAttack() + this.getHealth();
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
