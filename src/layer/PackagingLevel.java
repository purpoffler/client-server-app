package layer;

import dto.Message;
import layer.enums.ExpectedDataType;
import utlis.ConsoleHelper;

import java.util.concurrent.BlockingQueue;
import java.util.zip.CRC32;

public class PackagingLevel implements Runnable {
    private static final String signature = "zWj`Jjkg";
    private final BlockingQueue<Message> dataQueue;
    private final BlockingQueue<String> packetQueue;


    public PackagingLevel(BlockingQueue<Message> dataQueue, BlockingQueue<String> packetQueue) {
        this.dataQueue = dataQueue;
        this.packetQueue = packetQueue;
    }

    // Собираем пакет
    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                Message message = dataQueue.take(); // Получаем данные из очереди dataQueue
                ExpectedDataType dataType = message.getDataType();
                String data = message.getData();
                // Очищаем билдер
                sb.setLength(0);
                // Собираем пакет
                sb.append(signature);
                sb.append(dataLength(data));
                sb.append(dataType(dataType));
                sb.append(data);
                sb.append(crc32(data));
                // Отравляем пакет в очередь packetQueue
                String packet = sb.toString();
                packetQueue.put(packet);
                //ConsoleHelper.writeMessage(sb.toString()); // v file nado
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Поток прерван во время работы с очередью");
            }
        }
    }

    // Длинна данных - 3 символа
    public String dataLength(String data) {
        return String.format("%03d", data.length());
    }

    // Тип данных - 7 символов
    public String dataType(ExpectedDataType dataType) {
        return String.format("%-7s", dataType);
    }

    // Считаем контрольную сумму от всего пакета - длина 8 символов
    public String crc32(String data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        long value = crc32.getValue();
        return String.format("%08X", value);
    }

}
