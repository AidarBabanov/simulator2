package bot.commands;

import com.google.gson.JsonObject;

public class TargetCommand implements Command {

    private TargetSource target;
    private int position;
    private Owner owner;
    private String name;

    public TargetCommand(String name, TargetSource target, Owner owner, int position) {
        this.setTarget(target);
        this.setOwner(owner);
        this.setPosition(position);
        this.setName(name);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.getName());
        jsonObject.addProperty("action", "target");
        jsonObject.addProperty("targetType", target.toString().toLowerCase());
        jsonObject.addProperty("owner", owner.toString().toLowerCase());
        if (target == TargetSource.MINION) {
            jsonObject.addProperty("tablePosition", position);
        }

        return jsonObject;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public TargetSource getTarget() {
        return target;
    }

    public void setTarget(TargetSource target) {
        this.target = target;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum TargetSource {
        HERO, MINION
    }

    public enum Owner {
        ME, OPPONENT
    }
}
