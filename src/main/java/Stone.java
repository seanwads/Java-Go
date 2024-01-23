import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.FontMetrics;
import java.util.ArrayList;

public class Stone extends JButton{

    private boolean mouseOver = false;
    private boolean mousePressed = false;
    private boolean enabled = true;
    private boolean blackTurn = true;
    private boolean stonePlaced = false;
    private boolean checked = false;

    private ArrayList<Stone> neighbours;
    private final int xPos;
    private final int yPos;

    public Stone(String text, int x, int y){
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        xPos = x;
        yPos = y;
    }

    private int getDiameter(){
        return Math.min(getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize(){
        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
        int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
        return new Dimension(minDiameter, minDiameter);
    }

    @Override
    public boolean contains(int x, int y){
        int radius = getDiameter()/2;
        return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
    }

    @Override
    public void paintComponent(Graphics g){

        if(!stonePlaced){

        }
        int diameter = getDiameter();
        int radius = diameter/2;

        //circle fill
        if(enabled){
            if(mousePressed){
                if(this.blackTurn){
                    g.setColor(Color.BLACK);
                }
                else{
                    g.setColor(Color.WHITE);
                }
            }
            else if(mouseOver){
                g.setColor(Color.GREEN);
            }
            else{
                g.setColor(new Color(255, 215, 175));
            }

            g.fillOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);
        }
        else{
            g.setColor(new Color(150, 150, 150));
            g.fillOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);
        }


        //circle outline
        if(mouseOver && enabled && !mousePressed){
            g.setColor(Color.GREEN);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.drawOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);


        g.setColor(Color.BLACK);
        g.setFont(getFont());
        FontMetrics metrics = g.getFontMetrics(getFont());
        int stringWidth = metrics.stringWidth(getText());
        int stringHeight = metrics.getHeight();
        g.drawString(getText(), getWidth()/2 - stringWidth/2, getHeight()/2 + stringHeight/4);
    }

    public void placeStone(boolean state){
        blackTurn = state;
        mousePressed = true;
        stonePlaced = true;
        repaint();
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    public void setBlackTurn(boolean isBlack){
        this.blackTurn = isBlack;
    }

    public boolean isBlackTurn() {
        return blackTurn;
    }

    public boolean isStonePlaced() {
        return stonePlaced;
    }

    public void setStonePlaced(boolean stonePlaced) {
        this.stonePlaced = stonePlaced;
    }

    public ArrayList<Stone> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Stone> neighbours) {
        this.neighbours = neighbours;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
