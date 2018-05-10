package bot.optimalTrading;

import bot.*;
import bot.spells.DamageSpellB;

import java.util.*;

public class Table {
    private int targetMinionHealth;   //health of minion, which we want to kill, bag weight
    private int targetMinionDamage;   //damage of minion, which we want to kill
    private int mana;           //mana which we can use
    private int rows;
    private int cols;
    private List<TradingElement> elements;
    private Cell[][] table;
    private Cell defaultCell;

    public Table(GameStateB gameStateB, MinionB minionB) {
        elements = new LinkedList<>();
        elements.add(null);     //just to make calculations easier, as algorithm uses previous rows
        targetMinionHealth = minionB.getHealth();
        targetMinionDamage = minionB.getAttack();
        mana = gameStateB.getManaCrystals();
        int totalDamage = 0;
        for (CardB cardB : gameStateB.getMyHand()) {
            boolean addCard = false;
            if (cardB instanceof SpellB && !cardB.isUsed()) {
                SpellB spellB = (SpellB) cardB;
                if (spellB.getSpellEffect() instanceof DamageSpellB) {
                    DamageSpellB damageSpellB = (DamageSpellB) spellB.getSpellEffect();
                    switch (damageSpellB.getTargetTypeB()) {
                        case MINION:
                            addCard = true;
                            break;
                        case CHARACTER:
                            addCard = true;
                            break;
                        case ENEMY_MINION:
                            addCard = true;
                            break;
                        case ENEMY_CHARACTER:
                            addCard = true;
                    }
                }
            }
            TradingElement tradingElement = new TradingElement(cardB, targetMinionDamage);
            totalDamage += tradingElement.damage;
            if (addCard) elements.add(tradingElement);
        }

        if (!gameStateB.getMe().getHeroPowerB().isUsed())
            elements.add(new TradingElement(gameStateB.getMe().getHeroPowerB(), targetMinionDamage));

        for (MinionB minion : gameStateB.getMyTable()) {
            if (!minion.isUsed()) elements.add(new TradingElement(minion, targetMinionDamage));
        }

        elements.sort(new Comparator<TradingElement>() {
            @Override
            public int compare(TradingElement o1, TradingElement o2) {
                return o1.damage - o2.damage;
            }
        });
        rows = elements.size();
        cols = totalDamage + 1;
        table = new Cell[rows][cols];

        //first row null values. First row makes calculations easier
        defaultCell = new Cell();
        for (int j = 0; j < cols; j++) {
            table[0][j].prevCell = defaultCell;
        }
    }

    public List<CardB> getEffectiveList() {
        List<CardB> list = new LinkedList<>();
        createTable();
        int maxUsed = rows;
        int endIndex = cols - 1;
        for (int j = targetMinionHealth; j < cols; j++) {
            if (maxUsed < table[rows - 1][j].usedElements && table[rows - 1][j].damage >= targetMinionHealth) {
                maxUsed = table[rows - 1][j].usedElements;
                endIndex = j;
            }
            Cell currCell = table[rows - 1][endIndex];
            while (currCell != defaultCell) {
                list.add(currCell.tradingElement.card);
                currCell = currCell.prevCell;
            }
        }
        return list;
    }

    private void createTable() {
        for (int i = 1; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j].tradingElement = elements.get(i);
                int currentDamage = elements.get(i).damage;
                int currentPlayStats = elements.get(i).attackedStats;
                int currentNotPlayStats = elements.get(i).notAttackedStats;
                int manaCost = elements.get(i).manaCost;
                int oldIndex = j - elements.get(i).damage;
                if (oldIndex >= 0 && mana >= manaCost + table[i][oldIndex].manaUsed && currentDamage + table[i - 1][oldIndex].damage <= j && currentDamage + table[i - 1][oldIndex].damage >= table[i - 1][j].damage) {
                    if (table[i - 1][oldIndex].damage > table[i - 1][j].damage || (table[i - 1][oldIndex].damage == table[i - 1][j].damage && table[i - 1][oldIndex].stats + currentPlayStats > table[i - 1][j - 1].stats + currentNotPlayStats))
                        attacked(i, j);
                    else notAttacked(i, j);
                } else notAttacked(i, j);
            }
        }
    }

    private void notAttacked(int i, int j) {
        table[i][j].stats = table[i - 1][j].stats + elements.get(i).notAttackedStats;
        table[i][j].manaUsed = table[i - 1][j].manaUsed;
        table[i][j].damage = table[i - 1][j].damage;
        table[i][j].isAttacked = false;
        table[i][j].usedElements = table[i - 1][j].usedElements;
        table[i][j].prevCell = table[i - 1][j];
    }

    private void attacked(int i, int j) {
        int oldIndex = j - elements.get(i).damage;
        table[i][j].stats = table[i - 1][oldIndex].stats + elements.get(i).attackedStats;
        table[i][j].manaUsed = table[i - 1][oldIndex].manaUsed + elements.get(i).manaCost;
        table[i][j].damage = table[i - 1][oldIndex].damage + elements.get(i).damage;
        table[i][j].isAttacked = true;
        table[i][j].usedElements = table[i - 1][oldIndex].usedElements + 1;
        table[i][j].prevCell = table[i - 1][oldIndex];
    }

}
