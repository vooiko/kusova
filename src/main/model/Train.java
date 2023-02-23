package main.model;

import java.util.ArrayList;
import java.util.UUID;

public class Train {
    private final UUID id;
    private final String name;
    private final String code;
    private ArrayList<AssignedWagon> assignedWagons;

    public Train(String name, String code) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.code = code;
        this.assignedWagons = null;
    }

    public Train(UUID id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.assignedWagons = null;
    }
    
    public void assignWagons(ArrayList<AssignedWagon> assignedWagons){
        this.assignedWagons = assignedWagons;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", code: " + code;
    }

    public String toSqlValues() {
        return "('" + id + "', '" + name + "', '" + code + "')";
    }

    public String toFile() {
        return id + " " + name + " " + code + "\n";
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<AssignedWagon> getWagons() {
        return assignedWagons;
    }

    public void setWagons(ArrayList<AssignedWagon> wagons) {
        this.assignedWagons = wagons;
    }

    public Train copy() {
        var temp = new Train(this.id, this.name, this.code);
        ArrayList<AssignedWagon> tempArray = new ArrayList<>();
        assignedWagons.forEach(assignedWagon -> tempArray.add(assignedWagon.copy()));
        temp.setWagons(tempArray);
        return temp;
    }
}
