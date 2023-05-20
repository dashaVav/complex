package dev;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Elevator {
    private int id;
    private Direction directionNow;
    private Direction directionAfter;
    private int currentFloor = 1;
    private int targetFloor;
    private int maxCapacity;
    private Map<Integer, Integer> passengers = new HashMap<>();
    private Map<Integer, List<Call>> queuePassengers = new HashMap<>();


    public Elevator(int id, int maxCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        targetFloor = 0;
        directionNow = Direction.STOP;
    }

    public void incrementFloor(){
        currentFloor ++;
    }

    public void decrementFloor() {
        currentFloor --;
    }

    public int getCurrentFloor(){
        synchronized (this) {
            return currentFloor;
        }
    }

    public int getTargetFloor(){
        synchronized (this) {
            return targetFloor;
        }
    }
}
