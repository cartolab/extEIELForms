package es.udc.cartolab.gvsig.eielforms.formgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FormFileReader extends FormReader
{

  private final static String path = "gvSIG"+File.separator+"extensiones"+File.separator+"es.udc.cartolab.gvsig.eielforms"
	+File.separator+"forms"+File.separator; 


  public String getFormDefinition(String formName) throws FormException {
	  File file = new File(path  + formName.toLowerCase() + ".xml");
	  StringBuffer sb = new StringBuffer();
		  try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (FileNotFoundException e) {
			throw new FormException(e);
		} catch (IOException e) {
			throw new FormException(e);
		}
		return sb.toString();
  }
}


