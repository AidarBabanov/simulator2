package filesystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.cards.Card;
import entities.cards.Hero;
import entities.cards.HeroPower;
import entities.collections.Deck;
import player.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileSystem {

    public static Hero getHeroByDeckNameName(Player player, String deckName){
        Hero hero;
        String path = "resources/decks/" + deckName + ".json";
        JsonObject jsonObject = readFile(new File(path));
        String heroClass = jsonObject.get("heroClass").getAsString();
        String cardPath = null;
        if(heroClass.equals("MAGE")){
             cardPath = findCardPath("hero_jaina.json");
        }
        JsonObject jsonCard = readFile(new File(cardPath));
        hero = new Hero(player, jsonCard);
        return hero;
    }

    public static HeroPower getHeroPowerByName(Player player, String heroPowerStr){
        HeroPower heroPower = null;
        String cardPath = findCardPath(heroPowerStr+".json");
        JsonObject jsonHeroPower = readFile(new File(cardPath));
        heroPower = new HeroPower(player, jsonHeroPower);
        return heroPower;
    }

    public static Deck getDeckByName(Player player, String deckName) {
        String path = "resources/decks/" + deckName + ".json";
        JsonObject jsonObject = readFile(new File(path));
        return getDeckFromJson(player, jsonObject);
    }

    public static Deck getDeckFromJson(Player player, JsonObject jsonDeck) {
        Deck deck = new Deck(player);
        JsonArray jsonArray = jsonDeck.getAsJsonArray("cards");
        for (JsonElement card : jsonArray) {
            String cardPath = findCardPath(card.getAsString() + ".json");
            assert cardPath != null;
            JsonObject jsonCard = readFile(new File(cardPath));
            deck.add(Card.createCardFromJson(player, jsonCard));
        }
        return deck;
    }

    public static String findCardPath(String cardName) {
        String mainPathStr = "resources/cards/";
        File mainPath = new File(mainPathStr);
        for (File expansion : Objects.requireNonNull(mainPath.listFiles())) {
//            System.out.println(expansion.getName());
            for (File heroClass : Objects.requireNonNull(expansion.listFiles())) {
                for (File card : Objects.requireNonNull(heroClass.listFiles())) {
                    if (card.getName().equals(cardName)) return card.getAbsolutePath();
                }
            }
        }
        return null;
    }


    public static JsonObject readFile(File file) {
        return fromStringToJsonOBJ(readFileAsString(file.getAbsolutePath()));
    }

    public static String readFileAsString(String fileName) {
        String text = "";
//        System.out.println(fileName);
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static JsonObject fromStringToJsonOBJ(String str) {
        return new JsonParser().parse(str).getAsJsonObject();
    }
}
