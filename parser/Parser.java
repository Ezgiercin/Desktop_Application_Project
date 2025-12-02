package SamProd_Desktop_Application_Project.parser;

import SamProd_Desktop_Application_Project.exception.DataImportException;
import java.io.File;
import java.util.List;


// generic parser interface it all have the method parse -- note that DataImportExpection is custom check folder exception ( Please use this exception  project wide) - Sam
public interface Parser<T> {
    List<T> parse(File file) throws DataImportException;
}