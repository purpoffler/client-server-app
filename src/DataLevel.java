import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DataLevel implements Runnable {
    private final BlockingQueue<Message> dataQueue;

    public DataLevel(BlockingQueue<Message> dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        ConsoleHelper.getInstruction();
        while (true) {
            ExpectedDataType dataType = chooseDataType();
            String data = inputData();
            try {
                dataQueue.put(new Message(data, dataType));
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Ошибка при добавлении в очередь на уровне DataLevel");
            }
        }
    }

    private String inputData() {
        while (true) {
            String data = ConsoleHelper.readString();
            if (data.length() < 200) {
                return data;
            }
            ConsoleHelper.writeMessage("Упс, количество символов больше 200");
        }
    }

    private ExpectedDataType chooseDataType() {
        while (true) {
            String dataType = ConsoleHelper.readString();
            // С помощью стрима проходим по всем допустимым значениям типа данных и находим совпадения
            if (Arrays.stream(ExpectedDataType.values()).anyMatch(x -> x.name().equalsIgnoreCase(dataType))) {
                ConsoleHelper.writeMessage(String.format("Окей, тогда собираем %s", dataType));
                return ExpectedDataType.valueOf(dataType.toUpperCase());
            } else {
                ConsoleHelper.writeMessage("Упс, неправильный формат данных");
            }
        }
    }

}
