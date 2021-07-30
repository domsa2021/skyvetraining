package modules.business.Staff;

import modules.business.domain.Staff;
import org.skyve.domain.types.DateOnly;
import org.skyve.impl.util.TimeUtil;

import java.time.LocalDate;
import java.time.Period;

public class StaffExtension extends Staff {

    @Override
    public String getAge() {
        DateOnly dob = this.getDateOfBirth();
        Period diff = Period.between(dob.toLocalDate(), new DateOnly().toLocalDate());
        return String.format("%d years, %d months and %d days", diff.getYears(), diff.getMonths(), diff.getDays());
    }
}
