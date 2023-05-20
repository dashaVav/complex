package dev;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, который представляет лифт
 */
@Setter
@Getter
public class Elevator {
    private int id;
    private Direction directionCurrent;
    private Direction directionTarget;
    private int currentFloor;
    private int targetFloor;
    private Map<Integer, Integer> passengersInElevator = new HashMap<>();
    private Map<Integer, List<Call>> queuePassengers = new HashMap<>();

    public Elevator(int id) {
        this.id = id;
        targetFloor = 0;
        currentFloor = 1;
        directionCurrent = Direction.STOP;
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
