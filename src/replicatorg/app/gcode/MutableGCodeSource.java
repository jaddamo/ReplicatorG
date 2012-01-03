package replicatorg.app.gcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import replicatorg.app.Base;
import replicatorg.machine.model.ToolheadAlias;
import replicatorg.model.GCodeSource;

/**
 * Class to encapsulate a GCode file, as well as all of the operations
 * that can be performed or associated with it.
 * 
 * 
 * @author Ted Brandston <ted@makerbot.com>
 *
 */
public class MutableGCodeSource implements GCodeSource {

	/// all gcode source, one command per line
	ArrayList<String> source = new ArrayList<String>();
	
	
	public MutableGCodeSource() { }
	
	
	public MutableGCodeSource(GCodeSource shallowCopy) {
		source.addAll(shallowCopy.asList());
	}
	
	
	public MutableGCodeSource(Collection<String> shallowCopy) {
		source.addAll(shallowCopy);
	}
	
	
	public MutableGCodeSource(File sourceFile) {
		String curline;
		
		if(sourceFile == null) {
			Base.logger.severe("MutableGCodeSource passed a null sourceFile");
			return;
		}
		
		try {
			BufferedReader bir = new BufferedReader(new FileReader(sourceFile));
			curline = bir.readLine();
			while (curline != null) {
				source.add(curline);
				curline = bir.readLine();
			}
			bir.close();
		} catch (IOException e) {
			System.err.println("couldnt read file " + sourceFile.getAbsolutePath());
			return;
		}
	}
	
	
	@Override
	public Iterator<String> iterator() {
		return source.iterator();
	}

	@Override
	public List<String> asList() {
		return source;
	}

	@Override
	public int getLineCount() {
		return source.size();
	}

	
	///appends a line to the current source
	public void add(String line) {
		source.add(line);
	}
	
	///appends an entire GCode source file.
	public void add(GCodeSource toAdd) {
		add(toAdd.asList());
	}
	
	///appends a list (array list, linked list, etc) 
	public void add(Collection<String> toAdd) {
		source.addAll(toAdd);
	}
	
	/// inserts the passed line at specified location, 0 indexed.
	public void add(int location, String line) {
		source.add(location, line);
	}
	
	/// inserts the passed gcode at specified location, 0 indexed.
	public void add(int location, GCodeSource toAdd) {
		add(location, toAdd.asList());
	}
	
	/// inserts the passed collection (list, linked list, etc) at specified location, 0 indexed.
	public void add(int location, Collection<String> toAdd) {
		source.addAll(location, toAdd);
	}
	
	/// writes the gcode to file, no path expansion or file testing happens before write attempt.
	public void writeToFile(File f) {

		try {
			FileWriter bwr = new FileWriter(f);

			for (String s : source) {
				bwr.write(s + "\n");
			}
			bwr.close();
		} catch (IOException e) {
			Base.logger.log(Level.SEVERE, "Could not write MutableGCodeSource to file.", e);
		}
	}
	
	/**
	 * This looks for and tries to remove sections that match what we expect from the start and end code
	 * It is not guaranteed to remove start and end code, just to try its best.
	 * @param source
	 * @return
	 */
	public void stripStartEndBestEffort() {
		//TODO: try harder
		Base.logger.finer("stripStartEndBestEffort ToDo: TryHarder" );
		return;
	}
	

	/// Runs through this gcode file, swapping all references to the the current toolhead 
	/// to instread reference the specified toolhead.  Alters select G, M and T Codes.
	public void changeToolhead(ToolheadAlias tool) {
		GCodeCommand gcode;
		int value;
		ArrayList<String> newSource = new ArrayList<String>(source.size());
		///FUTURE: create a syncronize block here someday
		for(Iterator<String> it = source.iterator(); it.hasNext(); )
		{
			String line = it.next();
			gcode = new GCodeCommand(line);

			if(gcode.hasCode('T'))
			{
				value = (int)gcode.getCodeValue('T');
				if(value != tool.number)
				{
					if(value == 0)
						line = line.replace("T0", "T1");
					else if(value == 1)
						line = line.replace("T1", "T0");
				}
			}
			if(gcode.getCodeValue('G') == 54 && !(tool.getRecallOffsetGcodeCommand().equals("G54")))
			{
				line = line.replace("G54", tool.getRecallOffsetGcodeCommand());
			}
			if(gcode.getCodeValue('G') == 55 && !(tool.getRecallOffsetGcodeCommand().equals("G55")))
			{
				line = line.replace("G55", tool.getRecallOffsetGcodeCommand());
			}
			newSource.add(line);
		}
		source = newSource;
	}

	
	/// Make a deep copy of this MutableGCodeSource and returns it to the caller.
	public MutableGCodeSource copy() {
		MutableGCodeSource newSource = new MutableGCodeSource();
		for(String line : source)
			newSource.add(new String(line));
		return newSource;
	}
}
