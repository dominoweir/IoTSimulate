package BuildingSimulator;

public class Cell {

    public static String ROOM = "room";
    public static String LIGHT = "light";
    public static String WALL = "wall";
    public static String THING = "thing";

    boolean lit;
    String type;

    public Cell(){
        this.lit = false;
        this.type = ROOM;
    }

    public Cell(String type){
        this.lit = false;
        this.type = type;
    }

    public boolean isLit() { return lit; }

    public void setLit(boolean lit) { this.lit = lit; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
