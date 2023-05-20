package dev;

import java.util.*;

/**
 * Класс представляет здание, в котором двигаются лифты
 */
public class Building {
    int maxFloor;
    int minFloor;
    List<Call> queuePassengers = new ArrayList<>();
    final Elevator elevator1 = new Elevator(1);
    final Elevator elevator2 = new Elevator(2);

    public Building(int maxFloor) {
        minFloor = 1;
        this.maxFloor = maxFloor;
    }

    /**
     * Добавляет заявки в очередь, в которой они будут обрабатываться
     * @param call заявка к лифту
     */
    public synchronized void addPassengersInQueue(Call call) {
        queuePassengers.add(call);
        System.out.printf("Пассажир №%d ждет на этаже №%d%n", call.getId(), call.getCurrentFloor());
    }

    /**
     * Изменение положения лифтов
     */
    public void run() {
        step(elevator1, elevator2);
        step(elevator2, elevator1);
    }

    /**
     * Движение лифта
     * @param elevator лифт, который будет двигаться
     * @param another лифт, который не будет двигаться
     */
    private synchronized void step(Elevator elevator, Elevator another){
//        System.out.printf("Лифт №%d сейчас находится на этаже №%d %n",elevator.getId(),  elevator.getCurrentFloor());

        Out(elevator);
        updateQueue(elevator);
        InElevator(elevator);

        if (elevator.getDirectionCurrent() == another.getDirectionCurrent() && another.getDirectionCurrent() == Direction.STOP
                && !queuePassengers.isEmpty()) {
            if (Math.abs(another.getCurrentFloor() - queuePassengers.get(0).getCurrentFloor())
                    < Math.abs(elevator.getCurrentFloor() - queuePassengers.get(0).getCurrentFloor())) {
                return;
            }
        }

        // если лифт свободен(никуда не едет), то он берет новую заявку из очереди
        if (elevator.getDirectionCurrent() == Direction.STOP && !queuePassengers.isEmpty()) {
            Call call = queuePassengers.remove(0);
            elevator.setTargetFloor(call.getCurrentFloor());

            if (!elevator.getQueuePassengers().containsKey(call.getCurrentFloor())) {
                elevator.getQueuePassengers().put(call.getCurrentFloor(), new ArrayList<>());
            }
            elevator.getQueuePassengers().get(call.getCurrentFloor()).add(call);

            if (call.getCurrentFloor() > elevator.getCurrentFloor()) elevator.setDirectionCurrent(Direction.UP);
            else elevator.setDirectionCurrent(Direction.DOWN);

            elevator.setDirectionTarget(call.getDirection());

            updateQueue(elevator);
            InElevator(elevator);
        }

        // изменение этаж
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (elevator.getDirectionCurrent() == Direction.UP && elevator.getCurrentFloor() + 1 <= maxFloor) {
            elevator.incrementFloor();
        }
        if (elevator.getDirectionCurrent() == Direction.DOWN && elevator.getCurrentFloor() - 1 >= minFloor) {
            elevator.decrementFloor();
        }

    }

    /**
     * Добавляет подходящие заявки в очередь к лифту
     * @param elevator лифт, который сейчас двигается
     */
    private void updateQueue(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (elevator.getDirectionCurrent() == elevator.getDirectionTarget()) {
            Iterator<Call> iterator = queuePassengers.iterator();
            while (iterator.hasNext()) {
                Call call = iterator.next();
                if (call.getDirection() == elevator.getDirectionCurrent()) {
                    if (call.getCurrentFloor() <= elevator.getCurrentFloor() && elevator.getDirectionCurrent() == Direction.DOWN
                            || call.getCurrentFloor() >= elevator.getCurrentFloor() && elevator.getDirectionCurrent() == Direction.UP) {

                        if (!elevator.getQueuePassengers().containsKey(call.getCurrentFloor()))  {
                            elevator.getQueuePassengers().put(call.getCurrentFloor(), new ArrayList<>());
                        }

                        elevator.getQueuePassengers().get(call.getCurrentFloor()).add(call);
                        iterator.remove();

                    }
                }
            }
        }
    }

    /**
     * Пассажиры, которые находятся в очереди данного лифта и на currentFloor, заходят в лифт
     * @param elevator лифт, который сейчас двигается
     */
    private void InElevator(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        if (!elevator.getQueuePassengers().containsKey(elevator.getCurrentFloor())) {
            elevator.getQueuePassengers().put(elevator.getCurrentFloor(), new ArrayList<>());
        }

        Iterator<Call> iterator = elevator.getQueuePassengers().get(elevator.getCurrentFloor()).iterator();
        while (iterator.hasNext()) {
            Call call = iterator.next();
            elevator.getPassengersInElevator().put(call.getId(), call.getTargetFloor());
            elevator.setDirectionCurrent(call.getDirection());

            if (elevator.getTargetFloor() < call.getTargetFloor() && elevator.getDirectionCurrent() == Direction.UP) {
                elevator.setTargetFloor(call.getTargetFloor());
            }
            if (elevator.getTargetFloor() > call.getTargetFloor() && elevator.getDirectionCurrent() == Direction.DOWN) {
                elevator.setTargetFloor(call.getTargetFloor());
            }

            System.out.printf("Пассажир №%d зашел в лифт №%d на этаже №%d и едет на этаж №%d%n",
                    call.getId(), elevator.getId(),  call.getCurrentFloor(), call.getTargetFloor());
            iterator.remove();
        }
        if (elevator.getQueuePassengers().get(elevator.getCurrentFloor()).isEmpty()) {
            elevator.getQueuePassengers().remove(elevator.getCurrentFloor());
        }
    }

    /**
     * Пассажиры, которые находятся в лифте и доехали до своего этажа, выходят из лифта
     * @param elevator лифт, который сейчас двигается
     */
    private void Out(Elevator elevator) {
        if (elevator.getDirectionCurrent() == Direction.STOP) return;

        List<Integer> keysForRemove = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : elevator.getPassengersInElevator().entrySet()) {
            if (entry.getValue() == elevator.getCurrentFloor()) {
                System.out.printf("Пассажир №%d вышел из лифта №%d на этаже №%d%n",
                        entry.getKey(),elevator.getId(), entry.getValue());
                keysForRemove.add(entry.getKey());
            }
        }

        keysForRemove.forEach(i -> elevator.getPassengersInElevator().remove(i));

        if (elevator.getPassengersInElevator().isEmpty() && elevator.getQueuePassengers().isEmpty()) {
            elevator.setDirectionCurrent(Direction.STOP);
        }
    }
}
