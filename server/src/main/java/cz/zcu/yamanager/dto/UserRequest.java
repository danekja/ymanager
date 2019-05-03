package cz.zcu.yamanager.dto;

import java.util.List;

public class UserRequest {

    private List<VacationRequest> vacation;
    private List<AuthorizationRequest> authorization;

    public List<VacationRequest> getVacation() {
        return vacation;
    }

    public void setVacation(List<VacationRequest> vacation) {
        this.vacation = vacation;
    }

    public List<AuthorizationRequest> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<AuthorizationRequest> authorization) {
        this.authorization = authorization;
    }
}
