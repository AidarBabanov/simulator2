package bot;

import bot.spells.SpellEffectB;
import com.google.gson.JsonObject;

import java.util.List;

public class SimpleBot {

    private GameStateB gameStateB;

//    SimpleBot() {
//        gameStateB = new GameStateB();
//    }

    public void updateState(JsonObject jsonState){

    }

    public int calculateEnemyTotalDamage() {
        int totalDamage = 0;
        List<MinionB> opponentTable = gameStateB.getOpponentTable();
        for (MinionB minion : opponentTable) {
            totalDamage += minion.getAttack();
        }
        //+1 damage for mage hero power
        totalDamage += 1;
        return totalDamage;
    }

    public int calculateMyTotalDamage() {
        int totalDamage = 0;
        List<MinionB> myTable = gameStateB.getMyTable();
        List<CardB> myHand = gameStateB.getMyHand();
        for (MinionB minion : myTable) {
            totalDamage += minion.getAttack();
        }

        for (CardB cardB : myHand) {
            if (cardB instanceof SpellB) {
                SpellB spellCard = (SpellB) cardB;
                if (spellCard.hitsEnemyHero()) totalDamage += spellCard.getDamage();
            }
        }

        return totalDamage;
    }

    public int calculateEnemyStats() {
        int totalStats = 0;
        List<MinionB> opponentTable = gameStateB.getOpponentTable();
        for (MinionB minion : opponentTable) {
            totalStats += minion.getStats();
        }
        return totalStats;
    }

    public int calculateMyStats() {
        int totalStats = 0;
        List<MinionB> myTable = gameStateB.getMyTable();
        for (MinionB minion : myTable) {
            totalStats += minion.getStats();
        }
        return totalStats;
    }


    public GameStateB getGameStateB() {
        return gameStateB;
    }

    public void setGameStateB(GameStateB gameStateB) {
        this.gameStateB = gameStateB;
    }

    public int calculateMyAOEDamage(SpellEffectB spellEffectB) {
        int totalDamage = 0;
        List<MinionB> opponentTable = gameStateB.getOpponentTable();
        for (MinionB minion : opponentTable) {
            totalDamage += Math.min(spellEffectB.getDamage(), minion.getHealth());
        }
        return totalDamage;
    }
}
