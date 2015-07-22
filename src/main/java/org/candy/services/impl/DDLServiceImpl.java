package org.candy.services.impl;

import org.candy.dataaccess.DDLDataService;
import org.candy.model.DefinedEntity;
import org.candy.services.DDLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jpedro on 25-06-2015.
 */
@Service
public class DDLServiceImpl implements DDLService {
    private final DDLDataService ddlDataService;

    @Autowired
    public DDLServiceImpl(DDLDataService ddlDataService) {
        this.ddlDataService = ddlDataService;
    }

    @Override
    public void persistDefinedEntiry(DefinedEntity definedEntity) {
        ddlDataService.createDefinedEntity(definedEntity);
    }
}
