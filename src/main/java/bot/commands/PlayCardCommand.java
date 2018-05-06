package bot.commands;

import com.google.gson.JsonObject;

public class PlayCardCommand implements Command {

    private int handPosition;

    public PlayCardCommand(int handPosition) {
        this.setHandPosition(handPosition);
    }
    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "playCard");
        jsonObject.addProperty("handPosition", this.getHandPosition());
        return jsonObject;
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }

    public int getHandPosition() {
        return handPosition;
    }

    public void setHandPosition(int handPosition) {
        this.handPosition = handPosition;
    }
}
