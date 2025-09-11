package com.example.timers.service;

import com.example.timers.domain.instance.TimerInstance;
import com.example.timers.domain.template.TimerTemplate;
import com.example.timers.repository.TimerInstanceRepository;
import com.example.timers.scheduler.TimerScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemplateMaterializerTest {

    @Mock
    private TimerInstanceRepository instanceRepository;
    @Mock
    private TimerScheduler scheduler;

    @InjectMocks
    private TemplateMaterializer materializer;

    private TimerTemplate template;

    @BeforeEach
    void setUp() {
        template = new TimerTemplate();
        template.setId("tpl-1");
        template.setCronExpression("0 15 10 * * ?");
        template.setZoneId("UTC");
        template.setCountries(List.of("US", "CA"));
        template.setRegions(List.of("NE"));
        template.setSubregions(List.of("NY", "NJ"));
        template.setFlowTypes(List.of("A"));
        template.setClientIds(List.of("c1", "c2", "c3"));

        // saveAll returns its input for simplicity
        when(instanceRepository.saveAll(any(List.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void materialize_createsCartesianProduct_andSchedulesEach() throws Exception {
        List<TimerInstance> result = materializer.materializeAndSchedule(template);

        // 2 countries * 1 region * 2 subregions * 1 flow * 3 clients = 12
        assertThat(result).hasSize(12);

        // verify repo saveAll called once with 12 items
        ArgumentCaptor<List<TimerInstance>> captor = ArgumentCaptor.forClass(List.class);
        verify(instanceRepository, times(1)).saveAll(captor.capture());
        assertThat(captor.getValue()).hasSize(12);

        // scheduler called once per instance
        verify(scheduler, times(12)).scheduleOrUpdate(any(), eq("0 15 10 * * ?"), eq("UTC"));
    }
}


