package cz.zcu.yamanager.dto;

public class VacationInfo {
    private float value;
    private VacationUnit unit;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public VacationUnit getUnit() {
        return unit;
    }

    public void setUnit(VacationUnit unit) {
        this.unit = unit;
    }
}
