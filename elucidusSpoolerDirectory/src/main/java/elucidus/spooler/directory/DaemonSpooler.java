package elucidus.spooler.directory;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.GeneratorFactory;
import org.elucidus.generation.IGenerator;
import org.elucidus.generation.base.testHelpers.DefaultFallbackGenerator;
import org.elucidus.generation.email.GeneratorEmail;
import org.elucidus.generation.pdf.GeneratorPdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class DaemonSpooler implements Runnable {
	private static final transient Logger LOG = LoggerFactory.getLogger(DaemonSpooler.class);
	private final Vector<File> spoolingDirectories = new Vector<File>();
	private final GeneratorFactory gf;
	
	public DaemonSpooler() {
		this.gf = new GeneratorFactory();
		this.gf.registerGeneratorFiletype(GeneratorPdf.class, "pdf");
		this.gf.registerGeneratorFiletype(GeneratorEmail.class, "email"); 
		this.gf.setDefaultFallbackGenerator(new DefaultFallbackGenerator());  
	}
	 
	public void addSpoolingDirectory(String path) {
		this.spoolingDirectories.add(new File(path)); 
	}
	
	public void start() {
		new Thread(this).start(); 
	}  
	
	public void run() {
		while(true) {
			LOG.info("Spooling");
			 
			try {
				for (File spoolingDirectory : this.spoolingDirectories) {
					if (!spoolingDirectory.exists()) {
						LOG.error("Location for spooling does not exist: " + spoolingDirectory.getAbsolutePath());
						continue;
					}
					
					if (!spoolingDirectory.isDirectory()) {
						LOG.error("Location for spooling is not a directory: " + spoolingDirectory.getAbsolutePath());
						continue;
					} 
					
					for (File f : spoolingDirectory.listFiles()) {
						if (f.isHidden()) {
							continue;  
						}
						 
						if (!f.isFile()) {
							continue;
						}

						spoolFile(f); 
					}
				}
				
				// TODO Mark files as spooled, delete them, whatever.
				
				Thread.sleep(10000);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} 
	} 
	
	private void spoolFile(File f) throws GenerationException { 
		LOG.info("Sppoling file: " + f.getAbsolutePath()); 
		 
		IGenerator generator = gf.getGeneratorForFile(f);
		 
		LOG.info("Using " + generator.getClass().getSimpleName() + " for file " + f.getAbsolutePath());
 
		List<Item> items = generator.generate(f);
		
		for (Item i : items) { 
			LOG.debug(i.toString()); 
		}
		
		
	}

}
