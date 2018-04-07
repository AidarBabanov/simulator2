package bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class GameStateB {

    private int manaCrystals;

    private HeroB me;
    private HeroB opponent;

    private List<MinionB> myTable;
    private List<MinionB> opponentTable;
    private List<CardB> myHand;

    public GameStateB(JsonObject jsonState) {
        String manaCrystals = jsonState.get("currentManaCrystals").getAsString();
        JsonObject jsonMyHero = jsonState.get("hero").getAsJsonObject();
        JsonObject jsonOpponentHero = jsonState.get("opponentHero").getAsJsonObject();
        JsonArray jsonMyTable = jsonState.get("table").getAsJsonObject().get("cards").getAsJsonArray();
        JsonArray jsonOpponentTable = jsonState.get("opponentTable").getAsJsonObject().get("cards").getAsJsonArray();
        JsonArray jsonMyHand = jsonState.get("hand").getAsJsonObject().get("cards").getAsJsonArray();


        this.setMe(new HeroB());
        this.setOpponent(new HeroB());
        this.setMyTable(new LinkedList<>());
        this.setOpponentTable(new LinkedList<>());
        this.setMyHand(new LinkedList<>());
    }

    public HeroB getMe() {
        return me;
    }

    public void setMe(HeroB me) {
        this.me = me;
    }

    public HeroB getOpponent() {
        return opponent;
    }

    public void setOpponent(HeroB opponent) {
        this.opponent = opponent;
    }

    public List<MinionB> getMyTable() {
        return myTable;
    }

    public void setMyTable(List<MinionB> myTable) {
        this.myTable = myTable;
    }

    public List<MinionB> getOpponentTable() {
        return opponentTable;
    }

    public void setOpponentTable(List<MinionB> opponentTable) {
        this.opponentTable = opponentTable;
    }

    public List<CardB> getMyHand() {
        return myHand;
    }

    public void setMyHand(List<CardB> myHand) {
        this.myHand = myHand;
    }

    public int getManaCrystals() {
        return manaCrystals;
    }

    public void setManaCrystals(int manaCrystals) {
        this.manaCrystals = manaCrystals;
    }
}
