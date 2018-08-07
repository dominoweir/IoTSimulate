package SwingContent;

import BuildingSimulator.*;
import IoT.*;
import SampleHomes.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
/*
 *  Goals:
 *  interactivity
 *  visualization
 *  coverage
 *  parameterization
 */

public class Simulator extends JPanel implements ActionListener {
    private int width;
    private int height;
    private Building building;
    Cell[][] cells;
    private Timer timer;
    private final int delay = 10;
    private Actor actor;

    public Simulator(int picked, int minLights, int maxLights){

        super();

        switch(picked){
            case 0:
                width = 600;
                height = 800;
                building = new RandomHouse(width, height);
                break;
            case 1:
                width = 950;
                height = 700;
                building = new RandomOffice(width, height, minLights, maxLights);
                break;
            case 2:
                width = 300;
                height = 400;
                building = new Example1(width, height);
                break;
            case 3:
                width = 600;
                height = 800;
                building = new Example2(width, height);
                break;
            default:
                width = 600;
                height = 800;
                building = new RandomHouse(width, height);
                break;
        }

        cells = building.getCells();

        BufferedWriter out = null;
        try {
            FileWriter fileStream = new FileWriter("out.txt", false);
            out = new BufferedWriter(fileStream);
            for(int i = 0; i < width; i+=5){
                for(int j = 0; j < height; j+=5){
                    Thing closestLight = new ClosestLocationQuery(building).makeQuery(Cell.LIGHT, i, j);
                    Thing coveringLight = new PhysicalScopeQuery(building).makeQuery(Cell.LIGHT, i, j);
                    if(coveringLight != null && closestLight != null){
                        out.write(i + ", " + j + ", ");
                        out.write(closestLight.toString() + ", ");
                        out.write(coveringLight.toString() +  "\n");
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            out.close();
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // construct the panel that contains the building drawing
        this.setPreferredSize(new Dimension(width, height));
        this.setLocation(10, 10);
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);

        // construct the frame that holds both panels
        JFrame frame = new JFrame("IoT Simulator");
        frame.getContentPane().add(this);
        frame.setPreferredSize(new Dimension(width + 50, height + 50));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // paint the actual house on the frame
        actor = new Actor(10, 10, this.getHeight(), this.getWidth());
        repaint();

        // these lines are needed for actor movement
        addKeyListener(new TAdapter());
        timer = new Timer(delay, this);
        timer.start();

    }


    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintHouse(g);
        paintActor(g);
    }

    private void paintActor(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(5.0f));

        if (actor != null) {
            g2d.drawImage(actor.getImage(), actor.getTopLeftX(), actor.getTopLeftY(), null);
        }
    }

    private void paintHouse(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(5.0f));

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Cell currentCell = cells[i][j];

                if(currentCell.getType() == Cell.WALL){
                    g2d.setColor(Color.black);
                    g2d.fillRect(i, j, 1, 1);
                }
                else if(currentCell.getType()==Cell.ROOM){
                    if(currentCell.isLit()){
                        g2d.setColor(Color.YELLOW);
                        g2d.fillRect(i, j, 1, 1);
                    }
                }
                else {
                    // instance of Thing
                    if (currentCell. getType() == Cell.LIGHT){
                        Light l = (Light) currentCell;
                        l.drawSelf(g2d, cells, width, height);

                    }
                }
            }
        }
    }

    private void performQuery(){
        Thing closestLight = new ClosestLocationQuery(building).makeQuery(Cell.LIGHT, actor.getTopLeftX(), actor.getTopLeftY());
        System.out.println(actor.getTopLeftX().toString() + " " + actor.getTopLeftY().toString() + " " + closestLight);

        Thing coveringLight = new PhysicalScopeQuery(building).makeQuery(Cell.LIGHT, actor.getTopLeftX(), actor.getTopLeftY());
        System.out.println(actor.getTopLeftX().toString() + " " + actor.getTopLeftY().toString() + " " + coveringLight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actor.move();
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                actor.keyReleased(e);
            }
            else if(key == KeyEvent.VK_ENTER){
                performQuery();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                actor.keyPressed(e);
            }
        }
    }
}
