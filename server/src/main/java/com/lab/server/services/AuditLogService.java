package com.lab.server.services;

import com.lab.server.entities.AuditLog;
import com.lab.server.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }
    public void addAuditLog(AuditLog auditLog) {
        try{
            auditLogRepository.save(auditLog);
        } catch (Exception ignored) {

        }
    }
}
