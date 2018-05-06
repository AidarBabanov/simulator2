package bot.commands;

import com.google.gson.JsonObject;

public class AttackCommand implements Command {

    private TargetSource target;
    private TargetSource source;
    private int sourcePosition;
    private int targetPosition;

    AttackCommand(TargetSource source, int sourcePosition, TargetSource target, int targetPosition) {
        this.setSource(source);
        this.setTarget(target);
        this.setSourcePosition(sourcePosition);
        this.setTargetPosition(targetPosition);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "attack");
        jsonObject.addProperty("source", source.toString().toLowerCase());
        if (source == TargetSource.MINION) {
            jsonObject.addProperty("sourcePosition", this.getSourcePosition());
        }
        jsonObject.addProperty("target", target.toString().toLowerCase());
        if (target == TargetSource.MINION) {
            jsonObject.addProperty("targetPosition", this.getTargetPosition());
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

    public TargetSource getSource() {
        return source;
    }

    public void setSource(TargetSource source) {
        this.source = source;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(int sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
    }

    enum TargetSource {
        HERO, MINION
    }
}
