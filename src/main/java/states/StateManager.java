package states;

import player.Player;

public class StateManager {
    private State state;
    private Player player1;
    private Player player2;
    private static StateManager instance;

    public StateManager() {

    }

    public StateManager(State state, Player player1, Player player2) {
        this.setState(state);
        this.setPlayer1(player1);
        this.setPlayer2(player2);
    }

    public static StateManager getInstance() {
        if (instance == null) instance = new StateManager();
        return instance;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public void performActions() {
        state.performActions();
    }

    public void changeState() {
        if (state instanceof PreGame) {
            state = new PlayerTurn(player1);
        } else if (state instanceof PlayerTurn) {
            if (((PlayerTurn) state).getPlayer().getHero().getCurrentHealth() <= 0) {
                state = new EndGame();
            } else if (((PlayerTurn) state).getPlayer().getOpponent().getHero().getCurrentHealth() <= 0) {
                state = new EndGame();
            } else if (((PlayerTurn) state).getPlayer().equals(player1)) {
                state = new PlayerTurn(player2);
            } else if (((PlayerTurn) state).getPlayer().equals(player2)) {
                state = new PlayerTurn(player1);
            }
        }
    }

    public boolean gameEnded() {
        return state instanceof EndGame;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
