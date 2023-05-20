package dev;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Elevator {
    private int id;
    private Direction direction;
    private int currentFloor = 1;
    private int targetFloor;
    private int maxCapacity;
    private Map<Integer, Integer> passengers = new HashMap<>();
    private boolean isActive;

    public Elevator(int id, int maxCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        isActive = false;
        targetFloor = 0;
        direction = Direction.UP;
    }

    public void incrementFloor(){
        currentFloor ++;
    }

    public void decrementFloor() {
        currentFloor --;
    }
}
