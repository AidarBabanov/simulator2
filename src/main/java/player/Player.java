package player;

import com.google.gson.*;
import entities.cards.Card;
import entities.cards.Hero;
import entities.cards.Minion;
import entities.collections.Deck;
import entities.collections.Hand;
import entities.collections.Table;
import states.StateManager;
import targetting.Target;

import java.util.List;

public abstract class Player {

    private Card pendingCard;
    private String name;
    private Deck deck;
    private Hand hand;
    private Hero hero;
    private Table table;
    private Player opponent;
    private StateManager stateManager;
    private int manaCrystals;
    private int currentManaCrystals;


    public Player() {
    }

    public Player(String name, StateManager stateManager, Deck deck, Hand hand, Hero hero, Table table, Player opponent) {
        this.setName(name);
        this.setDeck(deck);
        this.setHand(hand);
        this.setHero(hero);
        this.setTable(table);
        this.setOpponent(opponent);
        this.setStateManager(stateManager);
        this.setManaCrystals(0);
        this.setCurrentManaCrystals(0);
    }

    public void playCard(int cardPositionInHand) {
        Card card = this.getHand().remove(cardPositionInHand);
        if (card.getCurrentManacost() > this.getCurrentManaCrystals()) this.write("{\"message\": \"not enough mana\"}");
        else {
            this.setPendingCard(card);
            card.playCard();
            this.setPendingCard(null);
        }
    }

    public void drawCard() {
        hand.drawCard();
    }

    public Card changeCard(Card card) {
        Card newCard;
        if (deck.size() > 0) {
            newCard = deck.drawCard();
            deck.shuffleCard(card);
        } else newCard = card;
        return newCard;
    }

    public void changeCardsOnStart(List<Integer> chosenCards) {
        for (Integer chosenCard : chosenCards) {
            Card changingCard = hand.get(chosenCard);
            Card newCard = changeCard(changingCard);
//            System.out.println(chosenCards.get(i)+" - "+ newCard);
            hand.set(chosenCard, newCard);
        }
    }

    public void useHeroPower() {
        if (this.getManaCrystals() < this.getHero().getHeroPower().getCurrentManacost()) {
            this.write("\"message\", \"not enough mana\"");
        } else {
            this.getHero().getHeroPower().use();
        }
    }

    public void attackWithHero(Target target) {

    }

    public void attackWithMinion(int positionOnTable, Target target) {
        Minion minion = this.getTable().get(positionOnTable);
        minion.attack(target);
    }

    public abstract String read();

    public abstract void write(String message);

    public JsonObject jsonStateInformation() {
        JsonObject jsonPlayer = new JsonObject();
        jsonPlayer.addProperty("message","stateInfo");
        jsonPlayer.addProperty("name", this.getName());
        jsonPlayer.addProperty("manaCrystals", this.getManaCrystals());
        jsonPlayer.addProperty("currentManaCrystals", this.getCurrentManaCrystals());
        jsonPlayer.add("hero", this.getHero().jsonStateInformation());
        jsonPlayer.add("deck", this.getDeck().jsonStateInformation());
        jsonPlayer.add("hand", this.getHand().jsonStateInformation());
        jsonPlayer.add("table", this.getTable().jsonStateInformation());

        jsonPlayer.addProperty("opponentName", this.getOpponent().getName());
        jsonPlayer.addProperty("opponentManaCrystals", this.getOpponent().getManaCrystals());
        jsonPlayer.addProperty("opponentCurrentManaCrystals", this.getOpponent().getCurrentManaCrystals());
        jsonPlayer.add("opponentHero", this.getOpponent().getHero().jsonStateInformation());
        jsonPlayer.addProperty("opponentHand", this.getOpponent().getHand().size());
        jsonPlayer.addProperty("opponentDeck", this.getOpponent().getDeck().size());
        jsonPlayer.add("opponentTable", this.getOpponent().getTable().jsonStateInformation());

        return jsonPlayer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonStateInformation().toString());
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public int getManaCrystals() {
        return manaCrystals;
    }

    public void setManaCrystals(int manaCrystals) {
        this.manaCrystals = manaCrystals;
    }

    public int getCurrentManaCrystals() {
        return currentManaCrystals;
    }

    public void setCurrentManaCrystals(int currentManaCrystals) {
        this.currentManaCrystals = currentManaCrystals;
    }

    public Card getPendingCard() {
        return pendingCard;
    }

    public void setPendingCard(Card pendingCard) {
        this.pendingCard = pendingCard;
    }

    public interface MessageHandler {
        boolean handle(String message, Player player);
    }

    public enum Command {

    }
}
