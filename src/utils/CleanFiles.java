package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONObject;

public class CleanFiles {

	public static void removeTextFromTxt(String filename) throws IOException {

		File file = new File(filename);
		File temp = File.createTempFile("file", ".txt", file.getParentFile());
		String charset = "UTF-8";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
		for (String line; (line = reader.readLine()) != null;) {
			if (line.contains("##ENGLISH_NER_CORENLP##")) {
				break;
			}
			writer.println(line);
		}
		reader.close();
		writer.close();
		file.delete();
		temp.renameTo(file);

	}

	public static void removeTextFromJson(String filename) throws IOException {

		FileReader file = new FileReader(filename);
		BufferedReader reader = new BufferedReader(file);

		String key = "";
		String line = reader.readLine();

		while (line != null) {
			key += line;
			line = reader.readLine();
		}

		String jsonString = key;
		JSONObject obj = new JSONObject(jsonString);
		obj.remove(
				"org.gcube.dataanalysis.wps.statisticalmanager.synchserver.mappedclasses.transducerers.ENGLISH_NER_CORENLP");
		StringWriter out = new StringWriter();
		String jsonStringOutput = obj.toString();
		System.out.println(jsonStringOutput);

	}

}
