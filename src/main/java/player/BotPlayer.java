package player;

import bot.SimpleBot;
import bot.commands.EndCommand;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BotPlayer extends Player {

    private SimpleBot bot;
    boolean preGame;

    public BotPlayer() {
        bot = new SimpleBot("BOT");
        super.setName("BOT");
    }

    @Override
    public String read() {
        if (preGame) {
            preGame = false;
            return new EndCommand(bot.getName()).toString();
        } else {
            return bot.getNextCommand();
        }
    }

    @Override
    public void write(String message) {
        JsonObject jsonObject = fromStringToJsonOBJ(message);
        if (jsonObject.has("state")) {
            String turn = jsonObject.get("state").getAsString();
            if (turn.equals("preGame")) {
                preGame = true;
            }
            else if (turn.equals("playerTurn") && jsonObject.get("player").getAsString().equals(bot.getName())) {
                bot.setNextMessageUpdateState(true);
            }
        } else if (jsonObject.has("message")) {
            if (jsonObject.get("message").getAsString().equals("stateInfo") && jsonObject.get("name").getAsString().equals(bot.getName()) && bot.isNextMessageUpdateState()) {
                bot.updateState(jsonObject);
                bot.think();
                bot.setNextMessageUpdateState(false);
            }
        }
    }

    private JsonObject fromStringToJsonOBJ(String message) {
        return new JsonParser().parse(message).getAsJsonObject();
    }
}
