package com.doki.cleanfiles;

import java.util.Arrays;
import java.util.Locale;

public class Settings {

	private final static String PRINT_HELP_ARGS = "--help";
	private boolean printHelp = false;

	private final static String QUIET_MODE_ARGS = "--quiet";
	private boolean quietMode = false;

	private final static String NO_DELETE_ARGS = "--nodelete";
	private boolean noDelete = false;

	public boolean isPrintHelp() {
		return printHelp;
	}

	public boolean isQuietMode() {
		return quietMode;
	}

	public boolean isNoDelete() {
		return noDelete;
	}

	private Settings() {
	}

	public static Settings parseArgs(String[] args) {
		Settings result = new Settings();
		if (args != null && args.length > 0) {
			Arrays.stream(args).forEach((final String arg) -> {
				switch (arg.trim().toLowerCase(Locale.ENGLISH)) {
				case PRINT_HELP_ARGS:
				default:
					result.printHelp = true;
					break;
				case QUIET_MODE_ARGS:
					result.quietMode = true;
					break;
				case NO_DELETE_ARGS:
					result.noDelete = true;
					break;
				}
			});
		}
		return result;
	}

}
