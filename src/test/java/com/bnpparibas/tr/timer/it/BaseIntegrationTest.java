package com.bnpparibas.tr.timer.it;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JdbcTemplate jdbc;

    @BeforeEach
    void cleanDb() {
        // Clean Quartz and app tables between tests to avoid interference
        jdbc.execute("DELETE FROM TIMER_EXECUTIONS");
        jdbc.execute("DELETE FROM TIMER_EXCLUDED_REGIONS");
        jdbc.execute("DELETE FROM TIMER_EXCLUDED_COUNTRIES");
        jdbc.execute("DELETE FROM TIMER_PRODUCT_TYPES");
        jdbc.execute("DELETE FROM TIMER_CLIENT_IDS");
        jdbc.execute("DELETE FROM TIMER_FLOW_TYPES");
        jdbc.execute("DELETE FROM TIMER_SUBREGIONS");
        jdbc.execute("DELETE FROM TIMER_REGIONS");
        jdbc.execute("DELETE FROM TIMER_COUNTRIES");
        jdbc.execute("DELETE FROM TIMERS");

        jdbc.execute("DELETE FROM QRTZ_FIRED_TRIGGERS");
        jdbc.execute("DELETE FROM QRTZ_PAUSED_TRIGGER_GRPS");
        jdbc.execute("DELETE FROM QRTZ_SCHEDULER_STATE");
        jdbc.execute("DELETE FROM QRTZ_LOCKS");
        jdbc.execute("DELETE FROM QRTZ_SIMPLE_TRIGGERS");
        jdbc.execute("DELETE FROM QRTZ_SIMPROP_TRIGGERS");
        jdbc.execute("DELETE FROM QRTZ_CRON_TRIGGERS");
        jdbc.execute("DELETE FROM QRTZ_TRIGGERS");
        jdbc.execute("DELETE FROM QRTZ_JOB_DETAILS");
        jdbc.execute("DELETE FROM QRTZ_CALENDARS");
    }
}


