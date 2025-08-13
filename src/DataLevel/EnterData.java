package DataLevel;

import java.util.Scanner;

public class EnterData {
    private static Scanner sc = new Scanner(System.in);

    static String inputData() {
        if (sc.hasNext()) {
            String data = sc.nextLine();
            while (true) {
                if (data.length() < 200) {
                    return data;
                }
                System.out.println("Упс, количество символов больше 200");
            }
        }
        return "Данных нет(";
    }

}
