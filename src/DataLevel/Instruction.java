package DataLevel;

public class Instruction {
    // Инструкция
    public static void getInstruction() {
        System.out.println();
        System.out.println("Привет!");
        System.out.println("С помощью меня ты можешь отправить данные различных типов:");
        System.out.println("""
                    CONSOLE – сервер выведет данные в консоль;
                    PLAIN – сервер сохранит данные в обычный файл свободного формата;
                    JSON – сервер сохранить данные в файл с типом .json и json-форматированием;
                """);
        System.out.println("Введи формат, в котором ты хочешь отправить данные, например, CONSOLE");
    }
}
