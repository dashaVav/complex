package dev;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Call {
    private int targetFloor;
    private int currentFloor;
    private int id;
    private Direction direction;
    private int  active = -1;

    public Call(int currentFloor, int targetFloor, int id) {
        this.targetFloor = targetFloor;
        this.currentFloor = currentFloor;
        this.id = id;

        if (targetFloor < currentFloor) direction = Direction.DOWN;
        else direction = Direction.UP;
    }
}
