package com.example.timers.domain.event;

public record TimerEvent(String timerName, String groupName, FilterCondition filterCondition) {}


