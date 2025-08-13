package PackagingLevel;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class PackagingLevel implements Runnable {
    private static final String signature = "zWj`Jjkg";
    private final BlockingQueue<HashMap<String, String>> dataQueue;
    private final BlockingQueue<String> packetQueue;


    public PackagingLevel(BlockingQueue<HashMap<String, String>> dataQueue, BlockingQueue<String> packetQueue) {
        this.dataQueue = dataQueue;
        this.packetQueue = packetQueue;
    }

    // Собираем пакет
    @Override
    public void run() {
        while (true) {
            try {
                StringBuilder sb = new StringBuilder();
                HashMap<String, String> map = dataQueue.take(); // Получаем данные из очереди dataQueue
                String dataType = map.get("dataType"); // Получаем тип данных
                String data = map.get("data"); // Получаем данные
                // Собираем пакет
                PacketProcces pp = new PacketProcces(data, dataType);
                // Очищаем билдер
                sb.setLength(0);
                // Собираем пакет
                sb.append(signature);
                sb.append(pp.dataLength());
                sb.append(pp.dataType());
                sb.append(data);
                sb.append(pp.crc32());
                // Отравляем пакет в очередь packetQueue
                packetQueue.put(sb.toString());
                //System.out.println(Thread.currentThread().getState());
            } catch (InterruptedException e) {
                System.out.println("Поток прерван во время работы с очередью");
            }
        }
    }
}
