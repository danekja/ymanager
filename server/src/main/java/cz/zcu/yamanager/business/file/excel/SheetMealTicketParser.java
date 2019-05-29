package cz.zcu.yamanager.business.file.excel;

import org.apache.poi.ss.usermodel.Sheet;

public class SheetMealTicketParser implements SheetParser {

    @Override
    public SheetContent parseSheet(Sheet sheet) {
        return null;
    }

    @Override
    public SheetType getType() {
        return SheetType.MEAL_TICKET;
    }
}
