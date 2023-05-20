package dev;

import java.util.*;

public class Building {
    int maxFloor;
    int minFloor;

    List<Call> queue = new ArrayList<>();
    Map<Integer, List<Call>> active = new HashMap<>();

//
//    public Building(){
////        addPassengersInQueue(new Call(1, 3, 1));
////        addPassengersInQueue(new Call(1, 4, 3));
//        addPassengersInQueue(new Call(6, 1, 2));
//        addPassengersInQueue(new Call(10, 6, 3));
//
//        Elevator elevator = new Elevator(10);
//        while ((!queue.isEmpty() || !elevator.getPassengers().isEmpty()) && elevator.getCurrentFloor() < 20) {
//            step(elevator);
//
////            if (elevator.getCurrentFloor() == 4) addPassengersInQueue(new Call(3, 1, 3));
//        }
//
//    }

    public synchronized void addPassengersInQueue(Call call) {
        if (!active.containsKey(call.getCurrentFloor())) {
            active.put(call.getCurrentFloor(), new ArrayList<>());
        }
        active.get(call.getCurrentFloor()).add(call);
        queue.add(call);
        System.out.printf("Пассажир %d ждет на этаже %d чтобы поехать на %d%n", call.getId(), call.getCurrentFloor(), call.getTargetFloor());
    }

    public synchronized void step(Elevator elevator){
        System.out.printf("Лифт %d на %d этаже%n",elevator.getId(),  elevator.getCurrentFloor());
        Out(elevator);
        In(elevator);

//        if (elevator.getPassengers().isEmpty()) {
//            System.out.println("Лифт пуст на этаже " + elevator.getCurrentFloor());
//        }

        if (queue.isEmpty() && elevator.getPassengers().isEmpty()){
            System.out.println("Очередь пассажиров пуста!");
            elevator.setActive(false);
            return;
        }

        if (!elevator.isActive()) {
            for (Call value : queue) {
                if (!value.isActive()) {
                    value.setActive(true);
                    Call call = value;
                    elevator.setTargetFloor(call.getCurrentFloor());
                    if (call.getCurrentFloor() > elevator.getCurrentFloor()) elevator.setDirection(Direction.UP);
                    else elevator.setDirection(Direction.DOWN);
                    elevator.setActive(true);
//                    System.out.println("done");
                    break;
                }
            }
        }

        if (!elevator.isActive()) return;

        if (elevator.getDirection() == Direction.UP) {
            elevator.incrementFloor();
        }
        else {
            elevator.decrementFloor();
        }
    }

    private void In(Elevator elevator){
        int currentFloor = elevator.getCurrentFloor();

        if (!active.containsKey(currentFloor) || active.get(currentFloor).isEmpty()) return;

        List<Call> callsForRemove = new ArrayList<>();

        active.get(currentFloor).forEach(pas -> {
            if (pas.getDirection() == elevator.getDirection() || elevator.getTargetFloor() == currentFloor) {
                elevator.setActive(true);
                elevator.setDirection(pas.getDirection());
                elevator.getPassengers().put(pas.getId(), pas.getTargetFloor());

                if (pas.getTargetFloor() > elevator.getTargetFloor() || elevator.getDirection() == Direction.UP) {
                    elevator.setTargetFloor(pas.getTargetFloor());
                }
                if (pas.getTargetFloor() < elevator.getTargetFloor() || elevator.getDirection() == Direction.DOWN) {
                    elevator.setTargetFloor(pas.getTargetFloor());
                }

                System.out.printf("Пассажир %d зашел в %d лифт на этаже %d и едет на этаж %d%n",
                        pas.getId(),elevator.getId(),  pas.getCurrentFloor(), pas.getTargetFloor());
                callsForRemove.add(pas);
            }
        });
        queue.removeAll(callsForRemove);
        active.get(currentFloor).removeAll(callsForRemove);
    }

    private void Out(Elevator elevator) {
        List<Integer> keysForRemove = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : elevator.getPassengers().entrySet()) {
            if (entry.getValue() == elevator.getCurrentFloor()) {
                System.out.printf("Пассажир %d вышел %d на этаже %d%n", entry.getKey(),elevator.getId(),  entry.getValue());
                keysForRemove.add(entry.getKey());
            }
        }
        keysForRemove.forEach(i -> elevator.getPassengers().remove(i));
//        if (elevator.getPassengers().isEmpty()) elevator.setActive(false);
    }
}
