package IoT;

public abstract class PhysicalScope {

    public PhysicalScope(){

    }

    public abstract boolean contains(int x, int y);

    public abstract int getX();

    public abstract int getY();

    public abstract int getR();


}
