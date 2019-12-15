package at.ac.fhcampuswien;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class Cell extends Pane {
    private ImageView view = new ImageView();
    private List<Cell> neighbours;
    private int nearbyMines = 0;
    private String type; //mine, normal
    private String state; //marked, covered, uncovered

    // TODO add addtional variables you need. for state...

    public Cell(String imgUrl, String state, String type) {
        view = new ImageView(new Image(imgUrl));
        getChildren().add(view);
        // TODO add stuff here if needed.
        this.state = state;
        this.type = type;
    }

    public void setNeighbours(List<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public void addNearbyMine() {
        this.nearbyMines++;
    }
    
    public int getNearbyMines() {
        return nearbyMines;
    }
    
    public String getSate() {
            return state;
        }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public void update(String imgUrl, String state, String type) {
        this.view.setImage(new Image(imgUrl));
        this.state = state;
        this.type = type;
    }
    
    
}
