package es.udc.cartolab.gvsig.eielforms.formgenerator;

public class FormException extends Exception {

	public FormException(Exception e) {
		super(e);
	}
	
	public FormException() {
		super();
	}
	
	public FormException(String msg) {
		super(msg);
	}
}
