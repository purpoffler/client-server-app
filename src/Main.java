
import DataLevel.DataLevel;
import PackagingLevel.Packaging;
import SendLevel.Client;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // Очереди для связи между потоками
        BlockingQueue<ArrayList<String>> dataQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> packetQueue = new LinkedBlockingQueue<>();

        // Создаем потоки и передаем в них очереди, с которыми они должны работать
        Thread dataThread = new Thread(new DataLevel(dataQueue));
        Thread packagingThread = new Thread(new Packaging(dataQueue, packetQueue));
        Thread sendThread = new Thread(new Client(packetQueue));

        // Запускаем потоки
        dataThread.start();
        packagingThread.start();
        sendThread.start();

    }
}
