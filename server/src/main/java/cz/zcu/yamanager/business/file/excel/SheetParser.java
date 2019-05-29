package cz.zcu.yamanager.business.file.excel;

import org.apache.poi.ss.usermodel.Sheet;

public interface SheetParser {

    SheetContent parseSheet(Sheet sheet);

    SheetType getType();
}
