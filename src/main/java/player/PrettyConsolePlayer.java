package player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrettyConsolePlayer extends Player {
    private BufferedReader in;

    public PrettyConsolePlayer(){
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String read() {

        String message = "";
        try {
            message = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arrMessage = message.split(" ");
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", super.getName());
//        System.out.println(arrMessage[0]);
        String action = "";
        switch (arrMessage[0]) {
            case "end":
                action = "end";
                jsonObject.addProperty("action", action);
                break;
            case "change":
                action = "change";
                JsonArray changingCards = new JsonArray();
                for (int i = 1; i < arrMessage.length; i++) {
                    changingCards.add(Integer.parseInt(arrMessage[i]));
                    jsonObject.add("cardsPositions", changingCards);
                }
                jsonObject.addProperty("action", action);
                break;

            case "play":
                action = "playCard";
                jsonObject.addProperty("handPosition", Integer.parseInt(arrMessage[1]));
//                jsonObject.addProperty("tablePosition", Integer.parseInt(arrMessage[2]));
//                jsonObject.addProperty("target", arrMessage[3]);
//                if (arrMessage.equals("minion")) {
//                    jsonObject.addProperty("targetPosition", Integer.parseInt(arrMessage[4]));
//                }
                jsonObject.addProperty("action", action);
                break;

            case "attack":
                int i = 1;
                action = "attack";
                jsonObject.addProperty("source", arrMessage[i]);
                if (arrMessage[i++].equals("minion")) {
                    jsonObject.addProperty("sourcePosition", Integer.parseInt(arrMessage[i++]));
                }

                jsonObject.addProperty("targetType", arrMessage[i]);
                if (arrMessage[i++].equals("minion")) {
                    jsonObject.addProperty("targetPosition", Integer.parseInt(arrMessage[i++]));
                }
                jsonObject.addProperty("action", action);
                break;
            case "position":
                jsonObject.addProperty("tablePosition", arrMessage[1]);
                break;
            case "target":
                jsonObject.addProperty("targetType", arrMessage[1]);
                jsonObject.addProperty("owner", arrMessage[2]);
                if (arrMessage[1].equals("minion")) {
                    jsonObject.addProperty("tablePosition", arrMessage[3]);
                }
                break;
            case "useHeroPower":
                jsonObject.addProperty("action", "useHeroPower");
        }

//        System.out.println(jsonObject.toString());

        return jsonObject.toString();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    private JsonObject fromStringToJsonOBJ(String message) {
        return new JsonParser().parse(message).getAsJsonObject();
    }
}
