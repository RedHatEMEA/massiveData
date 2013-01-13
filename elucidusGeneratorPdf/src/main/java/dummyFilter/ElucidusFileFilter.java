package dummyFilter;

import java.io.File;
import java.util.Map;

public interface ElucidusFileFilter {
	public Map<String, String> filterFile(File fileToFilter);

}
