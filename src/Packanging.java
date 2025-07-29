import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.zip.CRC32;

public class Packanging implements Callable<String> {
    static Scanner sc = new Scanner(System.in);
    String dataType;
    String data;
    StringBuilder sb = new StringBuilder();

    public Packanging(ArrayList<String> dataType){
        this.dataType = dataType.get(0);
        this.data = dataType.get(1);
    }

    // Собираем пакет
    @Override
    public String call() throws Exception {

        sb.append(packSignature());
        sb.append(dataLength());
        sb.append(dataType());
        sb.append(getData());
        sb.append(CRC32());

        return  sb.toString();
    }
    // Сигнатура длинной 8 симоволов
    private String packSignature(){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i=0; i < 8; i++){
            char symbol = (char) (33 + rand.nextInt(94));
            sb.append(symbol);
        }
        return sb.toString();
    }
    // Длинна данных - 3 символа
    private String dataLength() {
        return String.format("%03d", data.length());
    }

    // Тип данных - 7 символов
    private String dataType() {
        return String.format("%-7s", dataType);
    }

    // Сами данные
    private String getData() {
        return data;
    }

    // Считаем контрольную сумму от всего пакета - длина 8 символов
    private String CRC32() {
        CRC32 crc32 = new CRC32();
        crc32.update(sb.toString().getBytes());
        long value = crc32.getValue();
        return String.format("%08X", value);
    }

}
