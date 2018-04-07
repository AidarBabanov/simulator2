import entities.cards.Hero;
import entities.collections.Deck;
import entities.collections.Hand;
import entities.collections.Table;
import filesystem.FileSystem;
import player.Player;
import player.PrettyConsolePlayer;
import states.PreGame;
import states.State;
import states.StateManager;

public class Main {

    public static void main(String args[]) {



        StateManager stateManager = null;
        State state = null;
        Player player1 = null;
        Player player2 = null;
        stateManager = StateManager.getInstance();

        player1 = new PrettyConsolePlayer();
        player1.setName("Cool guy [Aidar]");
        player1.setHand(new Hand(player1));
//        Deck deck1 = getDeckFromFile2(player1);
        Deck deck1 = FileSystem.getDeckByName(player1, "custom_deck");
        deck1.shuffleDeck();
        player1.setDeck(deck1);
        player1.setTable(new Table(player1));
        player1.setStateManager(stateManager);
        Hero hero1 = FileSystem.getHeroByDeckNameName(player1, "custom_deck");
        hero1.setBaseHealth(1);
        hero1.setCurrentHealth(1);
        player1.setHero(hero1);

        player2 = new PrettyConsolePlayer();
        player2.setName("Rocking teacher [Ben]");
        player2.setHand(new Hand(player2));
//        Deck deck2 = getDeckFromFile2(player2);
        Deck deck2 = FileSystem.getDeckByName(player2, "custom_deck");
        deck2.shuffleDeck();
        player2.setDeck(deck2);
        player2.setTable(new Table(player2));
        player2.setStateManager(stateManager);
        Hero hero2 = FileSystem.getHeroByDeckNameName(player2, "custom_deck");
        hero2.setBaseHealth(1);
        hero2.setCurrentHealth(1);
        player2.setHero(hero2);

        player1.setOpponent(player2);
        player2.setOpponent(player1);


        stateManager.setPlayer1(player1);
        stateManager.setPlayer2(player2);


        stateManager.setPlayer1(player1);
        stateManager.setPlayer2(player2);
        state = new PreGame(stateManager);
        stateManager.setState(state);

//        System.out.println(player1.toString());
//        System.out.println(player2.toString());
//        System.out.println(player1.getDeck().toString());
        while (!stateManager.gameEnded()) {
            stateManager.performActions();
            stateManager.changeState();
        }
    }

//    private static void showAllFiles(File directory) {
//        for (File file : directory.listFiles()) {
//            System.out.println(file.getName());
//        }
//        System.out.println();
//    }

//    public static Deck getDeckFromFile2(Player player) {
//        Deck deck = new Deck(player);
//        File file = new File("resources\\cards\\classic\\neutral\\minion_argent_squire.json");
//        for (int i = 0; i < 30; i++) {
//            CardB card = copyCardFromFile(player, file);
//            deck.add(card);
//        }
//        return deck;
//    }
//
//    public static Deck getDeckFromFile(Player player) {
//        Deck deck = new Deck(player);
////        File testFile = new File("resources\\decks");
////        showAllFiles(testFile);
//        File file = new File("resources\\decks\\custom_deck.json");
////        System.out.println(file.length());
//        String fileContent = readFileAsString(file.getAbsolutePath());
//
//        JsonObject jsonObject = fromStringToJsonOBJ(fileContent);
//        JsonArray jsonArray = jsonObject.get("cards").getAsJsonArray();
//        for (JsonElement jsonElement : jsonArray) {
//            String cardName = jsonElement.getAsString();
//            CardB card = getCardFromFile(cardName);
//            deck.add(card);
//        }
//        return deck;
//    }
//
//    private static CardB getCardFromFile(String cardName) {
//        CardB card = null;
////        System.out.println(cardName + ".json SUKAAAAAAAAAA");
//        File cardsDirectory = new File("resources\\cards");
////        showAllFiles(cardsDirectory);
//        for (File expansionDirectory : cardsDirectory.listFiles()) {
////            showAllFiles(expansionDirectory);
//            for (File classDirectory : expansionDirectory.listFiles()) {
////                showAllFiles(classDirectory);
//                for (File aCard : classDirectory.listFiles()) {
//                    System.out.println(aCard.getName());
//                    if (aCard.getName().equals(cardName + ".json")) {
//                        card = copyCardFromFile(aCard);
//                        return card;
//                    }
//                }
//            }
//        }
//        return card;
//    }

//    private static CardB copyCardFromFile(Player player, File file) {
//        CardB card = null;                                  //don't forget to make more card types
//        String fileStr = readFileAsString(file.getAbsolutePath());
//        JsonObject jsonObject = fromStringToJsonOBJ(fileStr);
//        if (jsonObject.get("type").getAsString().equals("MINION")) {
//            int baseManacost = jsonObject.get("baseManaCost").getAsInt();
//            int baseAttack = jsonObject.get("baseAttack").getAsInt();
//            String name = jsonObject.get("name").getAsString();
//            String description = jsonObject.get("description").getAsString();
//            int baseHealth = jsonObject.get("baseHp").getAsInt();
//            MinionB minion;
//            minion = new MinionB();
//            minion.setPlayer(player);
//            minion.setName(name);
//            minion.setBaseManacost(baseManacost);
//            minion.setCurrentManacost(baseManacost);
//            minion.setDescription(description);
//            minion.setBaseAttack(baseAttack);
//            minion.setBaseHealth(baseHealth);
//            minion.setCurrentHealth(baseHealth);
//            minion.setCurrentAttack(baseAttack);
//            card = minion;
//        }
//        return card;
//    }
//
//    private static String readFileAsString(String fileName) {
//        String text = "";
////        System.out.println(fileName);
//        try {
//            text = new String(Files.readAllBytes(Paths.get(fileName)));
////            System.out.println(text);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return text;
//    }
//
//    private static JsonObject fromStringToJsonOBJ(String message) {
//        return new JsonParser().parse(message).getAsJsonObject();
//    }
}
