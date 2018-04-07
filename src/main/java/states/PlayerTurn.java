package states;

import entities.cards.Minion;
import targetting.Target;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import player.Player;

public class PlayerTurn implements State {

    private Player player;
    private boolean turnEnd;
    private PlayerTurnMessageHandler messageHandler;

    public PlayerTurn(Player player) {
            this.setPlayer(player);
            this.setTurnEnd(false);
        messageHandler = new PlayerTurnMessageHandler();
    }

    public void performActions() {
        player.write(state());
        player.getOpponent().write(state());

        player.setManaCrystals(player.getManaCrystals()+1);
        player.setCurrentManaCrystals(player.getManaCrystals());

        player.drawCard();


        for (Minion tableMinion : player.getTable()) {
            tableMinion.setCanAttack(true);
        }

        player.write(player.toString());
        player.getOpponent().write(player.getOpponent().toString());

        while (!this.isTurnEnd()) {
            String message = player.read();
            boolean handled = messageHandler.handle(message, player);
            if (!handled) player.write("{\"message\": \"Wrong command!\"}");
            else {
                player.write(player.toString());
                player.getOpponent().write(player.getOpponent().toString());
            }
        }
    }

    private String state() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", "playerTurn");
        jsonObject.addProperty("player", player.getName());
        return jsonObject.toString();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isTurnEnd() {
        return turnEnd;
    }

    public void setTurnEnd(boolean turnEnd) {
        this.turnEnd = turnEnd;
    }

    private class PlayerTurnMessageHandler implements Player.MessageHandler {
        public boolean handle(String message, Player player) {
            JsonObject jsonMessage = fromStringToJsonOBJ(message);
            if (!jsonMessage.get("name").getAsString().equals(player.getName())) {
                return false;}
            else {
                if (jsonMessage.get("action").getAsString().equals("playCard")) {
                    int handPosition = jsonMessage.get("handPosition").getAsInt();
                    if(handPosition>=player.getHand().size())player.write("{\"Message\": \"No card on this position\"}");
                    else player.playCard(handPosition);
                    return true;
                }

                if (jsonMessage.get("action").getAsString().equals("attack")) {
                    String source = jsonMessage.get("source").getAsString();
                    String strTarget = jsonMessage.get("target").getAsString();
                    Target target = null;
                    int targetPosition;
                    if (strTarget.equals("hero")) target = player.getOpponent().getHero();
                    else if (strTarget.equals("minion")) {
                        targetPosition = jsonMessage.get("targetPosition").getAsInt();
                        target = player.getOpponent().getTable().get(targetPosition);
                    }

                    if (source.equals("minion")) {
                        int sourcePosition = jsonMessage.get("sourcePosition").getAsInt();
                        player.attackWithMinion(sourcePosition, target);

                    } else if (source.equals("hero")) {
                        player.attackWithHero(target);

                    }

                    if (player.getHero().getCurrentHealth() <= 0) setTurnEnd(true);
                    if (player.getOpponent().getHero().getCurrentHealth() <= 0) setTurnEnd(true);
                    return true;
                }

                if (jsonMessage.get("action").getAsString().equals("end")) {
                    setTurnEnd(true);
                    return true;
                }
                if(jsonMessage.get("action").getAsString().equals("useHeroPower")){
                    player.useHeroPower();
                    return true;
                }
            }
            return false;
        }


    }

    private JsonObject fromStringToJsonOBJ(String message) {
        return new JsonParser().parse(message).getAsJsonObject();
    }
}
