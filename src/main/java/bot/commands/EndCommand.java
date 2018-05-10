package bot.commands;

import com.google.gson.JsonObject;

public class EndCommand implements Command {

    private String name;

    public EndCommand(String name) {
        this.setName(name);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.getName());
        jsonObject.addProperty("action", "end");
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
