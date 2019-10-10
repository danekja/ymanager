package org.danekja.ymanager.repository;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.List;

public class RepositoryUtils {

    /**
     * Method which extracts single result from a list which is expected to contains 0 or 1 results.
     *
     * @param queryResult query result in form of {@link List}
     * @param <T>         type of object expected in the list
     * @return the object from the list or null (when empty)
     * @throws IncorrectResultSizeDataAccessException when #queryResult contains more than 1 object
     */
    public static <T> T singleResult(List<T> queryResult) {
        switch (queryResult.size()) {
            case 0:
                return null;
            case 1:
                return queryResult.get(0);
            default:
                throw new IncorrectResultSizeDataAccessException(1, queryResult.size());
        }
    }
}
