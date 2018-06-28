package io.swagslash.settlersofcatan.controller.actions;

import io.swagslash.settlersofcatan.Player;
import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;

public class CardDrawAction extends GameAction {

    DevCard.DevCardTyp cardTyp;

    public CardDrawAction() {
    }

    public CardDrawAction(Player actor, DevCard.DevCardTyp cardTyp) {
        super(actor);
        this.cardTyp = cardTyp;
    }

    public DevCard.DevCardTyp getCardTyp() {
        return cardTyp;
    }

    public void setCardTyp(DevCard.DevCardTyp cardTyp) {
        this.cardTyp = cardTyp;
    }
}
