package elucidusFilterPdfTests;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import elucidusFilterPdf.FilterPdf;

@RunWith(Theories.class)
public class ParseTestingResources {

	@DataPoints
	public static File[] getTestingFiles() {
		File testingResourcesDirectory = new File("src/test/resources");

		return testingResourcesDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".pdf");
			}
		});
	}

	private void debugPrintAttributes(Map<String, String> attributes, int maxValueLength) {
		for (String key : new TreeSet<String>(attributes.keySet())) {
			String value = attributes.get(key);

			if (value.length() > maxValueLength) {
				value = value.substring(0, maxValueLength);
				value = value.replace("\n", "");
				value = value.replace("\r", "");
			}

			System.out.println(key + " = " + value);
		}
	}

	@Theory
	public void parseTestingResources(File testingFile) {
		System.out.println();
		System.out.println("Testing: " + testingFile.getAbsoluteFile());
		System.out.println("---------------------------------------------");

		Assert.assertNotNull(testingFile);

		FilterPdf fpdf = new FilterPdf();
		Map<String, String> filteredAttributes = fpdf.filterFile(testingFile);

		this.debugPrintAttributes(filteredAttributes, 128);
	}
}
