package cz.zcu.yamanager.business;

public class FileExportResult {

    private final String name;
    private final byte[] bytes;

    public FileExportResult(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
