package IoT;


public class ConicalScope extends PhysicalScope {
    private int x;
    private int y;
    private int radius;
    private double angle;

    public ConicalScope(int x, int y, int radius, double angle){
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.angle = angle;
    }

    public boolean contains(int x, int y){
        return false;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return radius;
    }

    public double getAngle() {
        return angle;
    }
}
