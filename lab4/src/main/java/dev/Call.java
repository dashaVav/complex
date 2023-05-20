package dev;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, который представляет заявку к лифту
 */
@Getter
@Setter
public class Call {
    private int targetFloor;
    private int currentFloor;
    private int id;
    private Direction direction;

    public Call(int currentFloor, int targetFloor, int id) {
        this.targetFloor = targetFloor;
        this.currentFloor = currentFloor;
        this.id = id;

        if (targetFloor < currentFloor) direction = Direction.DOWN;
        else direction = Direction.UP;
    }
}
