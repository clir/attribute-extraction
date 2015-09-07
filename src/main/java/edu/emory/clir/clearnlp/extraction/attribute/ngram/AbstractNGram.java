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
import java.util.Set;

import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.Smoothing;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 7, 2015
 */
public abstract class AbstractNGram<T> implements Serializable{
	private static final long serialVersionUID = 7598468521841113603L;

	private int i_totalCount;
	private int i_keyCount;
	private Smoothing smoothing;
	private Object2ObjectMap<String, AbstractNGram> m_ngrams;
	
	public AbstractNGram(){}
	
	
	public AbstractNGram(Smoothing smoothing){
		m_ngrams = new Object2ObjectOpenHashMap<>();
		setSmoothing(smoothing);
	}
	
	public abstract T getBest();
	
	public abstract T getUnigramMap();

	public abstract Set<String> getWordSet();
	
	public void estimateLikelihood(){
		smoothing.estimateMaximumLikelihoods(this);
	}
	
	
	public double getLikelihood(String... words){
		return 0;
		
	}
	
	public void add(long count,String... words){
		
	}

	
	public Object2ObjectMap<String, AbstractNGram> getNGramsMap(){
		return m_ngrams;
	}
	public int getTotalCount(){
		return i_totalCount;
	}
	
	public int getKeyCount(){
		return i_keyCount;
	}
	
	public Smoothing getSmoothing(){
		return this.smoothing;
	}


	public void setTotalCount(int i_totalCount) {
		this.i_totalCount = i_totalCount;
	}


	public void setKeyCount(int i_keyCount) {
		this.i_keyCount = i_keyCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSmoothing(Smoothing smoothing) {
		this.smoothing = smoothing;
	}
	
	
	
}