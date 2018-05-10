package entities.cards;

import com.google.gson.JsonObject;
import targetting.Target;
import filesystem.FileSystem;
import player.Player;
import states.PlayerTurn;
import states.State;

public class Hero extends Card implements Target {

    private int currentAttack;
    private HeroPower heroPower;
    private int baseHealth;
    private int currentHealth;
    private String heroClass;

    private Player player;
    private boolean isAlive;

    public Hero() {
        super();
    }

    public Hero(Player player, JsonObject jsonObject) {
        String name = jsonObject.get("name").getAsString();
        String heroClass = jsonObject.get("heroClass").getAsString();
        int health = jsonObject.get("attributes").getAsJsonObject().get("MAX_HP").getAsInt();
        int manacost = jsonObject.get("baseManaCost").getAsInt();
        this.setCurrentAttack(0);
        this.setBaseHealth(health);
        this.setCurrentHealth(this.getBaseHealth());
        this.setPlayer(player);
        this.setBaseManacost(manacost);
        this.setCurrentManacost(this.getBaseManacost());
        this.setName(name);
        this.setDescription("");
        this.setHeroClass(heroClass);

        String heroPowerStr = jsonObject.get("heroPower").getAsString();
        this.setHeroPower(FileSystem.getHeroPowerByName(player, heroPowerStr));
    }

    public void playCard() {

    }

//    public HeroB(String name, int baseManacost, String description, HeroPower heroPower, int baseHealth) {
//        super(name, baseManacost, description);
//        this.setHeroPower(heroPower);
//        this.setBaseHealth(baseHealth);
//        this.setCurrentHealth(baseHealth);
//    }

    public void useHeroPower() {
        heroPower.use();
    }

    public JsonObject jsonStateInformation() {
        JsonObject jsonHero = new JsonObject();
        jsonHero.addProperty("type", "hero");
        jsonHero.addProperty("name", super.getName());
        jsonHero.addProperty("baseManaCost", super.getBaseManacost());
        jsonHero.addProperty("currentManaCost", super.getCurrentManacost());
        jsonHero.addProperty("description", super.getDescription());
        jsonHero.add("heroPower", this.getHeroPower().jsonStateInformation());
        jsonHero.addProperty("baseHealth", this.getBaseHealth());
        jsonHero.addProperty("currentHealth", this.getCurrentHealth());
        return jsonHero;
    }


    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public HeroPower getHeroPower() {
        return heroPower;
    }

    public void setHeroPower(HeroPower heroPower) {
        this.heroPower = heroPower;
    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
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

        State state = getPlayer().getStateManager().getState();
        if (state instanceof PlayerTurn) {
            PlayerTurn playerTurn = (PlayerTurn) state;
            playerTurn.setTurnEnd(true);
        }
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }
}
