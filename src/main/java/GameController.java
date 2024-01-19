import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameController implements ActionListener{

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel titlePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton sBoardButton = new JButton("9x9");
    JButton mBoardButton = new JButton("13x13");
    JButton lBoardButton = new JButton("19x19");

    CircleButton[] buttons;
    boolean blackTurn = true;

    public GameController(){
        //frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.getContentPane().setBackground(new Color(200, 255, 200));
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
        sBoardButton.setBounds(0, 0, 50, 30);
        sBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstTurn(9);
            }
        });
        buttonPanel.add(sBoardButton);

        mBoardButton.setBounds(0, 0, 50, 30);
        mBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstTurn(13);
            }
        });
        buttonPanel.add(mBoardButton);

        lBoardButton.setBounds(0, 0, 50, 30);
        lBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstTurn(19);
            }
        });
        buttonPanel.add(lBoardButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void firstTurn(int boardSize){
        buttonPanel.remove(sBoardButton);
        buttonPanel.remove(mBoardButton);
        buttonPanel.remove(lBoardButton);

        buttons = new CircleButton[boardSize*boardSize];
        buttonPanel.setLayout(new GridLayout(boardSize, boardSize));

        for(JButton button : buttons){
            button = new CircleButton("");
            buttonPanel.add(button);
            button.setFocusable(false);
            button.addActionListener(this);
        }

        frame.add(buttonPanel);

        textField.setText("Black's turn");
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    public void checkCapture(){

    }

    public void whiteWins(){

    }

    public void blackWins(){

    }
}
