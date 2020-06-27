package org.danekja.ymanager.domain;

/**
 * The enum {@code Status} represents states of authorization and vacation approval process.
 */
public enum Status {
    /**
     * The subject of a request is accepted.
     */
    ACCEPTED,

    /**
     * The subject of a request is waiting for an answer.
     */
    PENDING,

    /**
     * The subject of a request is rejected.
     */
    REJECTED,
}
