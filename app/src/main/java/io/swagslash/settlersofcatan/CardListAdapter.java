package io.swagslash.settlersofcatan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.items.cards.DevCard;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Map<DevCard, Integer> cards;
    private ArrayList<DevCard> displayedCards;
    private DevCard selectedCard = null;

    CardListAdapter(Map<DevCard, Integer> input) {
        displayedCards = new ArrayList<>();
        cards = input;
        for (DevCard dc : input.keySet()) {
            int cnt = input.get(dc);
            for (int i = 0; i < cnt; i++) {
                displayedCards.add(dc);
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView cardDesc = holder.v.findViewById(R.id.card_desc);
        cardDesc.setText(displayedCards.get(position).getCardText());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card_item, parent, false);

        return new ViewHolder(container);
    }

    @Override
    public int getItemCount() {
        return displayedCards.size();
    }

    public ArrayList<DevCard> getDisplayedCards() {
        return displayedCards;
    }

    public DevCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(DevCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View v;

        ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
    }
}
