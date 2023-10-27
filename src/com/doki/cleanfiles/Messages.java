package com.doki.cleanfiles;

import java.io.File;

public class Messages {

	public static void showTitle(final Settings config) {
		if (config != null && !config.isQuietMode()) {
			System.out.println("CleanSrtFiles 1.0");
			System.out.println();
		}
	}

	public static void showHelp() {
		System.out.println("Parameters:");
		System.out.println();
		System.out.println("--help     : Show parameters.");
		System.out.println();
		System.out.println("--quiet    : Quiet mode.");
		System.out.println();
		System.out.println("--nodelete : Just show unused directories.");
	}

	public static void showStartSearch(final Settings config) {
		if (config != null && !config.isQuietMode()) {
			System.out.println("Searching...");
		}
	}

	public static void showStartDelete(final Settings config) {
		if (config != null && !config.isQuietMode()) {
			System.out.println("Deleting...");
		}
	}

	public static void showNoDirectory(final Settings config) {
		System.out.println("Not exist unused directory.");
	}

	public static void showDeleted(final Settings config, final File folder) {
		if (folder != null) {
			try {
				if (config != null && !config.isQuietMode()) {
					System.out.println(folder.getAbsolutePath() + (config.isNoDelete() ? "" : " [DELETED]"));
				}
			} catch (SecurityException e) {
				showNotDeleted(folder);
			}
		}
	}

	public static void showNotDeleted(final File folder) {
		if (folder != null) {
			try {
				System.out.println(folder.getAbsolutePath() + " not possible delete.");
			} catch (SecurityException e) {
				System.out.println("A directory that cannot be deleted.");
			}
		}
	}

	public static void showError(final Settings config, final Throwable e) {
		if (config != null && !config.isQuietMode()) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
