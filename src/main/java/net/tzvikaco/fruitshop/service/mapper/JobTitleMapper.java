package net.tzvikaco.fruitshop.service.mapper;


import net.tzvikaco.fruitshop.domain.*;
import net.tzvikaco.fruitshop.service.dto.JobTitleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobTitle} and its DTO {@link JobTitleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobTitleMapper extends EntityMapper<JobTitleDTO, JobTitle> {



    default JobTitle fromId(String id) {
        if (id == null) {
            return null;
        }
        JobTitle jobTitle = new JobTitle();
        jobTitle.setId(id);
        return jobTitle;
    }
}
