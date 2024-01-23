import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Board {
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JLabel textField = new JLabel();
    JLabel whiteCapturedText = new JLabel();
    JLabel blackCapturedText = new JLabel();
    JLabel emptyLabel = new JLabel();
    JButton sBoardButton = new JButton("9x9");
    JButton mBoardButton = new JButton("13x13");
    JButton lBoardButton = new JButton("19x19");
    JButton yesButton = new JButton("Yes");
    JButton noButton = new JButton("No");
    JLabel handicapText = new JLabel("Set Handicap?");
    JButton startButton;

    Stone[][] buttons;
    boolean blackTurn;
    int boardSize = 9;
    int blackCaptured = 0;
    int whiteCaptured = 0;

    boolean isHandicapTurn = false;
    public Board(){
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
        buttonPanel.setBackground(new Color(230, 230, 230));
        sBoardButton.setBounds(0, 0, 50, 30);
        sBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardSize = 9;
                setHandicap();
            }
        });
        buttonPanel.add(sBoardButton);

        mBoardButton.setBounds(0, 0, 50, 30);
        mBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardSize = 13;
                setHandicap();
            }
        });
        buttonPanel.add(mBoardButton);

        lBoardButton.setBounds(0, 0, 50, 30);
        lBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardSize = 19;
                setHandicap();
            }
        });
        buttonPanel.add(lBoardButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

        blackCapturedText.setForeground(new Color(30, 0, 0));
        blackCapturedText.setFont(new Font(Font.MONOSPACED,Font.BOLD, 24));
        blackCapturedText.setHorizontalAlignment(JLabel.LEFT);
        blackCapturedText.setText("Black Stones Captured: 0");
        blackCapturedText.setOpaque(true);

        whiteCapturedText.setForeground(new Color(30, 0, 0));
        whiteCapturedText.setFont(new Font(Font.MONOSPACED,Font.BOLD, 24));
        whiteCapturedText.setHorizontalAlignment(JLabel.RIGHT);
        whiteCapturedText.setText("White Stones Captured: 0");
        whiteCapturedText.setOpaque(true);

        emptyLabel.setText("        ");
        blackCapturedText.setFont(new Font(Font.MONOSPACED,Font.BOLD, 24));

        infoPanel.setBounds(0, 0, 800, 100);
        infoPanel.add(blackCapturedText, BorderLayout.WEST);
        infoPanel.add(emptyLabel);
        infoPanel.add(whiteCapturedText, BorderLayout.EAST);
        frame.add(infoPanel, BorderLayout.SOUTH);
        frame.repaint();
    }

    private void setHandicap(){
        buttonPanel.remove(sBoardButton);
        buttonPanel.remove(mBoardButton);
        buttonPanel.remove(lBoardButton);

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstTurn(true);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstTurn(false);
            }
        });

        buttonPanel.add(handicapText);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    public void firstTurn(boolean isHandicap){
        buttonPanel.remove(handicapText);
        buttonPanel.remove(yesButton);
        buttonPanel.remove(noButton);

        buttons = new Stone[boardSize][boardSize];
        buttonPanel.setLayout(new GridLayout(boardSize, boardSize));

        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                buttons[x][y] = new Stone("", x, y);
                buttonPanel.add(buttons[x][y]);
                buttons[x][y].setBlackTurn(true);
                buttons[x][y].setFocusable(false);
                int finalX = x;
                int finalY = y;
                buttons[x][y].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!buttons[finalX][finalY].isStonePlaced() && buttons[finalX][finalY].getEnabled()){
                            buttons[finalX][finalY].placeStone(blackTurn);

                            if(!isHandicapTurn){
                                checkChain(finalX, finalY);
                                checkCapture();
                                blackTurn = !blackTurn;
                                updateTextField();
                            }
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
        updateTextField();

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

        if(isHandicap){
            placeHandicap();
        }
    }

    private void placeHandicap() {
        isHandicapTurn = true;
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                buttons[x][y].setEnabled(false);
            }
        }

        switch (boardSize) {
            case 9:
                for(int y = 2; y < 7; y += 4){
                    for(int x = 2; x < 7; x += 4){
                        buttons[x][y].setEnabled(true);
                    }
                }
                break;
            case 13:
                for(int y = 3; y < 10; y += 3){
                    for(int x = 3; x < 10; x += 3){
                        buttons[x][y].setEnabled(true);
                    }
                }
                break;
            case 19:
                for(int y = 3; y < 16; y += 6){
                    for(int x = 3; x < 16; x += 6){
                        buttons[x][y].setEnabled(true);
                    }
                }
                break;
        }

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isHandicapTurn = false;
                blackTurn = !blackTurn;
                updateTextField();
                for(int y = 0; y < boardSize; y++){
                    for(int x = 0; x < boardSize; x++){
                        buttons[x][y].setEnabled(true);
                    }
                }
                titlePanel.remove(startButton);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
            }
        });

        titlePanel.add(startButton, BorderLayout.PAGE_END);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void setNeighbours(int x, int y){
        ArrayList<Stone> neighbours = new ArrayList<>();

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(!( i==j ) && !(i == -1 && j == 1) && !(i == 1 && j == -1)){
                    try{
                        neighbours.add(buttons[x+i][y+j]);
                    } catch(Exception e){
                        continue;
                    }
                }
            }
        }
        buttons[x][y].setNeighbours(neighbours);
    }

    ArrayList<Stone> stonesToCheck = new ArrayList<>();
    private void checkChain(int x, int y){
        Stone stone = buttons[x][y];

        for(Stone neighbour : stone.getNeighbours()){
            if(neighbour.isStonePlaced() && neighbour.isBlackTurn() != blackTurn && !stonesToCheck.contains(neighbour)){
                stonesToCheck.add(neighbour);
                checkChain(neighbour.getxPos(), neighbour.getyPos());
            }
        }
    }

   private void checkCapture() {
        if(!stonesToCheck.isEmpty()){
            for(Stone stone : stonesToCheck){
                for(Stone neighbour : stone.getNeighbours()){
                    if(!neighbour.isStonePlaced()){
                        System.out.println("not capture");
                        stonesToCheck.clear();
                        return;
                    }
                }
            }

            for(Stone stone: stonesToCheck){
                stone.setStonePlaced(false);
                stone.setMousePressed(false);
                stone.repaint();
            }

            if(blackTurn){
                whiteCaptured += stonesToCheck.size();
            }
            else{
                blackCaptured += stonesToCheck.size();
            }

            System.out.println(whiteCaptured + " white");
            System.out.println(blackCaptured + " black");
            stonesToCheck.clear();
        }
   }

    private void updateTextField() {
        if(blackTurn){
            textField.setText("Black's Turn");
        } else {
            textField.setText("White's Turn");
        }

        whiteCapturedText.setText("White Stones Captured: " + whiteCaptured);
        blackCapturedText.setText("Black Stones Captured: " + blackCaptured);
    }
}
