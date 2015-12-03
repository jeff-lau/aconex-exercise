package com.arconex.exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Main {

	public static final String DICTIONARY_PROPERTY = "dictionary";


	public static void main(final String[] args) {
		// TODO Auto-generated method stub

		final String inputDictionaryPath = System.getProperty(DICTIONARY_PROPERTY);


		final File dictionary = new File(inputDictionaryPath);

		if (dictionary.isFile()) {

			try {
				final BufferedReader br = new BufferedReader(new FileReader(dictionary));

				System.out.println(br.readLine());

			} catch (final Exception e){

			}

		} else {

			System.out.println("Input Dictionary is not a file");
			System.exit(-1);

		}


	}

}
