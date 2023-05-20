package dev;

import java.util.*;

public class Building {
    int maxFloor;
    int minFloor;

    List<Call> queue = new ArrayList<>();
    Map<Integer, List<Call>> active = new HashMap<>();

    final Elevator elevator1 = new Elevator(1, 10);
    final Elevator elevator2 = new Elevator(2, 10);


//    public Building(){
////        addPassengersInQueue(new Call(1, 3, 1));
////        addPassengersInQueue(new Call(1, 4, 3));
//        addPassengersInQueue(new Call(7, 3, 2));
//        addPassengersInQueue(new Call(1, 6, 3));
//
//        while (true) {
//            run();
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

    public void run() {
        step(elevator1);
        step(elevator2);
    }


    public synchronized void step(Elevator elevator){
        System.out.printf("Лифт %d на %d этаже%n",elevator.getId(),  elevator.getCurrentFloor());
//        System.out.println(elevator.getTargetFloor());
//        elevator.getQueuePassengers().forEach((p, c) -> System.out.print(p + " "));
//        System.out.println();
        Out(elevator);
        updateQueue(elevator);
        InElevator(elevator);

        if (elevator.getDirectionNow() == Direction.STOP && !queue.isEmpty()) {
            Call call = queue.remove(0);
            elevator.setTargetFloor(call.getCurrentFloor());
            if (!elevator.getQueuePassengers().containsKey(call.getCurrentFloor())) elevator.getQueuePassengers().put(call.getCurrentFloor(), new ArrayList<>());
            elevator.getQueuePassengers().get(call.getCurrentFloor()).add(call);
            if (call.getCurrentFloor() > elevator.getCurrentFloor()) elevator.setDirectionNow(Direction.UP);
            else elevator.setDirectionNow(Direction.DOWN);
            elevator.setDirectionAfter(call.getDirection());
            updateQueue(elevator);
            InElevator(elevator);
        }

        if (elevator.getDirectionNow() == Direction.STOP) return;

        if (elevator.getDirectionNow() == Direction.UP) {
            elevator.incrementFloor();
        }
        else {
            elevator.decrementFloor();
        }
    }

    private void updateQueue(Elevator elevator) {
        if (elevator.getDirectionNow() != Direction.STOP) return;
        if (elevator.getDirectionNow() == elevator.getDirectionAfter()) {
            Iterator<Call> iterator = queue.iterator();
            while (iterator.hasNext()) {
                Call call = iterator.next();
                if (call.getDirection() == elevator.getDirectionNow()) {
                    if (call.getCurrentFloor() <= elevator.getCurrentFloor() && elevator.getDirectionNow() == Direction.DOWN
                            || call.getCurrentFloor() >= elevator.getCurrentFloor() && elevator.getDirectionNow() == Direction.UP) {
                        if (!elevator.getQueuePassengers().containsKey(call.getCurrentFloor())) elevator.getQueuePassengers().put(call.getCurrentFloor(), new ArrayList<>());
                        elevator.getQueuePassengers().get(call.getCurrentFloor()).add(call);
//                        elevator.getQueuePassengers().getOrDefault(call.getCurrentFloor(), new ArrayList<>()).add(call);
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void InElevator(Elevator elevator) {
        if (elevator.getDirectionNow() == Direction.STOP) return;
        if (!elevator.getQueuePassengers().containsKey(elevator.getCurrentFloor())) elevator.getQueuePassengers().put(elevator.getCurrentFloor(), new ArrayList<>());


        Iterator<Call> iterator = elevator.getQueuePassengers().get(elevator.getCurrentFloor()).iterator();
        while (iterator.hasNext()) {
            Call call = iterator.next();
            elevator.getPassengers().put(call.getId(), call.getTargetFloor());
            elevator.setDirectionNow(call.getDirection());
            if (elevator.getTargetFloor() < call.getTargetFloor() && elevator.getDirectionNow() == Direction.UP) elevator.setTargetFloor(call.getTargetFloor());
            if (elevator.getTargetFloor() > call.getTargetFloor() && elevator.getDirectionNow() == Direction.DOWN) elevator.setTargetFloor(call.getTargetFloor());

            System.out.printf("Пассажир %d зашел в %d лифт на этаже %d и едет на этаж %d%n",
                    call.getId(), elevator.getId(),  call.getCurrentFloor(), call.getTargetFloor());
            iterator.remove();
        }
        if (elevator.getQueuePassengers().get(elevator.getCurrentFloor()).isEmpty()) elevator.getQueuePassengers().remove(elevator.getCurrentFloor());
    }

    private void Out(Elevator elevator) {
        if (elevator.getDirectionNow() == Direction.STOP) return;

        List<Integer> keysForRemove = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : elevator.getPassengers().entrySet()) {
            if (entry.getValue() == elevator.getCurrentFloor()) {
                System.out.printf("Пассажир %d вышел %d на этаже %d%n", entry.getKey(),elevator.getId(),  entry.getValue());
                keysForRemove.add(entry.getKey());
            }
        }
        keysForRemove.forEach(i -> elevator.getPassengers().remove(i));

        if (elevator.getPassengers().isEmpty() && elevator.getQueuePassengers().isEmpty()) elevator.setDirectionNow(Direction.STOP);
    }
}
