package com.arconex.exercise.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileParser {

	/**
	 * Parses the input dictionary file into a List of words.
	 *
	 * @param dictionaryFile
	 * @return
	 */
	List<String> parse(File dictionaryFile) throws FileNotFoundException, IOException;

}

