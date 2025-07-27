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

    @Override
    public String call() throws Exception {

        sb.append(packSignature());
        sb.append(dataLenght());
        sb.append(dataType());
        sb.append(getData());
        sb.append(CRC32());

        return  sb.toString();
    }

    private String packSignature(){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i=0; i < 8; i++){
            char symbol = (char) (33 + rand.nextInt(94));
            sb.append(symbol);
        }
        return sb.toString();
    }

    private int dataLenght() {
        return data.length();
    }

    private String dataType() {
        return dataType;
    }

    private String getData() {
        return data;
    }


    private String CRC32() {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        long value = crc32.getValue();
        return String.valueOf(value);
    }

}
