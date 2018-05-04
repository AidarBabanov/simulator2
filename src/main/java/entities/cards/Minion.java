package entities.cards;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import targetting.Target;
import player.Player;

public class Minion extends Card implements Target {
    private int baseAttack;
    private int baseHealth;
    private int currentAttack;
    private int currentHealth;

    private boolean canAttack;
    private boolean isAlive;

    private Player player;

    public Minion() {
        super();
    }

    public Minion(Player player, JsonObject jsonCard) {
        this.setPlayer(player);
        int baseManacost = jsonCard.get("baseManaCost").getAsInt();
        int baseAttack = jsonCard.get("baseAttack").getAsInt();
        String name = jsonCard.get("name").getAsString();
        String description = jsonCard.get("description").getAsString();
        int baseHealth = jsonCard.get("baseHp").getAsInt();
        this.setPlayer(player);
        this.setName(name);
        this.setBaseManacost(baseManacost);
        this.setCurrentManacost(baseManacost);
        this.setDescription(description);
        this.setBaseAttack(baseAttack);
        this.setBaseHealth(baseHealth);
        this.setCurrentHealth(baseHealth);
        this.setCurrentAttack(baseAttack);
    }

    public void playCard() {
        player.write("{\"message\": \"Set minion position\"}");
        String message = player.read();
        int position = fromStringToJsonOBJ(message).get("tablePosition").getAsInt();

        this.setCanAttack(false);
        player.setCurrentManaCrystals(player.getCurrentManaCrystals() - super.getCurrentManacost());
        summonMinion(position);
    }

    public void summonMinion(int positionOnTable) {
        player.getTable().summonMinion(this, positionOnTable);
    }

    public void attack(Target target) {
        this.setAlive(true);
        target.getDamage(this.currentAttack);
        this.getDamage(target.responseDamage());
        this.setCanAttack(false);
    }

//    public MinionB(String name, int baseManacost, String description, int baseAttack, int baseHealth) {
//        super(name, baseManacost, description);
//        super.setCurrentManacost(baseManacost);
//        this.setBaseAttack(baseAttack);
//        this.setBaseHealth(baseHealth);
//        this.setCurrentAttack(baseAttack);
//        this.setCurrentHealth(baseHealth);
//    }


    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public JsonObject jsonStateInformation() {
        JsonObject jsonCard = new JsonObject();
        jsonCard.addProperty("type", "MINION");
        jsonCard.addProperty("name", super.getName());
        jsonCard.addProperty("baseManaCost", super.getBaseManacost());
        jsonCard.addProperty("currentManaCost", super.getCurrentManacost());
        jsonCard.addProperty("description", super.getDescription());
        jsonCard.addProperty("canAttack", this.isCanAttack());
        jsonCard.addProperty("baseAttack", this.getBaseAttack());
        jsonCard.addProperty("currentAttack", this.getCurrentAttack());
        jsonCard.addProperty("baseHealth", this.getBaseHealth());
        jsonCard.addProperty("currentHealth", this.getCurrentHealth());
        return jsonCard;
    }

    private JsonObject fromStringToJsonOBJ(String message) {
        return new JsonParser().parse(message).getAsJsonObject();
    }

    @Override
    public String toString() {
        return jsonStateInformation().toString();
    }

    @Override
    public boolean isTargetable() {
        return true;
    }

    public void getDamage(int damage) {
        this.setCurrentHealth(this.getCurrentHealth() - damage);
        if (this.getCurrentHealth() <= 0) this.die();
    }

    public int responseDamage() {
        return this.getCurrentAttack();
    }

    public void die() {
        player.getTable().killMinion(this);
        this.setAlive(false);
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}

