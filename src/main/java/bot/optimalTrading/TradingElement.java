package bot.optimalTrading;

import bot.CardB;
import bot.MinionB;
import bot.SpellB;

public class TradingElement {
    int damage;     //weight
    int health;
    int attackedStats;
    int notAttackedStats;
    int manaCost;
    CardB card;

    public TradingElement(CardB card, int takingDamage) {
        this.card = card;
        if (card instanceof MinionB) {
            this.damage = ((MinionB) card).getAttack();
            this.health = ((MinionB) card).getHealth();
            if (takingDamage >= this.health) this.attackedStats = 0;
            if (takingDamage < this.health) this.attackedStats = takingDamage - this.health + this.damage;
            this.notAttackedStats = this.damage + this.health;
            this.manaCost = 0;
        }
        if(card instanceof SpellB){
            this.damage = ((SpellB) card).getDamage();
            this.health = 0;
            this.attackedStats = 0;
            this.notAttackedStats = 0;
            this.manaCost = card.getManaCost();
        }
    }
}
