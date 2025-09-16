import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable {
    private final BlockingQueue<String> packetQueue;
    private Socket clientSocket;

    public Client(BlockingQueue<String> packetQueue) {
        this.packetQueue = packetQueue;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("localhost", 4004);
            Connection connection = new Connection(clientSocket);
            while (true) {
                String packet = packetQueue.take();// Получаем данные из очереди dataQueue
                ConsoleHelper.writeMessage(packet);//Нужно будет перенаправить в файл
                connection.send(packet + "\n"); // отправляем сообщение на сервер
                String serverWord = connection.receive(); // ждём, что скажет сервер
                ConsoleHelper.writeMessage(serverWord); // получив - выводим на экран
                if (serverWord.equalsIgnoreCase("Лел, а данных-то я не получил!")) {
                    connection.send(packet + "\n"); // повторно отправляем сообщение на сервер
                }
                Thread.sleep(500);
                ConsoleHelper.getInstruction();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Неправильный хост", e);
        } catch (IOException e) {
            throw new RuntimeException("Потеря соединения", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
