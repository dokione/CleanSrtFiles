package com.doki.cleanfiles;

import java.nio.file.Path;
import java.util.List;

public class CleanSrtFiles
{

	public static void main(String[] args)
	{
		final Settings config = Settings.parseArgs(args);
		Messages.showTitle(config);
		if (config.isPrintHelp()) {
			Messages.showHelp();
		} else  {
			List<Path> folders = SrtFunctions.getForderWithOnlySrtFile(config);
			SrtFunctions.deleteDirectories(config, folders);
		}
	}

}
