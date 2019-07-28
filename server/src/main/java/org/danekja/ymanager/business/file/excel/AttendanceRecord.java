package org.danekja.ymanager.business.file.excel;

import java.util.Date;

public class AttendanceRecord {

    private final Date date;
    private final double estimateHours;
    private final double workHours;
    private final LoadType load;
    private final RecordType type;

    public AttendanceRecord(Date date, double estimateHours, double workHours, LoadType load, RecordType type) {
        this.date = date;
        this.estimateHours = estimateHours;
        this.workHours = workHours;
        this.load = load;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public double getEstimateHours() {
        return estimateHours;
    }

    public double getWorkHours() {
        return workHours;
    }

    public LoadType getLoad() {
        return load;
    }

    public RecordType getType() {
        return type;
    }
}
