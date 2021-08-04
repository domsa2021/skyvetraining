package modules.business.Staff;

import modules.admin.ModulesUtil;
import modules.business.domain.StaffStatusHistory;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.Timestamp;
import org.skyve.metadata.model.document.Bizlet;
import modules.business.domain.Staff;
import org.skyve.web.WebContext;

public class StaffBizlet extends Bizlet<StaffExtension> {

    @Override
    public void preSave(StaffExtension bean) throws Exception {
        if (bean.getDateOfBirth().after(new DateOnly())) {
            bean.setDateOfBirth(new DateOnly());
        }
        if (bean.isNotPersisted()){
            bean.setCode(ModulesUtil.getNextDocumentNumber("S", Staff.MODULE_NAME, Staff.DOCUMENT_NAME,Staff.codePropertyName, 10));
        }
        super.preSave(bean);
    }

    @Override
    public void preRerender(String source, StaffExtension bean, WebContext webContext) throws Exception {
        if (bean.originalValues().containsKey(Staff.statusPropertyName)) {
            StaffStatusHistory histo = StaffStatusHistory.newInstance();
            histo.setStatus(bean.getStatus());
            histo.setStatusTimeStamp(new Timestamp());
            histo.setParent(bean);
            bean.getStaffStatusHistory().add(histo);
        }
        if (bean.originalValues().containsKey(Staff.statusPropertyName) && bean.getStatus() != null && bean.getStatus().equals(Staff.Status.inTheOffice) ) {
            bean.home();
        }
        super.preRerender(source, bean, webContext);
    }
}
