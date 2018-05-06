package bot;

import bot.optimalTrading.Table;
import bot.spells.DamageSpellB;
import bot.spells.SpellEffectB;
import com.google.gson.JsonObject;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SimpleBot {

    private GameStateB gameStateB;
    private MaxSpellDamage maxSpellDamage;

    SimpleBot(JsonObject jsonState) {
        updateState(jsonState);
    }

    public void updateState(JsonObject jsonState) {
        this.setGameStateB(new GameStateB(jsonState));
        maxSpellDamage = new MaxSpellDamage();
    }

    public void think() {
        int myTotalDamage = calculateMyTotalDamage();
//        int enemyTotalDamage = calculateEnemyTotalDamage();
        if (myTotalDamage >= gameStateB.getOpponent().getHealth()) attackEnemyHero();
//        else if (enemyTotalDamage >= gameStateB.getMe().getHealth()) reduceEnemyDamageAsPossible();
        else makeTempoMoves();
    }

    private void makeTempoMoves() {

        //start using an aoe spell
        CardB effectiveSpell = null;
        int maxDamage = 0;
        int effectiveManaCost = 0;
        for (CardB card : gameStateB.getMyHand()) {
            if (card instanceof SpellB && ((SpellB) card).getSpellEffect() instanceof DamageSpellB && card.getManaCost() <= gameStateB.getManaCrystals()) {
                DamageSpellB spellEffectB = (DamageSpellB) ((SpellB) card).getSpellEffect();
                int calculatedMaxDamage = calculateMyAOEDamage(spellEffectB);
                if (spellEffectB.getTargetTypeB().isAOE() && maxDamage < calculatedMaxDamage) {
                    maxDamage = calculatedMaxDamage;
                    effectiveSpell = card;
                    effectiveManaCost = card.getManaCost();
                } else if (spellEffectB.getTargetTypeB().isAOE() && maxDamage == calculatedMaxDamage && card.getManaCost() < effectiveManaCost) {
                    effectiveSpell = card;
                    effectiveManaCost = card.getManaCost();
                }
            }
        }
        //end using an aoe spell

        // start of sort for trading
        List<MinionB> listOfEnemyMinions = new LinkedList<>(gameStateB.getOpponentTable());
        listOfEnemyMinions.sort(new Comparator<MinionB>() {
            @Override
            public int compare(MinionB o1, MinionB o2) {
                if (o1.getAttack() == o2.getAttack()) return o1.getHealth() - o2.getHealth();
                else return o2.getAttack() - o1.getAttack();
            }
        });
        gameStateB.setManaCrystals(gameStateB.getManaCrystals() - effectiveManaCost);
        // end of sort for trading

        //start trading
        for (MinionB minion : listOfEnemyMinions) {
            Table table = new Table(gameStateB, minion);
            List<CardB> effectiveList = table.getEffectiveList();
            for (CardB card : effectiveList) {
                if (card instanceof MinionB && ((MinionB) card).getHealth() <= minion.getAttack()) {
                    gameStateB.getMyTable().remove(card);
                }
                if (card instanceof SpellB) {
                    gameStateB.getMyHand().remove(card);
                    gameStateB.setManaCrystals(gameStateB.getManaCrystals() - card.getManaCost());
                }
            }
        }
        //end of trading

        //start playing cards
        List<MinionB> minionsToPlay = new LinkedList<>();
        for (CardB card : gameStateB.getMyHand()) {
            if (card instanceof MinionB) {
                minionsToPlay.add((MinionB) card);
            }
        }
        //end playing cards

//        playMinions(new LinkedList<MinionB>(), minionsToPlay, 0, 0, 0);

        //start minions to play
        minionsToPlay.sort(new Comparator<MinionB>() {
            @Override
            public int compare(MinionB o1, MinionB o2) {
                return o2.getStats() - o1.getStats();
            }
        });

        List<MinionB> minionsToPlayNew = new LinkedList<>();

        for (MinionB minion : minionsToPlay) {
            if (gameStateB.getMyTable().size() >= 7) break;
            if (minion.getManaCost() <= gameStateB.getManaCrystals()) {
                minionsToPlayNew.add(minion);
                gameStateB.setManaCrystals(gameStateB.getManaCrystals() - minion.getManaCost());
            }
        }
        minionsToPlay = minionsToPlayNew;
        //end minions to play
    }

    private void attackEnemyHero() {
    }

//    private void playMinions(List<MinionB> minionsToPlay, List<MinionB> maxMinionsToPlay, int currentStats, int maxStats, int handIndex) {
//        if (handIndex >= gameStateB.getMyHand().size()) {
//            if (currentStats > maxStats) maxMinionsToPlay = new LinkedList<>(minionsToPlay);
//        } else {
//            playMinions(minionsToPlay, maxMinionsToPlay, currentStats, maxStats, handIndex + 1);
//            if (gameStateB.getMyHand().get(handIndex) instanceof MinionB) {
//                minionsToPlay.add((MinionB) gameStateB.getMyHand().get(handIndex));
//                playMinions(minionsToPlay, maxMinionsToPlay, currentStats, maxStats, handIndex + 1);
//                minionsToPlay.remove(minionsToPlay.size() - 1);
//            }
//        }
//    }

//    private void reduceEnemyDamageAsPossible() {
//    }

//    private int calculateEnemyTotalDamage() {
//        int totalDamage = 0;
//        List<MinionB> opponentTable = gameStateB.getOpponentTable();
//        for (MinionB minion : opponentTable) {
//            totalDamage += minion.getAttack();
//        }
//        //+1 damage for mage hero power
//        totalDamage += 1;
//        return totalDamage;
//    }

    private int calculateMyTotalDamage() {
        int totalDamage = 0;
        List<MinionB> myTable = gameStateB.getMyTable();
        for (MinionB minion : myTable) {
            totalDamage += minion.getAttack();
        }
        totalDamage += maxSpellDamage.totalDamage;

        return totalDamage;
    }


    private int calculateEnemyStats() {
        int totalStats = 0;
        List<MinionB> opponentTable = gameStateB.getOpponentTable();
        for (MinionB minion : opponentTable) {
            totalStats += minion.getStats();
        }
        return totalStats;
    }

    private int calculateMyStats() {
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

    //class for calculation of max damage for enemy hero
    class MaxSpellDamage {
        List<DirectDamageCard> cardBs;
        int totalDamage;
        boolean usedHeroPower;

        MaxSpellDamage() {
            totalDamage = 0;
            usedHeroPower = false;
            cardBs = new LinkedList<>();
            calculateMaxSpellDamage();
        }

        private void calculateMaxSpellDamage() {
            int index = 0;
            int manaLeft = getGameStateB().getManaCrystals();
            List<DirectDamageCard> currentSpells = new LinkedList<>();

            if (manaLeft >= gameStateB.getMe().getHeroPowerB().getManaCost()) {
                currentSpells.add(gameStateB.getMe().getHeroPowerB());
            }
            calculateMaxSpellDamageHelper(index, manaLeft, currentSpells);
        }

        private void calculateMaxSpellDamageHelper(int index, int manaLeft, List<DirectDamageCard> currentSpells) {
            CardB card = null;
            boolean canTake = false;
            if (index >= getGameStateB().getMyHand().size() || manaLeft == 0) {
                int currentTotalDamage = calculateCurrentTotalDamage(currentSpells);
                if (currentTotalDamage > totalDamage) {
                    cardBs.clear();
                    cardBs.addAll(currentSpells);
                    totalDamage = currentTotalDamage;
                }
                return;
            }
            if (index < getGameStateB().getMyHand().size()) {
                card = getGameStateB().getMyHand().get(index);
                canTake = card.getManaCost() <= manaLeft && card instanceof DirectDamageCard && ((DirectDamageCard) card).hitsEnemyHero();
            }
            if (canTake) {
                currentSpells.add((DirectDamageCard) card);
                calculateMaxSpellDamageHelper(index + 1, manaLeft - card.getManaCost(), currentSpells);
            }
            calculateMaxSpellDamageHelper(index + 1, manaLeft, currentSpells);
        }

        private int calculateCurrentTotalDamage(List<DirectDamageCard> currentSpells) {
            int tempDmg = 0;
            for (DirectDamageCard spell : currentSpells) {
                tempDmg += spell.getDamage();
            }
            return tempDmg;
        }
    }

    interface DirectDamageCard {
        boolean hitsEnemyHero();

        int getDamage();
    }
}
