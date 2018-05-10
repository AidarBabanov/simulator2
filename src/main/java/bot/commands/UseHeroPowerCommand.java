package bot.commands;

import com.google.gson.JsonObject;

public class UseHeroPowerCommand implements Command {

    private String name;

    public UseHeroPowerCommand(String name) {
        this.setName(name);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.getName());
        jsonObject.addProperty("action", "useHeroPower");
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
