package modules.business.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.business.Staff.StaffExtension;
import org.locationtech.jts.geom.Geometry;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.ChangeTrackingArrayList;
import org.skyve.impl.domain.types.jaxb.DateOnlyMapper;
import org.skyve.impl.domain.types.jaxb.GeometryMapper;
import org.skyve.metadata.model.document.Bizlet.DomainValue;

/**
 * Staff
 * 
 * @depend - - - Status
 * @navcomposed 1 staffStatusHistory 0..n StaffStatusHistory
 * @navhas n baseOffice 0..1 Office
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class Staff extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "business";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Staff";

	/** @hidden */
	public static final String codePropertyName = "code";

	/** @hidden */
	public static final String namePropertyName = "name";

	/** @hidden */
	public static final String dateOfBirthPropertyName = "dateOfBirth";

	/** @hidden */
	public static final String agePropertyName = "age";

	/** @hidden */
	public static final String baseOfficePropertyName = "baseOffice";

	/** @hidden */
	public static final String locationPropertyName = "location";

	/** @hidden */
	public static final String photoPropertyName = "photo";

	/** @hidden */
	public static final String statusPropertyName = "status";

	/** @hidden */
	public static final String resumePropertyName = "resume";

	/** @hidden */
	public static final String staffStatusHistoryPropertyName = "staffStatusHistory";

	/**
	 * status
	 * <br/>
	 * status
	 **/
	@XmlEnum
	public static enum Status implements Enumeration {
		inTheOffice("In", "In the Office"),
		outToLunch("Out to lunch", "Out to lunch"),
		out("Out", "Out");

		private String code;
		private String description;

		/** @hidden */
		private DomainValue domainValue;

		/** @hidden */
		private static List<DomainValue> domainValues;

		private Status(String code, String description) {
			this.code = code;
			this.description = description;
			this.domainValue = new DomainValue(code, description);
		}

		@Override
		public String toCode() {
			return code;
		}

		@Override
		public String toDescription() {
			return description;
		}

		@Override
		public DomainValue toDomainValue() {
			return domainValue;
		}

		public static Status fromCode(String code) {
			Status result = null;

			for (Status value : values()) {
				if (value.code.equals(code)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static Status fromDescription(String description) {
			Status result = null;

			for (Status value : values()) {
				if (value.description.equals(description)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static List<DomainValue> toDomainValues() {
			if (domainValues == null) {
				Status[] values = values();
				domainValues = new ArrayList<>(values.length);
				for (Status value : values) {
					domainValues.add(value.domainValue);
				}
			}

			return domainValues;
		}
	}

	/**
	 * code
	 **/
	private String code;

	/**
	 * name
	 **/
	private String name;

	/**
	 * date of birth
	 **/
	private DateOnly dateOfBirth;

	/**
	 * age
	 **/
	private String age;

	/**
	 * office
	 **/
	private Office baseOffice = null;

	/**
	 * location
	 **/
	private Geometry location;

	/**
	 * id photo
	 **/
	private String photo;

	/**
	 * status
	 * <br/>
	 * status
	 **/
	private Status status = Status.inTheOffice;

	/**
	 * resume
	 **/
	private String resume;

	/**
	 * staffStatusHistory
	 * <br/>
	 * Staff Status History
	 **/
	private List<StaffStatusHistory> staffStatusHistory = new ChangeTrackingArrayList<>("staffStatusHistory", this);

	@Override
	@XmlTransient
	public String getBizModule() {
		return Staff.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Staff.DOCUMENT_NAME;
	}

	public static StaffExtension newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("Staff - {code}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Staff) && 
					this.getBizId().equals(((Staff) o).getBizId()));
	}

	/**
	 * {@link #code} accessor.
	 * @return	The value.
	 **/
	public String getCode() {
		return code;
	}

	/**
	 * {@link #code} mutator.
	 * @param code	The new value.
	 **/
	@XmlElement
	public void setCode(String code) {
		preset(codePropertyName, code);
		this.code = code;
	}

	/**
	 * {@link #name} accessor.
	 * @return	The value.
	 **/
	public String getName() {
		return name;
	}

	/**
	 * {@link #name} mutator.
	 * @param name	The new value.
	 **/
	@XmlElement
	public void setName(String name) {
		preset(namePropertyName, name);
		this.name = name;
	}

	/**
	 * {@link #dateOfBirth} accessor.
	 * @return	The value.
	 **/
	public DateOnly getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * {@link #dateOfBirth} mutator.
	 * @param dateOfBirth	The new value.
	 **/
	@XmlElement
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(DateOnlyMapper.class)
	public void setDateOfBirth(DateOnly dateOfBirth) {
		preset(dateOfBirthPropertyName, dateOfBirth);
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * {@link #age} accessor.
	 * @return	The value.
	 **/
	public String getAge() {
		return age;
	}

	/**
	 * {@link #age} mutator.
	 * @param age	The new value.
	 **/
	@XmlElement
	public void setAge(String age) {
		preset(agePropertyName, age);
		this.age = age;
	}

	/**
	 * {@link #baseOffice} accessor.
	 * @return	The value.
	 **/
	public Office getBaseOffice() {
		return baseOffice;
	}

	/**
	 * {@link #baseOffice} mutator.
	 * @param baseOffice	The new value.
	 **/
	@XmlElement
	public void setBaseOffice(Office baseOffice) {
		if (this.baseOffice != baseOffice) {
			Office oldBaseOffice = this.baseOffice;
			this.baseOffice = baseOffice;
			if ((baseOffice != null) && (baseOffice.getOfficeStaffElementById(getBizId()) == null)) {
				baseOffice.getOfficeStaff().add((StaffExtension) this);
			}
			if (oldBaseOffice != null) {
				oldBaseOffice.getOfficeStaff().remove(this);
			}
		}
	}

	public void nullBaseOffice() {
		this.baseOffice = null;
	}

	/**
	 * {@link #location} accessor.
	 * @return	The value.
	 **/
	public Geometry getLocation() {
		return location;
	}

	/**
	 * {@link #location} mutator.
	 * @param location	The new value.
	 **/
	@XmlElement
	@XmlJavaTypeAdapter(GeometryMapper.class)
	public void setLocation(Geometry location) {
		preset(locationPropertyName, location);
		this.location = location;
	}

	/**
	 * {@link #photo} accessor.
	 * @return	The value.
	 **/
	public String getPhoto() {
		return photo;
	}

	/**
	 * {@link #photo} mutator.
	 * @param photo	The new value.
	 **/
	@XmlElement
	public void setPhoto(String photo) {
		preset(photoPropertyName, photo);
		this.photo = photo;
	}

	/**
	 * {@link #status} accessor.
	 * @return	The value.
	 **/
	public Status getStatus() {
		return status;
	}

	/**
	 * {@link #status} mutator.
	 * @param status	The new value.
	 **/
	@XmlElement
	public void setStatus(Status status) {
		preset(statusPropertyName, status);
		this.status = status;
	}

	/**
	 * {@link #resume} accessor.
	 * @return	The value.
	 **/
	public String getResume() {
		return resume;
	}

	/**
	 * {@link #resume} mutator.
	 * @param resume	The new value.
	 **/
	@XmlElement
	public void setResume(String resume) {
		preset(resumePropertyName, resume);
		this.resume = resume;
	}

	/**
	 * {@link #staffStatusHistory} accessor.
	 * @return	The value.
	 **/
	@XmlElement
	public List<StaffStatusHistory> getStaffStatusHistory() {
		return staffStatusHistory;
	}

	/**
	 * {@link #staffStatusHistory} accessor.
	 * @param bizId	The bizId of the element in the list.
	 * @return	The value of the element in the list.
	 **/
	public StaffStatusHistory getStaffStatusHistoryElementById(String bizId) {
		return getElementById(staffStatusHistory, bizId);
	}

	/**
	 * {@link #staffStatusHistory} mutator.
	 * @param bizId	The bizId of the element in the list.
	 * @param element	The new value of the element in the list.
	 **/
	public void setStaffStatusHistoryElementById(String bizId, StaffStatusHistory element) {
		setElementById(staffStatusHistory, element);
	}

	/**
	 * {@link #staffStatusHistory} add.
	 * @param element	The element to add.
	 **/
	public boolean addStaffStatusHistoryElement(StaffStatusHistory element) {
		boolean result = staffStatusHistory.add(element);
		element.setParent((StaffExtension) this);
		return result;
	}

	/**
	 * {@link #staffStatusHistory} add.
	 * @param index	The index in the list to add the element to.
	 * @param element	The element to add.
	 **/
	public void addStaffStatusHistoryElement(int index, StaffStatusHistory element) {
		staffStatusHistory.add(index, element);
		element.setParent((StaffExtension) this);
	}

	/**
	 * {@link #staffStatusHistory} remove.
	 * @param element	The element to remove.
	 **/
	public boolean removeStaffStatusHistoryElement(StaffStatusHistory element) {
		boolean result = staffStatusHistory.remove(element);
		element.setParent(null);
		return result;
	}

	/**
	 * {@link #staffStatusHistory} remove.
	 * @param index	The index in the list to remove the element from.
	 **/
	public StaffStatusHistory removeStaffStatusHistoryElement(int index) {
		StaffStatusHistory result = staffStatusHistory.remove(index);
		result.setParent(null);
		return result;
	}
}
