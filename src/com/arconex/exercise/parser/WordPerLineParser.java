package com.arconex.exercise.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {{@link FileParser}}
 *
 * Takes in a File, performs some basic validation and read it line by line into a List<String>
 */
public class WordPerLineParser implements FileParser {

	/**
	 * Validates the input dictionary file.
	 *
	 * File must be a file and not a directory and must be readable.
	 *
	 * @param dictionarFile
	 */
	private void validateFile(final File file) {
		if (file == null || file.isDirectory()) {
			throw new IllegalArgumentException("Path should point to a file.");
		}
		if (!file.canRead()) {
			throw new IllegalArgumentException("Unable to read file");
		}
	}

	/**
	 * See {{@link FileParser#parse(File)}}
	 */
	@Override
	public List<String> parse(final File file) throws FileNotFoundException, IOException {

		validateFile(file);

		final List<String> words = Collections.synchronizedList(new ArrayList<String>());

		final FileReader fileReader = new FileReader(file);
		final BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			if (!line.isEmpty()) {
				words.add(line);
			}
		}
		bufferedReader.close();
		return words;
	}

}

