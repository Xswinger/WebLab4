package com.lab.server.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String methodName;

    private LocalDate executingTime;

    public AuditLog() {
    }

    public AuditLog(String methodName, LocalDate executingTime) {
        this.methodName = methodName;
        this.executingTime = executingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public LocalDate getExecutingTime() {
        return executingTime;
    }

    public void setExecutingTime(LocalDate executingTime) {
        this.executingTime = executingTime;
    }
}
