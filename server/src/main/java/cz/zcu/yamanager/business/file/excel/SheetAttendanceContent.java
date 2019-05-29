package cz.zcu.yamanager.business.file.excel;

import java.util.List;

public class SheetAttendanceContent implements SheetContent {

    private List<AttendanceRecord> records;

    private final double sumOfEstimateHours;
    private final double sumOfWorkHours;
    private final double hoursPerWeek;
    private final double overtimeHours;

    public SheetAttendanceContent(
            List<AttendanceRecord> records,
            double sumOfEstimateHours,
            double sumOfWorkHours,
            double hoursPerWeek,
            double overtimeHours)
    {
        this.records = records;
        this.sumOfEstimateHours = sumOfEstimateHours;
        this.sumOfWorkHours = sumOfWorkHours;
        this.hoursPerWeek = hoursPerWeek;
        this.overtimeHours = overtimeHours;
    }

    public double getSumOfEstimateHours() {
        return sumOfEstimateHours;
    }

    public double getSumOfWorkHours() {
        return sumOfWorkHours;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public List<AttendanceRecord> getRecords() {
        return records;
    }
}
