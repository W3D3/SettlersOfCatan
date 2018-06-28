package io.swagslash.settlersofcatan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagslash.settlersofcatan.pieces.items.DevelopmentCard;

@Ignore
public class ListAdapterTest {

    private PlayerListAdapter pla;
    private CardListAdapter cla;
    private int playerCount;
    private int cardCount;

    @Before
    public void init() {
        // players
        List<Player> players = new ArrayList<>();

        Player a = new Player(null, 0, 0, "A");
        Player b = new Player(null, 0, 0, "B");
        Player c = new Player(null, 0, 0, "C");
        Player d = new Player(null, 0, 0, "D");
        players.add(a);
        players.add(b);
        players.add(c);
        players.add(d);

        playerCount = players.size();
        pla = new PlayerListAdapter(players);

        // cards
        Map<DevelopmentCard, Integer> cards = new HashMap<>();

        /*
        cards.put(new D);
        cards.add("Card2");
        cards.add("Card3");
        cards.add("Card4");
        */
        cardCount = cards.size();
        cla = new CardListAdapter(cards);
    }

    @Test
    public void checkSize() {
        Assert.assertEquals(pla.getItemCount(), playerCount);
        Assert.assertEquals(cla.getItemCount(), cardCount);
    }
}
