package IoT;

import BuildingSimulator.Cell;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Light extends Thing {

    public Light(int x, int y, int r){

        super(new CircularScope(x, y, r));
        setType(Cell.LIGHT);
    }

    public Light(int x, int y, int r, double angle){

        super(new ConicalScope(x, y, r, angle));
        setType(Cell.LIGHT);
    }

    public void drawSelf(Graphics2D g2d, Cell[][] buildingCells, int buildingWidth, int buildingHeight){

        // first draw the scope
        g2d.setColor(new Color(255, 255, 224));
        int minX = scope.getX() - scope.getR();
        int minY = scope.getY() - scope.getR();
        int maxX = scope.getX() + scope.getR();
        int maxY = scope.getY() + scope.getR();
        // color cells in this box IF: (1) they are within R of (x,y) AND they are within "line of sight" of (x,y)
        for(int x = minX; x<=maxX; x++){
            for(int y = minY; y<maxY; y++) {
                if (isWithinR(x, y) && isLineOfSight(x, y, buildingCells, buildingWidth, buildingHeight) && x < buildingWidth && y < buildingHeight) {
                    g2d.fillRect(x, y, 1, 1);
                }
            }
        }

        //then draw the light (so its on top)
        g2d.setColor(Color.yellow);
        // drawing the middle of the light as 10x10 just so its visible.
        g2d.fill(new Ellipse2D.Double(scope.getX()-5, scope.getY()-5, 10, 10));


        //int upperLeftX = (int)scope.getX()-scope.getR();
        //int upperLeftY = (int)scope.getY()-scope.getR();
        //g2d.fill(new Ellipse2D.Double(upperLeftX, upperLeftY, 2*scope.getR(), 2*scope.getR()));

    }

    private boolean isWithinR(int x, int y){
        return (Point2D.distance(x,y, scope.getX(), scope.getY()) <= scope.getR());
    }

    private boolean isLineOfSight(int x, int y, Cell[][] cells, int buildingWidth, int buildingHeight){
        // this is basically an implementation of Bresenham's algorithm; I'm basing this on source code from here:
        // http://www.sanfoundry.com/java-program-bresenham-line-algorithm/

        int dx = Math.abs(scope.getX() - x);
        int dy = Math.abs(scope.getY() - y);

        int sx = x < scope.getX() ? 1 : -1;
        int sy = y < scope.getY() ? 1 : -1;

        int err = dx-dy;
        int e2;

        while (true) {

            // x,y is one point on the line
            // we now just need to check to see if any of this location in cells is a wall
            if(x < 0 || y < 0 || x >= buildingWidth || y >= buildingHeight){
                break;
            }

            if (x == scope.getX() && y == scope.getY()) {
                break;
            }
            if(cells[x][y].getType() == Cell.WALL){
                return false;
            }

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x = x + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y = y + sy;
            }
        }

        // at this point, we went through all of the points connecting the light to our location x,y; if none were walls, return true
        return true;
    }

    public String toString(){
        String toReturn = "";
        toReturn += "(";
        toReturn += scope.getX();
        toReturn += " ";
        toReturn += scope.getY();
        toReturn += ")";
        return toReturn;
    }

}
