package DataLevel;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class DataLevel implements Runnable {
    private ArrayList<String> inputData = new ArrayList<>();
    private final BlockingQueue<ArrayList<String>> dataQueue;
    private Scanner sc = new Scanner(System.in);

    public DataLevel(BlockingQueue<ArrayList<String>> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        while (true) {
            Instruction.getInstruction();
            // добавляем в лист выбранный тип данных
            inputData.add(ChooseType.chooseDataType());
            System.out.println("Введите данные (не более 200 символов):");

            // добавляем в лист сами данные
            inputData.add(InputData.inputData());

            try {
                dataQueue.put(inputData);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван во время put()");
                //   e.printStackTrace();
            }
        }
    }

}
