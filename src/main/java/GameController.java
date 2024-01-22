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
    int boardSize = 9;
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
                boardSize = 19;
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

        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
               setNeighbours(x, y);
            }
        }



        frame.add(buttonPanel);

        blackTurn = true;
        firstTurn = true;
        updateTextField();

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void setNeighbours(int x, int y){
        ArrayList<CircleButton> neighbours = new ArrayList<>();

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(!( i==j ) && !(i == -1 && j == 1) && !(i == 1 && j == -1)){
                    try{
                        neighbours.add(buttons[x+i][y+j]);
                    } catch(Exception e){
                        //button not added to list
                    }
                }
            }
        }

        buttons[x][y].setNeighbours(neighbours);

    }

    private void updateTextField() {
        if(blackTurn){
            textField.setText("Black's Turn");
        } else {
            textField.setText("White's Turn");
        }
    }

    public void checkCapture(int x, int y){
        if(buttons[x-1][y].isStonePlaced() && buttons[x][y-1].isStonePlaced() && buttons[x][y+1].isStonePlaced() && buttons[x+1][y].isStonePlaced()){
            System.out.println("capture");
        }
    }

    public void whiteWins(){

    }

    public void blackWins(){

    }

}
