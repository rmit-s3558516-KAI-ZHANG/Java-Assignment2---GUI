package OwnInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import application.Person;
import application.Relationships;

public interface WriteAndReadTextFile {
    public abstract void write_to_file() throws IOException;
    public abstract ArrayList<String> ReadFromFile(File file) throws IOException;
}
