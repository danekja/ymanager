package org.danekja.ymanager.business;

public class FileImportResult {

    private final String name;
    private final Long size;

    public FileImportResult(String name, Long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }
}
