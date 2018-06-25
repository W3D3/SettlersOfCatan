package io.swagslash.settlersofcatan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.items.DevelopmentCard;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Map<DevelopmentCard, Integer> cards;
    private ArrayList<DevelopmentCard> displayedCards;
    private DevelopmentCard selectedCard = null;

    CardListAdapter(Map<DevelopmentCard, Integer> input) {
        cards = input;
        for (DevelopmentCard dc : input.keySet()) {
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

    public ArrayList<DevelopmentCard> getDisplayedCards() {
        return displayedCards;
    }

    public DevelopmentCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(DevelopmentCard selectedCard) {
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
