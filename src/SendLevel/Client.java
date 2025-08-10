package SendLevel;

import DataLevel.Instruction;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable {
    private final BlockingQueue<String> packetQueue;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public Client(BlockingQueue<String> packetQueue) {
        this.packetQueue = packetQueue;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket("localhost", 4004);

            // Читаем сообщения от сервера
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Пишем серверу
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (true) {
                try {
                    String packet = packetQueue.take();// Получаем данные из очереди dataQueue
                    System.out.println(packet);
                    out.write(packet + "\n"); // отправляем сообщение на сервер
                    out.flush();
                    String serverWord = in.readLine(); // ждём, что скажет сервер
                    System.out.println(serverWord);
                    Thread.sleep(500);// получив - выводим на экран
                    Instruction.getInstruction();
                    if (serverWord.equalsIgnoreCase("Лел, а данных-то я не получил!")) {
                        out.write(packet + "\n"); // повторно отправляем сообщение на сервер
                        out.flush();
                    }
                    //System.out.println(Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Неправильный хост", e);
        } catch (IOException e) {
            throw new RuntimeException("Потеря соединения", e);
        }
    }
}
