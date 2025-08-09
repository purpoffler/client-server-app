package DataLevel;

import java.util.Arrays;
import java.util.Scanner;

public class ChooseType {
    private static Scanner sc = new Scanner(System.in);

    static String chooseDataType() {
        while (sc.hasNext()) {
            String str = sc.nextLine();
            // С помощью стрима проходим по всем допустимым значениям типа данных и находим совпадения
            if (Arrays.stream(ExpectedDataType.values()).anyMatch(x -> x.name().equalsIgnoreCase(str))) {
                System.out.println(String.format("Окей, тогда собираем %s", str));
                return str;
            } else {
                System.out.println("Упс, неправильный формат данных");
            }
        }
        return "Упс, больше данных нет(";
    }
}
