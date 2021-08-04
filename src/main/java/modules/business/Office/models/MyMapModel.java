package modules.business.Office.models;

import modules.business.Staff.StaffExtension;
import modules.business.domain.Office;
import modules.business.domain.Staff;
import org.locationtech.jts.geom.Geometry;
import org.skyve.metadata.view.model.map.MapFeature;
import org.skyve.metadata.view.model.map.MapItem;
import org.skyve.metadata.view.model.map.MapModel;
import org.skyve.metadata.view.model.map.MapResult;

import java.util.ArrayList;
import java.util.List;

public class MyMapModel extends MapModel<Office> {
    @Override
    public MapResult getResult(Geometry geometry) throws Exception {

        List<MapItem> items = new ArrayList<>();
        Office office = getBean();

        MapItem item = new MapItem();
        // that will set the item navigate to the Office document instance
        item.setModuleName(Office.MODULE_NAME);
        item.setDocumentName(Office.DOCUMENT_NAME);
        item.setBizId(office.getBizId());

        MapFeature feat = new MapFeature();
        feat.setGeometry(office.getBoundary());
        item.getFeatures().add(feat);
        item.setInfoMarkup(office.getBizKey());
        items.add(item);

        List<StaffExtension> staffs = office.getOfficeStaff();
        for (StaffExtension staff: staffs) {
            item = new MapItem();
            item.setModuleName(Staff.MODULE_NAME);
            item.setDocumentName(Staff.DOCUMENT_NAME);
            item.setBizId(staff.getBizId());
            feat = new MapFeature();
            feat.setGeometry(staff.getLocation());
            item.getFeatures().add(feat);
            item.setInfoMarkup(staff.getBizKey());
            items.add(item);
        }

        return new MapResult(items, null);
    }
}
