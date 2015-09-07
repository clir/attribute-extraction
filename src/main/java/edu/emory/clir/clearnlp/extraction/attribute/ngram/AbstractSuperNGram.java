/**
 * Copyright 2015, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.clir.clearnlp.extraction.attribute.ngram;

import java.io.Serializable;

import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.ISmoothing;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 7, 2015
 */
public abstract class AbstractSuperNGram implements Serializable {
	private static final long serialVersionUID = 155499806239279854L;
	
	protected final int N;
	protected int i_totalCount;
	protected int i_keyCount;
	protected int i_skipOffset;
	protected ISmoothing smoothing;
	
	public AbstractSuperNGram(int n){ 
		N = n; 
		init(0, null);
	}
	
	public AbstractSuperNGram(int n, ISmoothing smoothing){ 
		N = n;
		init(0, smoothing);
	}
	
	public AbstractSuperNGram(int n, int skip, ISmoothing smoothing){ 
		N = n;
		init(skip, smoothing);
	}
	
	private void init(int skip,  ISmoothing smoothing){
		i_totalCount = 0;
		i_keyCount = 0;
		i_skipOffset = skip;
		setSmoothing(smoothing);
		
	}
	
	/* Getters */
	public int getSkipOffset(){
		return i_skipOffset;
	}
	
	public int getKeyCount(){
		return i_keyCount;
	}

	public int getTotalCount(){
		return i_totalCount;
	}
	
	public ISmoothing getSmoothing(){
		return this.smoothing;
	}
	
	/* Setters */
	public void setSmoothing(ISmoothing smoothing) {
		this.smoothing = smoothing;
	}
	
	/* Abstract methods */
	public void add(String... words){
		addAux(1, words);
	}
	public void add(int inc,String... words){
		if(words.length != N) throw new IllegalArgumentException("Invalid # of strings");
		addAux(inc, words);
	}
	abstract protected void addAux(int inc, String... words);

	abstract public void estimateLikelihood();
	abstract public double getLikelihood(String... words);
}
