package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;

public class DiceRollAction extends GameAction {
    public int dic1;
    public int dic2;

    public DiceRollAction() {
    }

    public DiceRollAction(Player actor) {
        super(actor);
    }

    public DiceRollAction(Player actor, int dic1, int dic2) {
        super(actor);
        this.dic1 = dic1;
        this.dic2 = dic2;
    }

    public int getDic1() {
        return dic1;
    }

    public void setDic1(int dic1) {
        this.dic1 = dic1;
    }

    public int getDic2() {
        return dic2;
    }

    public void setDic2(int dic2) {
        this.dic2 = dic2;
    }
}
