import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class DataType implements Callable<ArrayList<String>> {
    static Scanner sc = new Scanner(System.in);
    ArrayList<String> inputData = new ArrayList<>();

    @Override
    public ArrayList<String> call() throws Exception {
        chooseDataType();
        System.out.println("Введите данные (не более 200 символов):");
        inputData();
        return inputData;
    }

    private void chooseDataType(){
        while (true) {
            if (sc.hasNext()) {
                String str = sc.nextLine();
                if (str.equalsIgnoreCase("CONSOLE") || str.equalsIgnoreCase("PLAIN") || str.equalsIgnoreCase("JSON")) {
                    System.out.println(String.format("Окей, тогда собираем %s", str));
                    inputData.add(str);
                    return;
                }
            }
            System.out.println("Упс, неправильный формат данных");
        }
    }

    private void inputData(){
        if (sc.hasNext()) {
            String data = sc.nextLine();
            while (true) {
                if (data.length() < 200) {
                    System.out.println("кайф!");
                    inputData.add(data);
                    break;
                }
                System.out.println("Упс, количество символов больше 200");
            }
        }
    }


}
