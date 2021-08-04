package modules.business.Staff;

import modules.business.domain.Staff;
import org.skyve.domain.types.DateOnly;
import org.skyve.impl.util.TimeUtil;

import java.time.LocalDate;
import java.time.Period;

public class StaffExtension extends Staff {

    @Override
    public String getAge() {
        if (this.getDateOfBirth() != null) {
            DateOnly dob = this.getDateOfBirth();
            Period diff = Period.between(dob.toLocalDate(), new DateOnly().toLocalDate());
            return String.format("%d years, %d months and %d days", diff.getYears(), diff.getMonths(), diff.getDays());
        } else{
            return null;
        }
    }

    public void home(){
        if (this.getBaseOffice() != null && this.getBaseOffice().getBoundary() != null) {
            this.setLocation(this.getBaseOffice().getBoundary().getCentroid());
        }
    }
}
