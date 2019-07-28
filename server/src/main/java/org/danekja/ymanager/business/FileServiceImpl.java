package org.danekja.ymanager.business;

import org.danekja.ymanager.ws.rest.RESTFullException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.danekja.ymanager.business.file.excel.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Component
public class FileServiceImpl implements FileService {

    private class Content {
        double overtime = 0;
        List<Date> vacations = new ArrayList<>();
    }

    @Override
    public FileImportResult parseXLSFile(String fileName, byte[] bytes) throws RESTFullException {

        try {
            Map<String, Content> contentMap = getContentInfo(ExcelParser.parseXLSX(new ByteArrayInputStream(bytes)));

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

        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDRectangle.A4);
        document.addPage(page1);

        try {
            PDPageContentStream cos = new PDPageContentStream(document, page1);

            cos.beginText();
            cos.setFont(PDType1Font.HELVETICA, 12);
            cos.newLineAtOffset(100, 10);
            cos.showText("Test");
            cos.endText();

            cos.close();

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            document.save(output);
            document.close();

            return new FileExportResult("export.pdf", output.toByteArray());

        } catch (IOException e) {
            throw new RESTFullException("", "");
        }
    }
}
