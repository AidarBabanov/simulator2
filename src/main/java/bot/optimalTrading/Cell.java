package bot.optimalTrading;

public class Cell {
    int damage;
    int manaUsed;
    int stats;
    boolean isAttacked;
    Cell prevCell;
    int usedElements;
    TradingElement tradingElement;

    Cell() {
        damage = 0;
        manaUsed = 0;
        stats = 0;
        isAttacked = false;
        prevCell = this;
        usedElements = 0;
        tradingElement = null;
    }
}
