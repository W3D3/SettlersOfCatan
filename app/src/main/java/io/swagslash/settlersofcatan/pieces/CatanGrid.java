//package io.swagslash.settlersofcatan.pieces;
//
//import java.util.ArrayList;
//
//import io.swagslash.settlersofcatan.pieces.utility.AxialHexLocation;
//import io.swagslash.settlersofcatan.pieces.utility.HexGridLayout;
//import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
//
///**
// * Created by wedenigc on 19.03.18.
// */
//
//public class CatanGrid {
//    private HexGridLayout gridLayout;
//    private Board board;
//
//    public CatanGrid(Board b, Integer diameter) {
//        this.board = b;
//        if(diameter % 2 == 0) throw new UnsupportedOperationException("Cannot create a Catan board with even diameter.");
//
//        this.gridLayout = new HexGridLayout(HexGridLayout.pointy, HexGridLayout.size_default, HexGridLayout.origin_default);
//
//        //Center is always Desert
//        Hex centerHex = new Hex(b, Hex.TerrainType.DESERT, new AxialHexLocation(0,0));
//        //ArrayList<HexPoint> hexPoints = HexGridLayout.polygonCorners(this.gridLayout, new AxialHexLocation(0, 0));
//        centerHex.calculateVertices(gridLayout);
//
//        Integer start = ((Double)(-(Math.floor(diameter/2)))).intValue();
//        Integer end = ((Double)((Math.floor(diameter/2)))).intValue();
//        for (int q = start; q <= end; q ++)
//        {
//            for (int r = start; r <= end; r ++)
//            {
//                Hex hex = new Hex(b, Hex.TerrainType.DESERT, new AxialHexLocation(0,0));
//                this.board.addHex()
//            }
//        }
//    }
//}
