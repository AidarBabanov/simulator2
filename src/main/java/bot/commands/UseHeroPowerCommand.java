package bot.commands;

import com.google.gson.JsonObject;

public class UseHeroPowerCommand implements Command {
    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "useHeroPower");
        return jsonObject;
    }
}
