package modules.admin.DataMaintenance.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.skyve.CORE;
import org.skyve.content.MimeType;
import org.skyve.domain.messages.Message;
import org.skyve.domain.messages.ValidationException;
import org.skyve.metadata.controller.DownloadAction;
import org.skyve.impl.backup.ExternalBackup;
import org.skyve.util.Util;
import org.skyve.web.WebContext;

import modules.admin.domain.DataMaintenance;

public class DownloadBackup extends DownloadAction<DataMaintenance> {
	private static final long serialVersionUID = 4544317770456317465L;

	@Override
	public void prepare(DataMaintenance bean, WebContext webContext) throws Exception {
		String selectedBackupName = bean.getSelectedBackupName();
		final boolean backupExists;
		if (ExternalBackup.areExternalBackupsEnabled()) {
			backupExists = ExternalBackup.getInstance().exists(selectedBackupName);
		} else {
			File backup = new File(String.format("%sbackup_%s%s%s",
					Util.getContentDirectory(),
					CORE.getUser().getCustomerName(),
					File.separator,
					selectedBackupName));
			backupExists = backup.exists();
			if (! backup.exists()) {
				Util.LOGGER.warning("Backup " + backup.getAbsolutePath() + " DNE");
			}
		}

		if (!backupExists) {
			throw new ValidationException(new Message("Backup " + selectedBackupName + " no longer exists"));
		}
	}

	@Override
	@SuppressWarnings("resource")
	public Download download(DataMaintenance bean, WebContext webContext)
	throws Exception {
		String selectedBackupName = bean.getSelectedBackupName();
		final File backup = new File(String.format("%sbackup_%s%s%s",
				Util.getContentDirectory(),
				CORE.getUser().getCustomerName(),
				File.separator,
				selectedBackupName));

		boolean deleteLocalBackup = false;
		try {
			if (ExternalBackup.areExternalBackupsEnabled()) {
				deleteLocalBackup = true;
				try (final FileOutputStream backupOutputStream = new FileOutputStream(backup)) {
					ExternalBackup.getInstance().downloadBackup(selectedBackupName, backupOutputStream);
				}
			}

			return new Download(selectedBackupName, new FileInputStream(backup), MimeType.zip);
		} finally {
			if (deleteLocalBackup) {
				if (!backup.delete()) {
					Util.LOGGER.warning("Failed to delete local backup " + backup.getAbsolutePath());
				}
			}
		}
	}
}