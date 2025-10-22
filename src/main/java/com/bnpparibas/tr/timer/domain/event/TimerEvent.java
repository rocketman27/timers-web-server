package com.bnpparibas.tr.timer.domain.event;

public record TimerEvent(String timerName, String groupName, FilterCondition filterCondition) {}


