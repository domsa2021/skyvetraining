package modules.business.Staff.actions;

import modules.business.Staff.StaffExtension;
import modules.business.domain.Staff;
import org.skyve.metadata.controller.ServerSideAction;
import org.skyve.metadata.controller.ServerSideActionResult;
import org.skyve.web.WebContext;

public class Home implements ServerSideAction<StaffExtension> {

    @Override
    public ServerSideActionResult<StaffExtension> execute(StaffExtension staff, WebContext webContext) throws Exception {
        staff.home();
        return new ServerSideActionResult<>(staff);
    }
}
