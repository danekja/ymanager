package org.danekja.ymanager.business;

import org.danekja.ymanager.ws.rest.exceptions.RESTFullException;

public interface FileService {

    FileImportResult parseXLSFile(String fileName, byte[] bytes) throws RESTFullException;

    FileExportResult createPDFFile() throws RESTFullException;

}
