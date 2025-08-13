package DataLevel;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DataLevel implements Runnable {
    private HashMap<String, String> inputData = new HashMap<>();
    private final BlockingQueue<HashMap<String, String>> dataQueue;

    public DataLevel(BlockingQueue<HashMap<String, String>> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        Instruction.getInstruction();
        while (true) {
            // добавляем в мапу тип данных
            inputData.put("dataType", ChooseType.chooseDataType());
            System.out.println("Введите данные (не более 200 символов):");
            // добавляем в мапу сами данные
            inputData.put("data", EnterData.inputData());

            try {
                //System.out.println(inputData.values());
                dataQueue.put(inputData);
            } catch (InterruptedException e) {
                System.out.println("Поток прерван во время put()");
                //   e.printStackTrace();
            }
        }
    }

}
