package com.lab.server.postProcessing;

import com.lab.server.entities.AuditLog;
import com.lab.server.services.AuditLogService;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AuditLogBeanPostProcessing implements BeanPostProcessor {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogBeanPostProcessing(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    private final Map<String, Class<?>> originalClasses = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> originalClass = bean.getClass();
        for (Method method : originalClass.getMethods()) {
            if (method.isAnnotationPresent(Audited.class)) {
                originalClasses.put(beanName, originalClass);
                break;
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> originalBean = originalClasses.get(beanName);
        if (originalBean != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(originalBean);
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                Optional<Method> originalMethod = Arrays.stream(originalBean.getMethods())
                        .filter(method::equals)
                        .findFirst();

                if (originalMethod.isPresent()) {
                    Audited annotation = originalMethod.get().getAnnotation(Audited.class);
                    if (annotation != null) {
                        Object invoke = proxy.invoke(bean, args);
                        saveAuditLog(method);
                        return invoke;
                    }
                }
                return method.invoke(bean, args);
            });
            return enhancer.create();
        }
        return bean;
    }

    @SneakyThrows
    private void saveAuditLog(Method method) {
        auditLogService.addAuditLog(new AuditLog(method.getName(), LocalDate.now()));
    }
}
