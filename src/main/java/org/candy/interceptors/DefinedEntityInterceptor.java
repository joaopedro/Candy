package org.candy.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.candy.model.DefinedEntity;
import org.candy.services.DDLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefinedEntityInterceptor {

    private final DDLService ddlService;

    @Autowired
    public DefinedEntityInterceptor(DDLService ddlService) {
        this.ddlService = ddlService;
    }

    @AfterReturning("execution(* org.candy.dataaccess.DefinedEntityRepository.save(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		DefinedEntity definedEntity = (DefinedEntity) joinPoint.getArgs()[0];
        ddlService.persistDefinedEntiry(definedEntity);
    }

}