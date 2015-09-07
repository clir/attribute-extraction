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
import edu.emory.mathcs.nlp.common.collection.tuple.ObjectDoublePair;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 7, 2015
 */
public abstract class AbstractNGram<T> implements Serializable {
	private static final long serialVersionUID = 155499806239279854L;
	
	protected final int N;
	protected int i_totalCount;
	protected int i_skipOffset;
	
	protected T best;
	protected ISmoothing smoothing;
	
	public AbstractNGram(int n){ 
		N = n; 
		init(0, null);
	}
	
	public AbstractNGram(int n, ISmoothing smoothing){ 
		N = n;
		init(0, smoothing);
	}
	
	public AbstractNGram(int n, int skip, ISmoothing smoothing){ 
		N = n;
		init(skip, smoothing);
	}
	
	private void init(int skip,  ISmoothing smoothing){
		i_totalCount = 0;
		i_skipOffset = skip;
		setSmoothing(smoothing);
		
	}
	
	/* Getters */
	public int getN(){
		return N;
	}
	
	public int getSkipOffset(){
		return i_skipOffset;
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
	abstract public int getKeyCount();
	abstract public void estimateLikelihood();
	abstract public ObjectDoublePair<T> getBest();
	
	@SuppressWarnings("unchecked")
	public void add(T... words){
		addAux(1, words);
	}
	
	@SuppressWarnings("unchecked")
	public void add(int inc, T... words){
		if(words.length != N) throw new IllegalArgumentException("Invalid # of strings");
		addAux(inc, words);
	}
	
	@SuppressWarnings("unchecked")
	abstract protected void addAux(int inc, T... words);
	
	@SuppressWarnings("unchecked")
	public double getLikelihood(T... words){
		if(words.length != N) throw new IllegalArgumentException("Invalid # of strings");
		return getLikelihoodAux(words);
	}
	
	@SuppressWarnings("unchecked")
	abstract protected double getLikelihoodAux(T... words);
}
