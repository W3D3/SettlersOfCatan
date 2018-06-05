package io.swagslash.settlersofcatan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private String[] cards;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }
    }

    CardListAdapter(String[] input){
        cards = input;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card_item, parent, false);

        return new ViewHolder(container);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView cardDesc = holder.v.findViewById(R.id.card_desc);
        cardDesc.setText(cards[position]);
    }

    @Override
    public int getItemCount() {
        return cards.length;
    }
}
