package IoT;


import BuildingSimulator.Cell;

public abstract class Thing extends Cell {
    protected PhysicalScope scope;
    protected boolean penetratesWalls;

    public Thing(PhysicalScope scope){
        this.scope = scope;
        penetratesWalls = false;
        setType(Cell.THING);
    }

    public Thing(PhysicalScope scope, boolean penetratesWalls){
        this.scope = scope;
        this.penetratesWalls = penetratesWalls;
    }

    public PhysicalScope getScope(){ return scope;}
    public boolean getPenetration(){ return penetratesWalls; }
}
