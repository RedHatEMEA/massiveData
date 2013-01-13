package elucidusFilterPdfTests;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.pdf.GeneratorPdf;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;


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

	private void debugPrintAttributes(List<Item> itemList, int maxValueLength) {
		for (Item i : itemList) {
			Hashtable<String, Object> attributes = i.getContents();
			
			for (String key : new TreeSet<String>(attributes.keySet())) {
				String value = attributes.get(key).toString();
 
				if (value.length() > maxValueLength) {
					value = value.substring(0, maxValueLength);
					value = value.replace("\n", "");
					value = value.replace("\r", "");
				}

				System.out.println(key + " = " + value);
			}
		}
	}  

	@Theory 
	public void parseTestingResources(File testingFile) throws GenerationException{
		System.out.println();
		System.out.println("Testing: " + testingFile.getAbsoluteFile());
		System.out.println("---------------------------------------------");

		Assert.assertNotNull(testingFile);

		GeneratorPdf fpdf = new GeneratorPdf();
		List<Item> filteredAttributes = fpdf.generate(testingFile);

		this.debugPrintAttributes(filteredAttributes, 128);
	}
}
