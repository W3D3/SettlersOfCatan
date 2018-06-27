package io.swagslash.settlersofcatan.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.graphics.Shader;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.otaliastudios.zoom.ZoomLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.swagslash.settlersofcatan.MainActivity;
import io.swagslash.settlersofcatan.R;
import io.swagslash.settlersofcatan.SettlerApp;
import io.swagslash.settlersofcatan.controller.GameController;
import io.swagslash.settlersofcatan.controller.PhaseController;
import io.swagslash.settlersofcatan.pieces.Board;
import io.swagslash.settlersofcatan.pieces.Edge;
import io.swagslash.settlersofcatan.pieces.Hex;
import io.swagslash.settlersofcatan.pieces.Vertex;
import io.swagslash.settlersofcatan.pieces.items.Resource;
import io.swagslash.settlersofcatan.pieces.utility.HexPoint;
import io.swagslash.settlersofcatan.utility.Pair;

/**
 * HexView is a View that draws a Standard Catan Grid on a Canvas
 */
public class HexView extends View {

    Board board;
    List<Hex> hexes;
    List<Region> regionList;
    WindowManager manager;
    int maxX;
    int maxY;
    int scale = 1;
    Pair<Integer, Integer> offset = null;
    List<Path> takenVertices;
    List<Path> freeVertices;
    List<Path> takenEdges;
    List<Path> freeEdges;
    ZoomLayout zoomLayout = null;
    //Hexagon paint
    Paint strokePaint;
    Paint fillPaint;
    // Vertex Paint
    Paint circlePaint;
    Paint edgePaint;
    Paint roadPaint;
    Region clip;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleDetector;
    private Paint vertexClickPaint;
    private Paint textPaint;

    MainActivity activity;

    public HexView(Context context) {
        super(context);

        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }

        hexes = new ArrayList<>();
        regionList = new ArrayList<>();

        this.strokePaint = new Paint();
        this.fillPaint = new Paint();

        this.gestureDetector = null;
        this.scaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        this.vertexClickPaint = new Paint();
        vertexClickPaint.setColor(Color.WHITE);
        vertexClickPaint.setStyle(Paint.Style.STROKE);

        freeVertices = new ArrayList<>();
        takenVertices = new ArrayList<>();
        freeEdges = new ArrayList<>();
        takenEdges = new ArrayList<>();

        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.FILL);

        edgePaint = new Paint();
        edgePaint.setColor(Color.BLUE);
        edgePaint.setStyle(Paint.Style.STROKE);
        edgePaint.setStrokeWidth(4);

        roadPaint = new Paint();
        roadPaint.setColor(Color.GRAY);
        roadPaint.setStyle(Paint.Style.STROKE);
        roadPaint.setStrokeWidth(8);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(48);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public WindowManager getManager() {
        return manager;
    }

    public void setManager(WindowManager manager) {
        this.manager = manager;
    }

    public ZoomLayout getZoomLayout() {
        return zoomLayout;
    }

    public void setZoomLayout(ZoomLayout zoomLayout) {
        this.zoomLayout = zoomLayout;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void prepare() {
        hexes.addAll(board.getHexagons());

        Display mdisp = getManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;
        scale = Math.min(maxX, maxY) / 5;
        offset = new Pair<>(Math.min(maxX, maxY) / 2, Math.min(maxX, maxY) / 2);
        clip = new Region();

        //TODO ADJUST MIN/MAX HEIGHT/WIDTH VIA PROPERTIES?
        // OR GET IT FROM PARENT?
        setMinimumHeight(maxY);
        setMinimumWidth(maxX);

        generateHexPaths();
        generateVerticePaths();
        generateEdgePaths();

        //ready to draw
        setWillNotDraw(false);
        invalidate();
    }

    public void generateHexPaths() {
        // GENERATE PATHS und so
        for (Hex hex : hexes) {
            hex.calculatePath(offset, scale);
            Path path = hex.getPath();
            Region r = new Region();
            r.setPath(path, clip);
            hex.setRegion(r);
            if (hex.hasRobber()) {
                hex.getRobber().calculatePath(offset, scale);
            }
        }
    }

    public void generateVerticePaths() {
        for (Vertex v : board.getVerticesList()) {
            HexPoint drawPoint = v.getCoordinates().scale(offset, scale);
            v.calculatePath(offset, scale);
        }
    }

    public void generateEdgePaths() {
        for (Edge e : board.getEdgesList()) {
            e.calculatePath(offset, scale);
        }
    }

    protected void onDraw(Canvas c) {

        super.onDraw(c);

        clip.set(0, 0, c.getWidth(), c.getHeight());

        //Background white
        Paint bg = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_texture);
        BitmapShader fillBMPshader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bg.setShader(fillBMPshader);


        bg.setStyle(Paint.Style.FILL);
        c.drawPaint(bg);

        strokePaint.setStrokeWidth(3);
        strokePaint.setPathEffect(null);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);

        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.YELLOW);


        for (Hex hex : hexes) {
            Path path = hex.getPath();
            path.setFillType(Path.FillType.EVEN_ODD);
            fillPaint.setColor(hex.getTerrainColor());
            c.drawPath(path, fillPaint);
            c.drawPath(path, strokePaint);
            Region r = new Region();
            r.setPath(path, clip);
            hex.setRegion(r);
        }


        PhaseController phaseController = board.getPhaseController();

        for (Vertex vertex : board.getVerticesList()) {
            Region region = new Region();
            region.setPath(vertex.getPath(), clip);
            vertex.setRegion(region);
            switch (vertex.getUnitType()) {
                case NONE:
                    if (phaseController.isAllowedToBuildOnVertex(vertex)) {
                        c.drawPath(vertex.getPath(), vertexClickPaint);
                    }
                    break;
                case SETTLEMENT:
                    if (phaseController.isAllowedToBuildOnVertex(vertex) && phaseController.getCurrentPhase() == Board.Phase.SETUP_CITY) {
                        c.drawPath(vertex.getPath(), vertexClickPaint);
                    } else {
                        circlePaint.setColor(vertex.getOwner().getColor());
                        c.drawPath(vertex.getPath(), circlePaint);
                    }
                    break;
                case CITY:
                    circlePaint.setColor(vertex.getOwner().getColor());
                    c.drawPath(vertex.getPath(), circlePaint);
                    break;
            }

        }

        for (Edge edge : board.getEdgesList()) {
            Region region = new Region();
            region.setPath(edge.getPath(), clip);
            edge.setRegion(region);
            switch (edge.getUnitType()) {
                case ROAD:
                    roadPaint.setColor(edge.getOwner().getColor());
                    c.drawPath(edge.getPath(), roadPaint);
                    break;
                case NONE:
                    if(phaseController.isAllowedToBuildOnEdge(edge)) {
                        c.drawPath(edge.getPath(), vertexClickPaint);
                    }
                    break;
                default:
                    break;
            }
        }

        // DRAW NUMBER TOKENS OVER THEM

        for (Hex hex : hexes) {

            final HexPoint coordinates = hex.getCenter().scale(offset, scale);
            if(hex.getNumberToken() != null) {
                if (hex.getNumberToken().getNumber() > 0 && !hex.hasRobber()) {
                    c.drawText(hex.getNumberToken().toString(), (float)coordinates.x, (float)coordinates.y, textPaint);
                    invalidate();
                } else if (hex.hasRobber()) {
                    hex.getRobber().calculatePath(offset, scale);
                    c.drawPath(hex.getRobber().getPath(), textPaint);
                    invalidate();
                }

            }

        }


    }

    public void showFreeSettlements() {
        redraw();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //TODO remove debug data and handle touches properly

        if (gestureDetector == null) {
            this.gestureDetector = new GestureDetector(this.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent event) {
                    // triggers first for both single tap and long press
                    redraw();
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if(handleVertexClick(e)) return true;
                    if(handleEdgeClick(e)) return true;
                    handleHexClick(e, "Single Tap");

                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    handleHexClick(e, "Long Press");
                }

                // Keep this in for future use
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    handleHexClick(e, "Double Tap");
                    return false;
                }
            });
        }
        //detect touch
        gestureDetector.onTouchEvent(event);
        scaleDetector.onTouchEvent(event);


        return true;
    }

    private Pair<Integer, Integer> getCoordinates(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (zoomLayout != null) {
            // invert the coordinates so they map back to our regions when using a zoom engine
            //x = x * (1/zoomLayout.getEngine().getRealZoom()) - zoomLayout.getEngine().getPanX();
            //y = y * (1/ zoomLayout.getEngine().getRealZoom()) - zoomLayout.getEngine().getPanY();
        }
        return new Pair<>((int) x, (int) y);
    }

    private Hex getHexFromCoordinates(int x, int y) {
        for (int i = 0; i < hexes.size(); i++) {
            Region r = hexes.get(i).getRegion();
            if (r.contains(x, y)) {
                return hexes.get(i);
            }
        }
        return null;
    }

    private Vertex getVertexFromCoordinates(int x, int y) {
        Collection<Vertex> vertices = SettlerApp.board.getVerticesList();

        for (Vertex vertex : vertices) {
            Region r = vertex.getRegion();
            if (r.contains(x, y)) {
                return vertex;
            }
        }

        return null;
    }

    private Edge getEdgeFromCoordinates(int x, int y) {
        final ArrayList<Edge> edges = new ArrayList<>(SettlerApp.board.getEdgesList());
        for (int i = 0; i < edges.size(); i++) {
            Region r = edges.get(i).getRegion();
            if (r.contains(x, y)) {
                return edges.get(i);
            }
        }
        return null;
    }

    private void handleHexClick(MotionEvent event, String msg) {
        Pair<Integer, Integer> coordinates = getCoordinates(event);
        Hex hex = getHexFromCoordinates(coordinates.first, coordinates.second);
        if (hex == null) return;

        switch (SettlerApp.board.getPhaseController().getCurrentPhase()) {
            case MOVING_ROBBER:
                if (GameController.getInstance().canRob(hex)) {

                    generateHexPaths();
                    GameController.getInstance().moveRobber(hex);
                    activity.choosePlayerToRob(hex.getRobber().getRobbablePlayers(SettlerApp.getPlayer()));

                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                }
                break;
        }


        System.out.println(hex.toString());
        Toast.makeText(getContext().getApplicationContext(), hex.toString() + " ~ " + msg,
                Toast.LENGTH_SHORT).show();
    }


    private boolean handleVertexClick(MotionEvent event) {
        Pair<Integer, Integer> coordinates = getCoordinates(event);
        Vertex vertex = getVertexFromCoordinates(coordinates.first, coordinates.second);
        if (vertex == null) return false;
        boolean buildSuccess = false;

        Toast.makeText(getContext().getApplicationContext(), vertex.toString() + " ~ clicked",
                Toast.LENGTH_SHORT).show();

        switch (SettlerApp.board.getPhaseController().getCurrentPhase()) {

            case SETUP_SETTLEMENT:
                if(GameController.getInstance().buildSettlement(vertex, SettlerApp.getPlayer())) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    buildSuccess = true;
                }
                generateVerticePaths();
                redraw();

                break;
            case FREE_SETTLEMENT:
                if(GameController.getInstance().buildFreeSettlement(vertex, SettlerApp.getPlayer())) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.FREE_ROAD);
                    buildSuccess = true;
                }
                //TODO REMOVE!
                //GameController.getInstance().buildCity(vertex, SettlerApp.getPlayer());
                SettlerApp.getPlayer().getInventory().addResource(new Resource(Resource.ResourceType.BRICK), 5);
                SettlerApp.getPlayer().getInventory().addResource(new Resource(Resource.ResourceType.WOOD), 5);
                SettlerApp.getPlayer().getInventory().addResource(new Resource(Resource.ResourceType.ORE), 5);
                SettlerApp.getPlayer().getInventory().addResource(new Resource(Resource.ResourceType.GRAIN), 5);
                SettlerApp.getPlayer().getInventory().addResource(new Resource(Resource.ResourceType.WOOL), 5);

                generateVerticePaths();
                redraw();
                break;
            case SETUP_CITY:
                if (GameController.getInstance().buildCity(vertex, SettlerApp.getPlayer())) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    buildSuccess = true;
                }

                generateVerticePaths();
                redraw();
                break;
            default:
                break;
        }
        if (buildSuccess) activity.updateResources();
        return buildSuccess;
    }

    private boolean handleEdgeClick(MotionEvent event) {
        Pair<Integer, Integer> coordinates = getCoordinates(event);
        Edge edge = getEdgeFromCoordinates(coordinates.first, coordinates.second);
        if (edge == null) return false;
        boolean buildSuccess = false;

        Toast.makeText(getContext().getApplicationContext(), edge.toString() + " ~ clicked",
                Toast.LENGTH_SHORT).show();

        switch (SettlerApp.board.getPhaseController().getCurrentPhase()) {

            case SETUP_ROAD:


                if(GameController.getInstance().buildRoad(edge, SettlerApp.getPlayer())) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    buildSuccess = true;
                }
                generateEdgePaths();
                redraw();

                break;
            case FREE_ROAD:

                if(GameController.getInstance().buildFreeRoad(edge, SettlerApp.getPlayer())) {
                    SettlerApp.board.getPhaseController().setCurrentPhase(Board.Phase.PLAYER_TURN);
                    buildSuccess = true;
                }
                generateEdgePaths();
                redraw();

                break;
        }
        if (buildSuccess) activity.updateResources();
        return buildSuccess;
    }

    public void redraw() {
        this.invalidate();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            redraw();
            return true;

        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            redraw();
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            redraw();
            return true;
        }
    }


}
