
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Инструкция
        System.out.println("Привет!");
        System.out.println("С помощью меня ты можешь отправить данные различных типов:");
        System.out.println("""
                    CONSOLE – сервер выведет данные в консоль;
                    PLAIN – сервер сохранит данные в обычный файл свободного формата;
                    JSON – сервер сохранить данные в файл с типом .json и json-форматированием
                """);
        System.out.println("Введи формат, в котором ты хочешь отправить данные, например, CONSOLE");

        // Определение типа
        DataType dp = new DataType();
        Future<ArrayList<String>> future1 = executor.submit(dp);
        ArrayList<String> dataType = future1.get();

        // Отладка
        System.out.println(dataType);


        // Сборка пакета
        Packanging pc = new Packanging(dataType);
        Future<String> future2 = executor.submit(pc);
        String packet = future2.get();

        // Отладка
        System.out.println(packet);



        // убиваем executor
        executor.shutdown();
    }
}
