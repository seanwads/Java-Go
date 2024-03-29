import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GoTest {
    final int testBoardSize = 13;
    Board board;

    @Before
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    public void createBoard(){
        board = new Board();
        board.boardSize = testBoardSize;
    }

    @Test
    public void testCreateBoardSize(){
        board.firstTurn(false);

        Assertions.assertEquals(testBoardSize, board.buttons.length, "Board size " + board.buttons.length + " not " + testBoardSize*testBoardSize);
    }

    @Test
    public void testSetHandicap(){
        board.firstTurn(true);;

        Assertions.assertTrue(board.isHandicapTurn, "Handicap turn not initialised");
        Assertions.assertFalse(board.buttons[0][0].getEnabled(), "Handicap board mode not initialised - non-handicap stones not disabled");
        Assertions.assertTrue(board.buttons[3][3].getEnabled(), "Handicap board mode not initialised - handicap stones not enabled");

        board.startButton.doClick();

        Assertions.assertTrue(board.buttons[0][0].getEnabled(), "Stones not enabled on exiting handicap mode");
    }

    @Test
    public void testStonesEnabled(){
        board.firstTurn(false);

        boolean isEnabled = false;
        boolean isStonePlaced = false;

        for(int y = 0; y < testBoardSize; y++){
            for(int x = 0; x < testBoardSize; x++){
                Stone stone = board.buttons[x][y];
                isEnabled = stone.getEnabled();
                isStonePlaced = stone.isStonePlaced();
            }
        }

        Assertions.assertTrue(isEnabled, "Not all stones enabled at startup");
        Assertions.assertFalse(isStonePlaced, "Stone placed at startup");
    }

    @Test
    public void testPlaceStone(){
        board.firstTurn(false);

        Stone stone = board.buttons[0][0];
        stone.doClick();

        Assertions.assertTrue(stone.isStonePlaced(), "Stone not placed");
        Assertions.assertFalse(board.blackTurn, "Turn not changed on stone placement");
        Assertions.assertEquals(board.textField.getText(), "White's Turn", "Turn status text not updated");
    }

    @Test
    public void testPlaceStoneDisabled(){
        board.firstTurn(true);;
        Stone stone = board.buttons[0][0];
        stone.doClick();

        Assertions.assertFalse(stone.isStonePlaced(), "Stone placed on disabled intersection");
    }

    @Test
    public void testCaptureStone(){
        board.firstTurn(false);

        Stone stone = board.buttons[2][2];
        stone.doClick();

        board.buttons[1][2].placeStone(false);
        board.buttons[2][1].placeStone(false);
        board.buttons[2][3].placeStone(false);

        board.buttons[3][2].doClick();

        Assertions.assertFalse(stone.isStonePlaced(), "Surrounded stone not captured");
        Assertions.assertEquals(board.blackCaptured, 1, "Stone not recorded as captured");
        Assertions.assertEquals(board.blackCapturedText.getText(), "Black Stones Captured: 1", "Captured text not updated");
    }

    @Test
    public void testCaptureMultipleStones() throws InterruptedException {
        board.firstTurn(false);

        Thread.sleep(1000);

        board.buttons[2][2].placeStone(false);
        board.buttons[2][3].placeStone(false);
        board.buttons[3][2].placeStone(false);
        board.buttons[3][3].placeStone(false);

        board.buttons[1][2].placeStone(true);
        board.buttons[1][3].placeStone(true);
        board.buttons[2][1].placeStone(true);
        board.buttons[2][4].placeStone(true);
        board.buttons[3][1].placeStone(true);
        board.buttons[3][4].placeStone(true);
        board.buttons[4][2].placeStone(true);

        board.buttons[4][3].doClick();

        boolean isPlacedStone = false;

        for(int y = 2; y < 4; y++){
            for(int x = 2; y < 4; y++){
                isPlacedStone = board.buttons[x][y].isStonePlaced();
            }
        }

        Assertions.assertFalse(isPlacedStone, "Stones not captured");
        Assertions.assertEquals(board.whiteCaptured, 4, "Stones not recorded as captured");
        Assertions.assertEquals(board.whiteCapturedText.getText(), "White Stones Captured: 4", "Captured text not updated");
    }


}
