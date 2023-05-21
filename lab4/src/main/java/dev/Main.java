package dev;

import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    /**
     * Запрашивает необходимую информацию.
     * Инициализирует здание, к котором будут двигаться лифты.
     * Запускает поток заявок к лифту.
     * Запускает 2 потока - движения лифтов.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("# Нумерация этажей в доме начинается с 1");

        System.out.print("# Введите кол-во этажей в доме:");
        int maxFloor = scanner.nextInt();

        System.out.println("# Лифт двигается со скоростью один этаж в секунду");
        System.out.print("# Интервал заявок в секундах:");
        int interval = scanner.nextInt();

        Building building = new Building(maxFloor);

        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        // заявки
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
                building.addPassengersInQueue(new Call(currentFloor, targetFloor, idPassenger));
            }}, 0, interval, TimeUnit.SECONDS);

        // первый лифт
        service.scheduleAtFixedRate(building::run1, 0, 1, TimeUnit.SECONDS);

        // второй лифт
        service.scheduleAtFixedRate(building::run2, 0, 1, TimeUnit.SECONDS);
    }

}