package modules.business.Staff;

import modules.business.domain.Staff;
import org.skyve.CORE;
import org.skyve.job.Job;
import org.skyve.persistence.DocumentQuery;

import java.util.List;

public class StaffJobRehome extends Job {

    @Override
    public void execute() throws Exception {
        List<String> log = getLog();
        DocumentQuery q = CORE.getPersistence().newDocumentQuery(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
        q.getFilter().addEquals(Staff.statusPropertyName, Staff.Status.inTheOffice);
        List<StaffExtension> list = q.beanResults();
        for (StaffExtension s : list) {
            s.home();
            s = CORE.getPersistence().save(s);
            log.add(String.format("staff %s update successfully", s.getBizKey()));
        }
    }
}
