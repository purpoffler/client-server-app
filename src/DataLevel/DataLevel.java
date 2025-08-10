package DataLevel;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DataLevel implements Runnable {
    private LinkedHashMap<String, String> inputData = new LinkedHashMap<>();
    private final BlockingQueue<ArrayList<String>> dataQueue;

    public DataLevel(BlockingQueue<ArrayList<String>> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        while (true) {
            // добавляем в мапу тип данных
            inputData.put("dataType", ChooseType.chooseDataType());
            System.out.println("Введите данные (не более 200 символов):");

            // добавляем в мапу сами данные
            inputData.put("data", EnterData.inputData());

            try {
                //System.out.println(inputData.values());
                dataQueue.put(new ArrayList<>(inputData.values()));
            } catch (InterruptedException e) {
                System.out.println("Поток прерван во время put()");
                //   e.printStackTrace();
            }
        }
    }

}
