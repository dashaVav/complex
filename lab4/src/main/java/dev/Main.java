package dev;

import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Нумерация этажей в дома начинается с 1");
//        System.out.println("Введите кол-во этажей в доме:");
//        int maxFloor = scanner.nextInt();
        int maxFloor = 10;

//        System.out.println("Максимальная вместимость лифта:");
//        int maxCapacity = scanner.nextInt();

//        System.out.println("Интервал заявок в миллисекундах:");
//        int interval = scanner.nextInt();

        int interval = 2000;

        Building building = new Building();
        System.out.println("+");
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

        service.scheduleAtFixedRate(new Runnable() {
            int idPassenger = 0;
            @Override
            public void run() {
                int currentFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                int targetFloor;
                do {
                    targetFloor = ThreadLocalRandom.current().nextInt(1, maxFloor + 1);
                } while (currentFloor == targetFloor);
                idPassenger ++;
                building.addPassengersInQueue(new Call(currentFloor, targetFloor,idPassenger));
            }}, 0, 5, TimeUnit.SECONDS);

        service.scheduleAtFixedRate(new Runnable() {
            Elevator elevator1 = new Elevator(10);
            @Override
            public void run() {
                building.step(elevator1);
            }}, 0, 1, TimeUnit.SECONDS);
    }


        //поток для лифтов

}