package com.tower.service.config.utils;

import java.io.File;

public abstract class FileWatchdog extends Thread implements WatchDog {
	// private static Logger logger = LoggerFactory.getLogger(FileWatchdog.class);

	/**
	 * The default delay between every file modification check, set to one hour.
	 */
	static final public long DEFAULT_DELAY = 3600000;

	/**
	 * The name of the file to observe for changes.
	 */
	private String filename;

	/**
	 * The delay to observe between every check. By default set {@link #DEFAULT_DELAY}.
	 */
	protected long delay = DEFAULT_DELAY;

	File file;
	long lastModif = 0;
	boolean warnedAlready = false;
	boolean interrupted = false;

	protected FileWatchdog(String filename) {

		this.filename = filename;
		this.setName(this.getName() + "@" + filename);
		file = new File(filename);
		setDaemon(true);
	}

	protected void checkAndConfigure() {

		boolean fileExists;
		try {
			fileExists = file.exists();
		} catch (SecurityException e) {
			// LogLog.warn("Was not allowed to read check file existance, file:["+filename+"].");
			interrupted = true; // there is no point in continuing
			return;
		}

		if (fileExists) {
			long l = file.lastModified();
			if (lastModif != 0 && l > lastModif) {
				lastModif = l;
				doOnChange();
				// logger.info("database configure changed at "+l + " "+ new Date(l).toString());

			} else {
				lastModif = l;
			}
			warnedAlready = false;
		} else {
			if (!warnedAlready) {
				// LogLog.debug("["+filename+"] does not exist.");
				warnedAlready = true;
			}
		}
	}

	public void run() {

		// logger.info("watch thread " + Thread.currentThread().getName()+" start watch " + filename +" :");
		while (!interrupted) {
			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e) {
			}
			checkAndConfigure();
		}
	}

	public String getFilename() {
		return filename;
	}

	/**
	 * Set the delay to observe between each check of the file changes.
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

}
