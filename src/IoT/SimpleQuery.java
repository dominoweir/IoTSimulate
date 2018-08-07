package IoT;

import BuildingSimulator.Building;

public class SimpleQuery extends Query{

    public SimpleQuery(Building building){
        super(building);
    }

    @Override
    public Thing makeQuery(String type, int x, int y) {
        return null; // TODO: BAD!
    }
}
