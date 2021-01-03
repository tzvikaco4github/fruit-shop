package net.tzvikaco.fruitshop.service.dto.internal;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeDTO extends PersonDTO implements Serializable {

    private String jobTitleId;

    private String jobTitleTitle;

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitleTitle() {
        return jobTitleTitle;
    }

    public void setJobTitleTitle(String jobTitleTitle) {
        this.jobTitleTitle = jobTitleTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return jobTitleId.equals(that.jobTitleId) && jobTitleTitle.equals(that.jobTitleTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobTitleId, jobTitleTitle);
    }
}
