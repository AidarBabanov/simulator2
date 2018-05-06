package bot.commands;

import com.google.gson.JsonObject;

public class TargetCommand implements Command {

    private TargetSource target;
    private int position;
    private Owner owner;

    TargetCommand(TargetSource target, Owner owner, int position){
        this.setTarget(target);
        this.setOwner(owner);
        this.setPosition(position);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "target");
        jsonObject.addProperty("targetType", target.toString().toLowerCase());
        jsonObject.addProperty("owner", owner.toString().toLowerCase());
        if(target==TargetSource.MINION){
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

    enum TargetSource {
        HERO, MINION
    }

    enum Owner{
        ME, OPPONENT
    }
}
