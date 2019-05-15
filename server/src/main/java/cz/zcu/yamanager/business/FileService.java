package cz.zcu.yamanager.business;

import cz.zcu.yamanager.ws.rest.RESTFullException;

public interface FileService {

    FileImportResult parseXLSFile(String fileName, byte[] bytes) throws RESTFullException;

    FileExportResult createPDFFile() throws RESTFullException;

}
