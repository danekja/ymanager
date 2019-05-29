package cz.zcu.yamanager.business.file.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SheetAttendanceParser implements SheetParser {


    @Override
    public SheetContent parseSheet(Sheet sheet) {

        List<AttendanceRecord> records = new ArrayList<>();

        double sumOfEstimateHours = 0;
        double sumOfWorkHours = 0;
        double hoursPerWeek = 0;
        double overtimeHours = 0;

        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

           Cell firstCell = row.getCell(0);

           if (firstCell == null) continue;

           switch (firstCell.getCellType()) {
               case NUMERIC: {
                   AttendanceRecord record = parseRowOfAttendance(row);
                   records.add(record);

                   sumOfEstimateHours += record.getEstimateHours();
                   sumOfWorkHours += record.getWorkHours();

               } break;
               case STRING: {
                    Cell loadCell = row.getCell(1);
                   String numString = loadCell.getStringCellValue().replaceAll("[^1-9.]", "");
                   hoursPerWeek = Double.parseDouble(numString);
               }
           }
        }

        overtimeHours = sumOfEstimateHours - sumOfWorkHours;

        return new SheetAttendanceContent(records, sumOfEstimateHours, sumOfWorkHours, hoursPerWeek, overtimeHours);
    }

    private AttendanceRecord parseRowOfAttendance(Row row) {

        Date date = null;
        Cell dateCell = row.getCell(0);
        if(dateCell.getCellType().equals(CellType.NUMERIC)) {
            date = dateCell.getDateCellValue();
        }

        LoadType load = null;
        Cell loadCell = row.getCell(1);
        switch (loadCell.getStringCellValue()) {
            case "prac": load = LoadType.WOORKING_WEEK; break;
            case "vikend": load = LoadType.WEEKEND; break;
        }

        double estimateHours = 0;
        RecordType type = RecordType.BLANK;
        Cell estimateHoursCell = row.getCell(2);
        switch (estimateHoursCell.getCellType()) {
            case NUMERIC: {
                estimateHours = estimateHoursCell.getNumericCellValue();
                type = RecordType.WORK;
            } break;
            case STRING: {
                String cellValue = estimateHoursCell.getStringCellValue();
                if (cellValue.equals("dovolena")) {
                    estimateHours = 0;
                    type = RecordType.VACATION;
                }
            }
        }

        double workHours = 0;
        Cell workHoursCell = row.getCell(3);
        switch (workHoursCell.getCellType()) {
            case NUMERIC: {
                workHours = workHoursCell.getNumericCellValue();
            } break;
            case STRING: {
                String cellValue = workHoursCell.getStringCellValue();
                if (cellValue.equals("dovolena")) {
                    workHours = 0;
                }
            }
        }

        return new AttendanceRecord(date, estimateHours, workHours, load, type);
    }


    @Override
    public SheetType getType() {
        return SheetType.ATTENDANCE;
    }
}
