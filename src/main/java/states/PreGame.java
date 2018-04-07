package states;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import player.Player;

import java.util.LinkedList;
import java.util.List;

public class PreGame implements State {
    private StateManager stateManager;
    private Player player1;
    private Player player2;

    private PreGameMessageHandler messageHandler;

    public PreGame(StateManager stateManager) {
        this.stateManager = stateManager;
        this.player1 = stateManager.getPlayer1();
        this.player2 = stateManager.getPlayer2();
        messageHandler = new PreGameMessageHandler();
    }

    public void performActions() {
        player1.write("{\"state\": \"preGame\"}");
        player2.write("{\"state\": \"preGame\"}");

        player1.drawCard();
        player1.drawCard();
        player1.drawCard();

        player2.drawCard();
        player2.drawCard();
        player2.drawCard();
        player2.drawCard();

        player1.setManaCrystals(0);
        player1.setCurrentManaCrystals(0);

        player2.setManaCrystals(0);
        player2.setCurrentManaCrystals(0);

        player1.write(player1.toString());
        player2.write(player2.toString());

        String message;
        boolean handled;

        do {
            message = player1.read();
            handled = messageHandler.handle(message, player1);
            if (!handled) player1.write("\"message\": \"Wrong command!|\"");
        } while (!handled);

        player1.write(player1.toString());
        player2.write(player2.toString());

        do {
            message = player2.read();
            handled = messageHandler.handle(message, player2);
            if (!handled) player1.write("{\"message\": \"Wrong command!\"}");
        } while (!handled);

        player1.write(player1.toString());
        player2.write(player2.toString());
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    private class PreGameMessageHandler implements Player.MessageHandler {

        public boolean handle(String message, Player player) {
            JsonObject jsonMessage = fromStringToJsonOBJ(message);
            if (!jsonMessage.get("name").getAsString().equals(player.getName())) return false;
            else {
                if (jsonMessage.get("action").getAsString().equals("change")) {
                    JsonArray jsonArray = jsonMessage.get("cardsPositions").getAsJsonArray();
                    List<Integer> changingCards = new LinkedList<Integer>();
                    for (JsonElement jsonElement : jsonArray) {
                        changingCards.add(jsonElement.getAsInt());
                    }
                    player.changeCardsOnStart(changingCards);
                    return true;
                } else return jsonMessage.get("action").getAsString().equals("end");
            }
        }

        private JsonObject fromStringToJsonOBJ(String message) {
            return new JsonParser().parse(message).getAsJsonObject();
        }
    }
}