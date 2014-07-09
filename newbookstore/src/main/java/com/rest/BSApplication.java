package com.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class BSApplication extends Application {
	private Set<Object> singletons = new HashSet<>();
	private Set<Class<?>> empty = new HashSet<>();
	public BSApplication(){
		singletons.add(new BookService());
	}
	@Override
	public Set<Class<?>> getClasses(){
		return empty;
	}
	@Override
	public Set<Object> getSingletons(){
		return singletons;
	}
	
}
