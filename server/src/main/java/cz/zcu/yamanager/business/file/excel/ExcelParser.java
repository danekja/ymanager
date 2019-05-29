package cz.zcu.yamanager.business.file.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ExcelParser {

    private static class SheetInfo {
        String employee;
        SheetParser parser;
    }

    public static Map<String, SheetContent[]> parseXLSX(InputStream inputStream) throws IOException {

        Map<String, SheetContent[]> sheets = new HashMap<>();

        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        Iterator<Sheet> sheetIterator = wb.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();

            Optional<SheetInfo> sheetTitleOptional = parseSheetTitle(sheet);

            if (!sheetTitleOptional.isPresent()) {
                continue;
            }

            SheetInfo sheetInfo = sheetTitleOptional.get();

            SheetContent sheetContent = sheetInfo.parser.parseSheet(sheet);

            if (sheetContent == null) continue;

            if (!sheets.containsKey(sheetInfo.employee)) {
                sheets.put(sheetInfo.employee, new SheetContent[SheetType.values().length]);
            }

            sheets.get(sheetInfo.employee)[sheetInfo.parser.getType().ordinal()] = sheetContent;
        }

        return sheets;
    }

    private static Optional<SheetInfo> parseSheetTitle(Sheet sheet) {

        String[] sheetName = sheet.getSheetName().split("-");

        if (sheetName.length != 2) {
            return Optional.empty();
        }

        SheetInfo result = new SheetInfo();
        result.employee = sheetName[1].trim();
        switch (sheetName[0].trim().toLowerCase()) {
            case "dochazka": result.parser = new SheetAttendanceParser(); break;
            case "stravenky": result.parser = new SheetMealTicketParser(); break;
        }
        return Optional.of(result);

    }
}
