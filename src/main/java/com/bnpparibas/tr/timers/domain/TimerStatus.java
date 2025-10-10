package com.bnpparibas.tr.timers.domain;

public enum TimerStatus {
    ACTIVE,      // Ready to execute
    SUSPENDED,   // Paused/disabled
    RUNNING,     // Currently executing
    COMPLETED,   // Successfully completed
    FAILED       // Execution failed
}


