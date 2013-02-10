package elucidus.spooler.directory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final transient Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		if (args.length != 1) {
			LOG.error("Spooler requires 1 argument: the directory from which you want to spool from!");
		} else { 
			DaemonSpooler spooler = new DaemonSpooler();
			spooler.addSpoolingDirectory(args[0]); 
			spooler.start(); 
		} 
	}  
}  
