package cz.zcu.yamanager.business.mock;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import cz.zcu.yamanager.business.FileExportResult;
import cz.zcu.yamanager.business.FileImportResult;
import cz.zcu.yamanager.business.FileService;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileServiceMock implements FileService {

    private List<String> content = new ArrayList<>();

    @Override
    public FileImportResult parseXLSFile(String fileName, byte[] bytes) throws RESTFullException {
        try {
            HSSFWorkbook wb = new HSSFWorkbook(new ByteArrayInputStream(bytes));

            HSSFSheet sheet=wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {
                row=(HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    cell = (HSSFCell) cells.next();

                    content.add(cell.getStringCellValue());
                }
            }
            return new FileImportResult(fileName, (long) bytes.length);
        } catch (IOException e) {
            throw new RESTFullException("error", "");
        }
    }

    @Override
    public FileExportResult createPDFFile() throws RESTFullException {

        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDRectangle.A4);
        PDRectangle rect = page1.getMediaBox();
        document.addPage(page1);

        try {
            PDPageContentStream cos = new PDPageContentStream(document, page1);

            int line = 0;

            for (String word : content) {
                cos.beginText();
                cos.setFont(PDType1Font.HELVETICA, 12);
                cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
                cos.showText(word);
                cos.endText();
            }

            cos.close();

            ByteOutputStream output = new ByteOutputStream();

            document.save(output);
            document.close();

            return new FileExportResult("export.pdf", output.getBytes());

        } catch (IOException ioex) {
            throw new RESTFullException("", "");
        }
    }
}
