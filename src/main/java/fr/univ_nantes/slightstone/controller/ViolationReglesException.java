package fr.univ_nantes.slightstone.controller;

public class ViolationReglesException extends Exception {

	private static final long serialVersionUID = 2673525915469920482L;

	public ViolationReglesException() {
		super();
	}
	
	public ViolationReglesException(String message) {
		super(message);
	}
}
