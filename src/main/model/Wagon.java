package main.model;

import java.util.UUID;

public class Wagon {
    protected final UUID id;
    protected final WagonType wagonType;

    public Wagon(WagonType wagonType) {
        this.id = UUID.randomUUID();
        this.wagonType = wagonType;
    }

    public Wagon(Wagon wagon) {
        this.id = wagon.getWagonId();
        this.wagonType = new WagonType(wagon.getWagonTypeId(), wagon.getComfortType(), wagon.getSeatsNumber(), wagon.getWeightPerPerson());
    }

    public Wagon(UUID id, WagonType type) {
        this.id = id;
        this.wagonType = type;
    }

    public UUID getWagonId() {
        return id;
    }

    public UUID getWagonTypeId() {
        return wagonType.getId();
    }

    public String getWagonTypeName() {
        return wagonType.getComfortTypeName();
    }

    public ComfortTypes getComfortType() {
        return wagonType.getComfortType();
    }

    public int getSeatsNumber() {
        return wagonType.getSeatsNumber();
    }

    public float getWeightPerPerson() {
        return wagonType.getMaxThingsWeightPerPerson();
    }

    @Override
    public String toString() {
        return getWagonTypeName() + " wagon - " + getSeatsNumber() + " seats - " + getWeightPerPerson() + "kg/person";
    }

    public String toFile() {
        return id + " " + wagonType.toFile() + "\n";
    }

    public Wagon copy() {
        return new Wagon(this);
    }

    public String toSqlValues() {
        return "('" + id + "', '" + getWagonTypeName() + "', '" + getSeatsNumber() + "', " + getWeightPerPerson() + ")";
    }
}
