package bot;

import com.google.gson.JsonObject;

public class HeroB {
    private int health;

    public HeroB() {
    }

    public HeroB(JsonObject jsonObject) {
        int health = jsonObject.get("currentHealth").getAsInt();
        this.setHealth(health);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
