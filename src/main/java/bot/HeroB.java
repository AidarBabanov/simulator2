package bot;

import com.google.gson.JsonObject;

public class HeroB {
    private int health;
    private HeroPowerB heroPowerB;

    public HeroB() {
    }

    public HeroB(JsonObject jsonObject) {
        int health = jsonObject.get("currentHealth").getAsInt();
        this.setHealth(health);
        this.setHeroPowerB(new HeroPowerB(jsonObject.get("heroPower").getAsJsonObject()));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public HeroPowerB getHeroPowerB() {
        return heroPowerB;
    }

    public void setHeroPowerB(HeroPowerB heroPowerB) {
        this.heroPowerB = heroPowerB;
    }
}
