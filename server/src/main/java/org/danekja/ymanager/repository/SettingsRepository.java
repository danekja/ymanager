package org.danekja.ymanager.repository;

import org.danekja.ymanager.domain.DefaultSettings;

/**
 * DAO interface for settings object.
 */
public interface SettingsRepository {

    /**
     * @return currently valid default settings
     */
    DefaultSettings get();

}
