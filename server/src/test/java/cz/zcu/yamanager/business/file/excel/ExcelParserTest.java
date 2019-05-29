package cz.zcu.yamanager.business.file.excel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExcelParserTest {

    @Test
    public void parseXLSFile() {

        try {

            InputStream stream = getClass().getClassLoader().getResourceAsStream("dochazka_mezistupen.xlsx");

            Map<String, SheetContent[]> sheets = ExcelParser.parseXLSX(stream);

            Map<String, Double> nameOvertime = new HashMap<>();
            Map<String, List<Date>> nameVacation = new HashMap<>();

            for (Map.Entry<String, SheetContent[]> item : sheets.entrySet()) {
                String name = item.getKey();
                SheetAttendanceContent content = (SheetAttendanceContent) item.getValue()[SheetType.ATTENDANCE.ordinal()];
                for (AttendanceRecord record : content.getRecords()) {
                    if (record.getType().equals(RecordType.VACATION)) {
                        if (!nameVacation.containsKey(name)) {
                            nameVacation.put(name, new ArrayList<>());
                        }
                        nameVacation.get(name).add(record.getDate());
                    }
                }
                nameOvertime.put(name, content.getOvertimeHours());
            }

            assertTrue(nameOvertime.containsKey("Onegin"));
            assertEquals(0, nameOvertime.get("Onegin").intValue());

            assertTrue(nameOvertime.containsKey("Novak"));
            assertEquals(0, nameOvertime.get("Novak").intValue());

            assertTrue(nameOvertime.containsKey("Pavel"));
            assertEquals(0, nameOvertime.get("Pavel").intValue());

            assertTrue(nameOvertime.containsKey("Fristensky"));
            assertEquals(0, nameOvertime.get("Fristensky").intValue());


            assertFalse(nameVacation.containsKey("Onegin"));
            assertFalse(nameVacation.containsKey("Novak"));
            assertFalse(nameVacation.containsKey("Fristensky"));

            assertTrue(nameVacation.containsKey("Pavel"));
            assertEquals(5, nameVacation.get("Pavel").size());

        } catch (IOException e) {
            assertFalse(false, "");
        }
    }

}