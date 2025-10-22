package com.bnpparibas.tr.timer.it;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import com.bnpparibas.tr.timer.it.support.TimerRequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.awaitility.Awaitility.await;

public class TimerCrudIntegrationTest extends BaseIntegrationTest {

    @Test
    void createActiveTimer_persistsTimersAndQuartz_withWaitingState() throws Exception {
        String body = TimerRequests.create("t1", "UTC", "10:15", false);

        MvcResult res = mockMvc.perform(post("/api/timers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        // Extract id from response JSON
        String json = res.getResponse().getContentAsString();
        String id = json.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        assertThat(id).isNotBlank();

        // App table
        Integer timersCount = jdbc.queryForObject("select count(*) from TIMERS where id=?", Integer.class, id);
        assertThat(timersCount).isEqualTo(1);

        // Quartz trigger in WAITING state
        Integer qCount = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id);
        assertThat(qCount).isEqualTo(1);
    }

    @Test
    void editTimer_updatesTimersAndQuartz() throws Exception {
        String createBody = TimerRequests.create("t3", "UTC", "10:15", false);

        MvcResult res = mockMvc.perform(post("/api/timers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andReturn();
        String id = res.getResponse().getContentAsString().replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        String updateBody = TimerRequests.update("t3-updated", "Europe/London", "09:30", null, false);

        mockMvc.perform(put("/api/timers/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk());

        // Verify app table changes
        String zone = jdbc.queryForObject("select ZONE_ID from TIMERS where id=?", String.class, id);
        assertThat(zone).isEqualTo("Europe/London");

        // Verify Quartz trigger remains present in WAITING state (not paused)
        Integer waiting = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id);
        assertThat(waiting).isEqualTo(1);
    }

    @Test
    void createSuspendResume_changesTriggerState() throws Exception {
        String createBody = TimerRequests.create("t4", "UTC", "10:15", false);

        MvcResult res = mockMvc.perform(post("/api/timers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andReturn();
        String id = res.getResponse().getContentAsString().replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        // Pause via service API is not exposed; emulate by updating suspended=true
        String suspendBody = TimerRequests.update("t4", "UTC", "10:15", null, true);
        mockMvc.perform(put("/api/timers/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suspendBody))
                .andExpect(status().isOk());

        Integer paused = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='PAUSED'", Integer.class, id);
        assertThat(paused).isEqualTo(1);

        // Resume (suspended=false)
        String resumeBody = TimerRequests.update("t4", "UTC", "10:15", null, false);
        mockMvc.perform(put("/api/timers/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resumeBody))
                .andExpect(status().isOk());

        Integer waiting = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id);
        assertThat(waiting).isEqualTo(1);
    }

    @Test
    void bulkSuspendResume_twoTimers() throws Exception {
        String body1 = TimerRequests.create("b1", "UTC", "10:15", false);
        String body2 = TimerRequests.create("b2", "UTC", "10:15", false);

        String id1 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON).content(body1))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        String id2 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON).content(body2))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        // initial waiting
        assertThat(jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id1)).isEqualTo(1);
        assertThat(jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id2)).isEqualTo(1);

        // suspend both
        String suspend1 = TimerRequests.update("b1", "UTC", "10:15", null, true);
        String suspend2 = TimerRequests.update("b2", "UTC", "10:15", null, true);
        mockMvc.perform(put("/api/timers/" + id1).contentType(MediaType.APPLICATION_JSON).content(suspend1)).andExpect(status().isOk());
        mockMvc.perform(put("/api/timers/" + id2).contentType(MediaType.APPLICATION_JSON).content(suspend2)).andExpect(status().isOk());

        assertThat(jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='PAUSED'", Integer.class, id1)).isEqualTo(1);
        assertThat(jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='PAUSED'", Integer.class, id2)).isEqualTo(1);

        // resume both
        String resume1 = TimerRequests.update("b1", "UTC", "10:15", null, false);
        String resume2 = TimerRequests.update("b2", "UTC", "10:15", null, false);
        mockMvc.perform(put("/api/timers/" + id1).contentType(MediaType.APPLICATION_JSON).content(resume1)).andExpect(status().isOk());
        mockMvc.perform(put("/api/timers/" + id2).contentType(MediaType.APPLICATION_JSON).content(resume2)).andExpect(status().isOk());

        assertThat(jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id1)).isEqualTo(1);
        assertThat(jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='WAITING'", Integer.class, id2)).isEqualTo(1);
    }

    @Test
    void manualTrigger_createsManualExecution() throws Exception {
        String body = TimerRequests.create("m1", "UTC", "10:15", false);
        String id = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(post("/api/timers/" + id + "/_trigger")).andExpect(status().isNoContent());

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            Integer execCount = jdbc.queryForObject("select count(*) from TIMER_EXECUTIONS where TIMER_ID=? and TRIGGER_TYPE='MANUAL'", Integer.class, id);
            assertThat(execCount).isGreaterThanOrEqualTo(1);
        });
    }

    @Test
    void scheduledTrigger_createsScheduledExecution() throws Exception {
        String body = TimerRequests.createCron("s1", "UTC", "0/2 * * * * ?", false);
        String id = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        await().atMost(Duration.ofSeconds(12)).untilAsserted(() -> {
            Integer execCount = jdbc.queryForObject("select count(*) from TIMER_EXECUTIONS where TIMER_ID=? and TRIGGER_TYPE='SCHEDULED'", Integer.class, id);
            assertThat(execCount).isGreaterThanOrEqualTo(1);
        });
    }

    @Test
    void severalManualTriggers_createExecutionsForEach() throws Exception {
        String id1 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.create("mm1", "UTC", "10:15", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        String id2 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.create("mm2", "UTC", "10:15", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(post("/api/timers/" + id1 + "/_trigger")).andExpect(status().isNoContent());
        mockMvc.perform(post("/api/timers/" + id2 + "/_trigger")).andExpect(status().isNoContent());

        await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            Integer c1 = jdbc.queryForObject("select count(*) from TIMER_EXECUTIONS where TIMER_ID=? and TRIGGER_TYPE='MANUAL'", Integer.class, id1);
            Integer c2 = jdbc.queryForObject("select count(*) from TIMER_EXECUTIONS where TIMER_ID=? and TRIGGER_TYPE='MANUAL'", Integer.class, id2);
            assertThat(c1).isGreaterThanOrEqualTo(1);
            assertThat(c2).isGreaterThanOrEqualTo(1);
        });
    }

    @Test
    void severalScheduledTriggers_createExecutionsForEach() throws Exception {
        String id1 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.createCron("ss1", "UTC", "0/2 * * * * ?", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        String id2 = mockMvc.perform(post("/api/timers").contentType(MediaType.APPLICATION_JSON)
                        .content(TimerRequests.createCron("ss2", "UTC", "0/2 * * * * ?", false)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        await().atMost(Duration.ofSeconds(12)).untilAsserted(() -> {
            Integer c1 = jdbc.queryForObject("select count(*) from TIMER_EXECUTIONS where TIMER_ID=? and TRIGGER_TYPE='SCHEDULED'", Integer.class, id1);
            Integer c2 = jdbc.queryForObject("select count(*) from TIMER_EXECUTIONS where TIMER_ID=? and TRIGGER_TYPE='SCHEDULED'", Integer.class, id2);
            assertThat(c1).isGreaterThanOrEqualTo(1);
            assertThat(c2).isGreaterThanOrEqualTo(1);
        });
    }
    @Test
    void createSuspendedTimer_persistsTimersAndQuartz_withPausedState() throws Exception {
        String body = "{" +
                "\"name\":\"t2\"," +
                "\"zoneId\":\"UTC\"," +
                "\"triggerTime\":\"10:15\"," +
                "\"suspended\":true" +
                "}";

        MvcResult res = mockMvc.perform(post("/api/timers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        String json = res.getResponse().getContentAsString();
        String id = json.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        assertThat(id).isNotBlank();

        Integer timersCount = jdbc.queryForObject("select count(*) from TIMERS where id=?", Integer.class, id);
        assertThat(timersCount).isEqualTo(1);

        Integer qCount = jdbc.queryForObject("select count(*) from QRTZ_TRIGGERS where TRIGGER_NAME=? and TRIGGER_GROUP='instances' and TRIGGER_STATE='PAUSED'", Integer.class, id);
        assertThat(qCount).isEqualTo(1);
    }
}


