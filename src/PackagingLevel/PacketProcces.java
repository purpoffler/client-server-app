package PackagingLevel;

import java.util.zip.CRC32;

public class PacketProcces {
    private String data;
    private String dataType;

    public PacketProcces(String data, String dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    // Длинна данных - 3 символа
    public String dataLength() {
        return String.format("%03d", data.length());
    }

    // Тип данных - 7 символов
    public String dataType() {
        return String.format("%-7s", dataType);
    }

    // Считаем контрольную сумму от всего пакета - длина 8 символов
    public String crc32() {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        long value = crc32.getValue();
        return String.format("%08X", value);
    }
}
