package cz.zcu.yamanager.business.mock;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.datatable.DataTable;
import cz.zcu.yamanager.business.FileExportResult;
import cz.zcu.yamanager.business.FileImportResult;
import cz.zcu.yamanager.business.FileService;
import cz.zcu.yamanager.ws.rest.RESTFullException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class FileServiceMock implements FileService {

    private List<String> content = new ArrayList<>();

    @Override
    public FileImportResult parseXLSFile(String fileName, byte[] bytes) throws RESTFullException {
        try {

            XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes));

            XSSFSheet sheet=wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {
                row=(XSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();

                    content.add(cell.getRawValue());
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

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            document.save(output);
            document.close();

            return new FileExportResult("export.pdf", pdfTable());

        } catch (IOException ioex) {
            throw new RESTFullException("", "");
        }
    }

    private byte[] pdfTable() throws IOException {
        PDPage myPage = new PDPage(PDRectangle.A4);
        PDDocument mainDocument = new PDDocument();
        PDPageContentStream contentStream = new PDPageContentStream(mainDocument, myPage);



        //Dummy Table
        float margin = 50;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = myPage.getMediaBox().getHeight() - (2 * margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = myPage.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
        float yPosition = 550;

        List<List> data = new ArrayList();
        data.add(new ArrayList<>(
                Arrays.asList("Column One", "Column Two", "Column Three", "Column Four", "Column Five")));
        for (int i = 1; i <= 100; i++) {
            data.add(new ArrayList<>(
                    Arrays.asList("Row " + i + " Col One", "Row " + i + " Col Two", "Row " + i + " Col Three", "Row " + i + " Col Four", "Row " + i + " Col Five")));
        }
        BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, true, true);
        DataTable t = new DataTable(dataTable, myPage);
        t.addListToTable(data, DataTable.HASHEADER);
        dataTable.draw();

        contentStream.close();
        mainDocument.addPage(myPage);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mainDocument.save(output);

        mainDocument.close();
        contentStream.close();

        return output.toByteArray();
    }

    private byte[] pdfSimple() throws IOException {

        PDPage myPage = new PDPage(PDRectangle.A4);
        PDDocument mainDocument = new PDDocument();
        PDPageContentStream contentStream = new PDPageContentStream(mainDocument, myPage);



        //Dummy Table
        float margin = 50;
        // starting y position is whole page height subtracted by top and bottom margin
        float yStartNewPage = myPage.getMediaBox().getHeight() - (2 * margin);
        // we want table across whole page width (subtracted by left and right margin ofcourse)
        float tableWidth = myPage.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 70;
        // y position is your coordinate of top left corner of the table
        float yPosition = 550;

        BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, true, drawContent);


        Row<PDPage> headerRow = table.createRow(15f);
        Cell<PDPage> cell = headerRow.createCell(100, "Header");
        table.addHeaderRow(headerRow);


        Row<PDPage> row = table.createRow(12);
        cell = row.createCell(30, "Data 1");
        cell = row.createCell(70, "Some value");

        table.draw();


        contentStream.close();
        mainDocument.addPage(myPage);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mainDocument.save(output);

        mainDocument.close();
        contentStream.close();

        return output.toByteArray();
    }


}
