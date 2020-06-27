package org.danekja.ymanager.business;

import java.io.IOException;

public interface FileService {

    FileImportResult parseXLSFile(String fileName, byte[] bytes) throws IOException;

    FileExportResult createPDFFile() throws IOException;

}
