package es.udc.cartolab.gvsig.eielforms.formgenerator;

public abstract class FormReader {
	
	public abstract String getFormDefinition(String formName) throws FormException;

}
