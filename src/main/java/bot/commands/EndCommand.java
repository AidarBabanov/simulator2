package bot.commands;

import com.google.gson.JsonObject;

public class EndCommand implements Command {
    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "end");
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
