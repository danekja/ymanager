package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.Status;
import org.danekja.ymanager.domain.Vacation;
import org.danekja.ymanager.dto.VacationDayDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VacationRepository {

    List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from);

    List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final Status status);

    List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final LocalDate to);

    List<VacationDayDTO> getVacationDays(final long userId, final LocalDate from, final LocalDate to, final Status status);

    Optional<Vacation> getVacationDay(final long id);

    void insertVacationDay(final Long userId, final Vacation day);

    void updateVacationDay(final Vacation item);

    void deleteVacationDay(final long id);

    boolean isExistVacationForUser(Long userId, LocalDate date);
}
