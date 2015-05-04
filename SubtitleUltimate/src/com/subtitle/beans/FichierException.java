package com.subtitle.beans;

public class FichierException extends Exception {
	private String message;

	public FichierException(String message) {
		super();
		this.message = message;
	}
	
}
