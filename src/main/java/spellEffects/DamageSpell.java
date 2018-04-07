package spellEffects;

import com.google.gson.JsonObject;
import targetting.Target;
import player.Player;
import targetting.TargetType;

import java.util.LinkedList;
import java.util.List;

public class DamageSpell extends SpellEffect {

    private Player player;
    private int damage;
    private List<Target> targetList;
    private TargetType targetType;

    public DamageSpell() {
        targetList = new LinkedList<>();
    }

    public DamageSpell(JsonObject jsonEffect) {
        targetList = new LinkedList<>();
        this.setDamage(jsonEffect.get("value").getAsInt());
        this.setTargetType(TargetType.defineTarget(jsonEffect));
    }

    @Override
    public void getArguments(Player player) {
        targetList = targetType.getTargets(player);
    }

    @Override
    public void forceTargets(List<Target> targets) {
        this.setTargetList(targets);
    }

    @Override
    public void performAction() {
        for (Target target : targetList) {
            target.getDamage(damage);
        }

    }

    @Override
    public JsonObject jsonStateInformation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", "DamageSpell");
        jsonObject.addProperty("value", this.damage);
        jsonObject.addProperty("targetType", targetType.toString());
        return jsonObject;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTargetList(List<Target> targetList) {
        this.targetList = targetList;
    }

    public List<Target> getTargetList() {
        return this.targetList;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }


}
