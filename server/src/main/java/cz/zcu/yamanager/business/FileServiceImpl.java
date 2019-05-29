package cz.zcu.yamanager.business;

import cz.zcu.yamanager.business.file.excel.*;
import cz.zcu.yamanager.ws.rest.RESTFullException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static cz.zcu.yamanager.business.file.excel.ExcelParser.parseXLSX;

public class FileServiceImpl implements FileService {

    private class Content {
        double overtime = 0;
        List<Date> vacations = new ArrayList<>();
    }

    @Override
    public FileImportResult parseXLSFile(String fileName, byte[] bytes) throws RESTFullException {

        try {
            Map<String, Content> contentMap = getContentInfo(parseXLSX(new ByteArrayInputStream(bytes)));

            return new FileImportResult(fileName, (long) contentMap.size());

        } catch (IOException e) {
            throw new RESTFullException("", "");
        }
    }

    private Map<String, Content> getContentInfo(Map<String, SheetContent[]> sheetForName) {

        Map<String, Content> result = new HashMap<>();

        for (Map.Entry<String, SheetContent[]> item : sheetForName.entrySet()) {
            String name = item.getKey();
            SheetAttendanceContent content = (SheetAttendanceContent) item.getValue()[SheetType.ATTENDANCE.ordinal()];

            if (!result.containsKey(name)) {
                result.put(name, new Content());
            }

            for (AttendanceRecord record : content.getRecords()) {
                if (record.getType().equals(RecordType.VACATION)) {
                    result.get(name).vacations.add(record.getDate());
                }
            }
            result.get(name).overtime = content.getOvertimeHours();
        }

        return result;
    }


    @Override
    public FileExportResult createPDFFile() throws RESTFullException {
        return new FileExportResult("unknown", new byte[0]);
    }
}
