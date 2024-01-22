import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameController {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton sBoardButton = new JButton("9x9");
    JButton mBoardButton = new JButton("13x13");
    JButton lBoardButton = new JButton("19x19");

    CircleButton[][] buttons;
    boolean blackTurn;
    private boolean firstTurn;
    int boardSize;
    int blackCaptured = 0;
    int whiteCaptured = 0;
    ArrayList<CircleButton> stonesToCapture = new ArrayList<>();

    public GameController(){
        //frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        //text field setup
        textField.setBackground(new Color(255, 255, 255));
        textField.setForeground(new Color(30, 0, 0));
        textField.setFont(new Font(Font.MONOSPACED,Font.BOLD, 24));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Select board size to start");
        textField.setOpaque(true);

        //title panel setup
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 200);
        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);

        //menu button panel setup
        buttonPanel.setBounds(0, 200, 800, 200);
        buttonPanel.setBackground(Color.WHITE);
        sBoardButton.setBounds(0, 0, 50, 30);
        sBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardSize = 9;
                firstTurn();
            }
        });
        buttonPanel.add(sBoardButton);

        mBoardButton.setBounds(0, 0, 50, 30);
        mBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardSize = 13;
                firstTurn();
            }
        });
        buttonPanel.add(mBoardButton);

        lBoardButton.setBounds(0, 0, 50, 30);
        lBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardSize = 13;
                firstTurn();
            }
        });
        buttonPanel.add(lBoardButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

    }

    public void firstTurn(){
        buttonPanel.remove(sBoardButton);
        buttonPanel.remove(mBoardButton);
        buttonPanel.remove(lBoardButton);

        buttons = new CircleButton[boardSize][boardSize];
        buttonPanel.setLayout(new GridLayout(boardSize, boardSize));

        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                buttons[x][y] = new CircleButton("", x, y);
                buttonPanel.add(buttons[x][y]);
                setNeighbours(x, y);
                buttons[x][y].setBlackTurn(true);
                buttons[x][y].setFocusable(false);
                int finalX = x;
                int finalY = y;
                buttons[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!buttons[finalX][finalY].isStonePlaced()){
                            buttons[finalX][finalY].placeStone(blackTurn);
                            checkCapture(finalX, finalY);

                            blackTurn = !blackTurn;
                            updateTextField();
                        }
                    }
                });
            }
        }
        frame.add(buttonPanel);

        blackTurn = true;
        firstTurn = true;
        updateTextField();

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

//    private void setNeighbours(int x, int y) {
//        CircleButton[] neighbours;
//
//        if((x == 0 && y == 0) || (x == boardSize - 1 && y == boardSize - 1 ) || (x == 0 && y == boardSize - 1) || (x == boardSize - 1 && y == 0)){
//            neighbours = new CircleButton[2];
//            neighbours[0] = buttons[x == 0 ? x+1 : x-1][y];
//            neighbours[1] = buttons[x][y == 0 ? y+1 : y-1];
//        }
//        else if(x == 0 || x == boardSize - 1 || y == 0 || y == boardSize - 1){
//            neighbours = new CircleButton[3];
//            if(x == 0 || x == boardSize - 1){
//                neighbours[0] = buttons[x][y+1];
//                neighbours[1] = buttons[x == 0 ? x : x-1][y];
//                neighbours[2] = buttons[x][y-1];
//            } else {
//                neighbours[0] = buttons[x-1][y];
//                neighbours[1] = buttons[x][y == 0 ? y+1 : y-1];
//                neighbours[2] = buttons[x+1][y];
//            }
//        }
//        else{
//            neighbours = new CircleButton[4];
//            neighbours[0] = buttons[x-1][y];
//            neighbours[1] = buttons[x][y+1];
//            neighbours[2] = buttons[x][y-1];
//            neighbours[3] = buttons[x+1][y];
//        }
//        buttons[x][y].setNeighbours(neighbours);
//    }

    private void updateTextField() {
        if(blackTurn){
            textField.setText("Black's Turn");
        } else {
            textField.setText("White's Turn");
        }
    }

    public void checkCapture(int x, int y){

    }

    public void whiteWins(){

    }

    public void blackWins(){

    }

}
