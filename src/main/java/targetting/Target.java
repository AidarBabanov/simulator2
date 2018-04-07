package targetting;

public interface Target {
    boolean isTargetable();
    void getDamage(int damage);
    int responseDamage();
    void die();
    boolean isAlive();
}
