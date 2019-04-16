package cz.zcu.yamanager.repository;

import cz.zcu.yamanager.domain.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HelloRepository {

    private static final Logger log = LoggerFactory.getLogger(HelloRepository.class);

    private final JdbcTemplate jdbc;

    @Autowired
    public HelloRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Test> all() {
        return jdbc.query("SELECT id, message FROM test",
                (rs, rowNum) -> new Test(rs.getLong("id"), rs.getString("message")));
    }
}
