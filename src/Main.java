import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Очереди для связи между потоками
        BlockingQueue<Message> dataQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> packetQueue = new LinkedBlockingQueue<>();

        // Создаем потоки и передаем в них очереди, с которыми они должны работать
        Thread dataThread = new Thread(new DataLevel(dataQueue));
        Thread packagingThread = new Thread(new PackagingLevel(dataQueue, packetQueue));
        Thread sendThread = new Thread(new Client(packetQueue));

        // Запускаем потоки
        dataThread.start();
        packagingThread.start();
        sendThread.start();

    }
}
