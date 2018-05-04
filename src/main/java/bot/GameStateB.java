package bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
        int manaCrystals = jsonState.get("currentManaCrystals").getAsInt();
        JsonObject jsonMyHero = jsonState.get("hero").getAsJsonObject();
        JsonObject jsonOpponentHero = jsonState.get("opponentHero").getAsJsonObject();
        JsonArray jsonMyTable = jsonState.get("table").getAsJsonObject().get("cards").getAsJsonArray();
        JsonArray jsonOpponentTable = jsonState.get("opponentTable").getAsJsonObject().get("cards").getAsJsonArray();
        JsonArray jsonMyHand = jsonState.get("hand").getAsJsonObject().get("cards").getAsJsonArray();

        this.setMe(new HeroB(jsonMyHero));
        this.setOpponent(new HeroB(jsonOpponentHero));
        this.setMyTable(createNewListFromJson(jsonMyTable, MinionB.class));
        this.setOpponentTable(createNewListFromJson(jsonOpponentTable, MinionB.class));
        this.setMyHand(createNewListFromJson(jsonMyHand, CardB.class));
        this.setManaCrystals(manaCrystals);
    }

    public <T extends CardB> List<T> createNewListFromJson(JsonArray jsonArray, Class<T> type) {
        List<T> result = new LinkedList<>();
        for (JsonElement jsonCard : jsonArray) {
            CardB cardB = CardB.createCardFromJson(jsonCard.getAsJsonObject());
            result.add((T) cardB);
        }
        return result;
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
