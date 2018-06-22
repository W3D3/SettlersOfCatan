package io.swagslash.settlersofcatan.utility;

import java.util.ArrayList;
import java.util.List;

public class TradeUpdateIntent extends TradeOfferIntent {
    private List<String> selectedOfferees = new ArrayList<>();

    public List<String> getSelectedOfferees() {
        return selectedOfferees;
    }

    public void setSelectedOfferees(List<String> selectedOfferees) {
        this.selectedOfferees = selectedOfferees;
    }
}
