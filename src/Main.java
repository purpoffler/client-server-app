
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        // Инструкция
        System.out.println("Привет!");
        System.out.println("С помощью меня ты можешь отправить данные различных типов:");
        System.out.println("""
                    CONSOLE – сервер выведет данные в консоль;
                    PLAIN – сервер сохранит данные в обычный файл свободного формата;
                    JSON – сервер сохранить данные в файл с типом .json и json-форматированием
                """);
        System.out.println("Введи формат, в котором ты хочешь отправить данные, например, CONSOLE");

        //Создаем пул из 3 потоков
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 1 слой, принимаем данные на вход
        CompletableFuture<ArrayList<String>> futureData = CompletableFuture.supplyAsync(() ->{
                    DataType dp = new DataType();
            try {
                return dp.call();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка в вводе данных", e);
            }
        }, executor);

        // 2 слой, упаковываем их в пакет
        CompletableFuture<String> futurePackanging = futureData.thenApplyAsync(dataType -> {
            Packanging pc = new Packanging(dataType);
            try {
                return pc.call();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при сборе пакета", e);
            }
        }, executor);

        // Асинхронная отладка
        futurePackanging.thenAcceptAsync(packet -> {
            System.out.println("Упакованный пакет: " + packet);
        }, executor);

        // 3 слой, через сокет отправляем данные серверу



        // Ждем всю цепочку
        futurePackanging.join(); // НЕ обязательно, если тебе не нужно ждать окончания

        // убиваем executor
        executor.shutdown();


    }
}
