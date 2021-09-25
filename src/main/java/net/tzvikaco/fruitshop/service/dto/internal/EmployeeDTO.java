package net.tzvikaco.fruitshop.service.dto.internal;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeDTO extends PersonDTO implements Serializable {

    private String jobTitleId;

    private String jobTitleName;

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return jobTitleId.equals(that.jobTitleId) && jobTitleName.equals(that.jobTitleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobTitleId, jobTitleName);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{ " +
            super.toString() +
            "jobTitleId='" + jobTitleId + '\'' +
            ", jobTitleTitle='" + jobTitleName + '\'' +
            '}';
    }
}
