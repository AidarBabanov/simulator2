package bot;

import bot.commands.*;
import bot.optimalTrading.Table;
import bot.spells.DamageSpellB;
import bot.spells.MissilesSpellB;
import bot.spells.SpellEffectB;
import com.google.gson.JsonObject;
import entities.cards.Minion;
import spellEffects.DamageSpell;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SimpleBot {

    private GameStateB gameStateB;
    private MaxSpellDamage maxSpellDamage;
    private List<Command> commands;
    private String name;
    private boolean nextMessageUpdateState;

    public SimpleBot(String botName) {
        commands = new LinkedList<>();
        name = botName;
    }

    public SimpleBot(JsonObject jsonState) {
        commands = new LinkedList<>();
        updateState(jsonState);
    }

    public String getNextCommand() {
        if (commands == null || commands.size() == 0) {
            return new EndCommand(name).toString();
        } else {
            String message = commands.remove(0).toString();
            return message;
        }
    }

    public void updateState(JsonObject jsonState) {
        this.setGameStateB(new GameStateB(jsonState));
        this.gameStateB.getMe().getHeroPowerB().setUsed(false);
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
//        System.out.println("Doing tempo moves");
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

        //add aoe command
        Command castAOE = new PlayCardCommand(name, gameStateB.getMyHand().indexOf(effectiveSpell));
        commands.add(castAOE);
        //

        //start trading
        List<MinionB> listOfEnemyMinions = new LinkedList<>(gameStateB.getOpponentTable());
        listOfEnemyMinions.sort(new Comparator<MinionB>() {
            @Override
            public int compare(MinionB o1, MinionB o2) {
                if (o1.getAttack() == o2.getAttack()) return o1.getHealth() - o2.getHealth();
                else return o2.getAttack() - o1.getAttack();
            }
        });
        gameStateB.setManaCrystals(gameStateB.getManaCrystals() - effectiveManaCost);


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
                if (card instanceof HeroPowerB) {
                    gameStateB.getMe().getHeroPowerB().setUsed(true);
                }
            }
            //add trading command
            for (CardB card : effectiveList) {
                if (card instanceof MinionB) {
                    int sourcePosition = gameStateB.getMyTable().indexOf(card);
                    int targetPosition = gameStateB.getOpponentTable().indexOf(minion);
                    Command attackEnemyMinion = new AttackCommand(name, AttackCommand.TargetSource.MINION, sourcePosition, AttackCommand.TargetSource.MINION, targetPosition);
                    commands.add(attackEnemyMinion);
                }
                if (card instanceof SpellB) {
                    if (((SpellB) card).getSpellEffect() instanceof MissilesSpellB) {
                        int handPosition = gameStateB.getMyHand().indexOf(card);
                        Command missileSpell = new PlayCardCommand(name, handPosition);
                        commands.add(missileSpell);
                    } else {
                        int handPosition = gameStateB.getMyHand().indexOf(card);
                        int targetPosition = gameStateB.getOpponentTable().indexOf(minion);
                        Command missileSpell = new PlayCardCommand(name, handPosition);
                        commands.add(missileSpell);
                        Command target = new TargetCommand(name, TargetCommand.TargetSource.MINION, TargetCommand.Owner.OPPONENT, targetPosition);
                        commands.add(target);
                    }
                }
                if (card instanceof HeroPowerB) {
                    Command heroPower = new UseHeroPowerCommand(name);
                    commands.add(heroPower);
                    Command target = new TargetCommand(name, TargetCommand.TargetSource.MINION, TargetCommand.Owner.OPPONENT, -1);
                    commands.add(target);
                }
            }
        }
        //end of trading

        //start attack hero
        for (MinionB minion : gameStateB.getMyTable()) {
            if (!minion.isUsed()) {
                int tablePosition = gameStateB.getMyTable().indexOf(minion);
                Command attackHero = new AttackCommand(name, AttackCommand.TargetSource.MINION, tablePosition, AttackCommand.TargetSource.HERO, -1);
                commands.add(attackHero);
            }
        }
        //end attack hero

        //start minions to play
        List<MinionB> minionsToPlay = new LinkedList<>();
        for (CardB card : gameStateB.getMyHand()) {
            if (card instanceof MinionB) {
                minionsToPlay.add((MinionB) card);
            }
        }

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

        for (MinionB minion : minionsToPlay) {
            int handPosition = gameStateB.getMyHand().indexOf(minion);
            int tablePosition = gameStateB.getMyTable().size();
            Command playMinion = new PlayCardCommand(name, handPosition);
            commands.add(playMinion);
            Command position = new TablePositionCommand(name, tablePosition);
            commands.add(position);
        }
        //end minions to play

        commands.add(new EndCommand(name));
    }

    private void attackEnemyHero() {
//        System.out.println("Attacking hero");
        for (MinionB minion : gameStateB.getMyTable()) {
            int tablePosition = gameStateB.getMyTable().indexOf(minion);
            Command attackHero = new AttackCommand(name, AttackCommand.TargetSource.MINION, tablePosition, AttackCommand.TargetSource.HERO, -1);
            commands.add(attackHero);
        }

        for (DirectDamageCard card : maxSpellDamage.cardBs) {
            if (card instanceof SpellB) {
                int handPosition = gameStateB.getMyHand().indexOf(card);
                Command spell = new PlayCardCommand(name, handPosition);
                commands.add(spell);
                if (((SpellB) card).getSpellEffect() instanceof DamageSpellB) {
                    commands.add(new TargetCommand(name, TargetCommand.TargetSource.HERO, TargetCommand.Owner.OPPONENT, -1));
                }
            }
            if (card instanceof HeroPowerB) {
                Command heroPower = new UseHeroPowerCommand(name);
                commands.add(heroPower);
                Command target = new TargetCommand(name, TargetCommand.TargetSource.HERO, TargetCommand.Owner.OPPONENT, -1);
                commands.add(target);
            }
        }
        System.out.println(commands);
    }

    private int calculateMyTotalDamage() {
        int totalDamage = 0;
        List<MinionB> myTable = gameStateB.getMyTable();
        for (MinionB minion : myTable) {
            totalDamage += minion.getAttack();
        }
        totalDamage += maxSpellDamage.totalDamage;

        return totalDamage;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNextMessageUpdateState() {
        return nextMessageUpdateState;
    }

    public void setNextMessageUpdateState(boolean nextMessageUpdateState) {
        this.nextMessageUpdateState = nextMessageUpdateState;
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

            calculateMaxSpellDamageHelper(index, manaLeft, currentSpells);

            int manaSpent = 0;
            for (DirectDamageCard card : currentSpells) {
                manaSpent += card.getManaCost();
            }
            if (manaLeft - manaSpent >= gameStateB.getMe().getHeroPowerB().getManaCost()) {
                cardBs.add(gameStateB.getMe().getHeroPowerB());
                totalDamage += gameStateB.getMe().getHeroPowerB().getDamage();
            }
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

        int getManaCost();
    }
}
