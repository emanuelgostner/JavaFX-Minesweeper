package at.ac.fhcampuswien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller {

    // Model
    private Board board;

    // private
    private boolean isActive;

    // View Fields
    @FXML
    private Label message;
    @FXML
    private GridPane grid;
    @FXML
    private Button restart;

    @FXML
    public void initialize() {
        isActive = true;
        this.board = new Board();
        Cell[][] cells = this.board.getCells();
        for(int i = 0; i < Board.ROWS; i++){
            for(int j = 0; j < Board.COLS; j++) {
                // uncomment below as soon as the Cells are initialized by class Board.
                grid.add(cells[i][j], j, i);
            }
        }
    }
    @FXML
    public void update(MouseEvent event){
        Cell[][] cells = this.board.getCells();
        if(isActive) {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                int row = (int) event.getY() / Board.CELL_SIZE;
                int col = (int) event.getX() / Board.CELL_SIZE;
                if (event.getButton() == MouseButton.PRIMARY) {
                    // TODO Uncover...
                    if(!this.board.uncover(row,col)){
                        this.isActive = false;
                        this.message.setText("You stepped into a mine ¯\\_(ツ)_/¯");
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    // TODO Mark...
                    this.board.markCell(row, col);
                }
                // TODO 1. Check if the player has already won
                if(board.isGameWon()){
                    this.isActive = false;
                    this.message.setText("You won, good job!");
                }
                // TODO 2. If the game is still in active mode show used mine markers.
                if(isActive)
                    message.setText(" Marker: " + board.getMinesMarked() + "/" + Board.NUM_MINES);
            }
        }
    }

    @FXML
    public void restart(ActionEvent actionEvent) {
        grid.getChildren().clear();
        initialize();
        this.message.setText("You shall be reborn!");
    }
}
