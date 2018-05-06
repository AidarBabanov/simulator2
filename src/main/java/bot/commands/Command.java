package bot.commands;

import com.google.gson.JsonObject;

public interface Command {
    JsonObject toJson();
}
