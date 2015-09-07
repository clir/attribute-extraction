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

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.ISmoothing;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 7, 2015
 */
public abstract class AbstractNGram<T> extends AbstractSuperNGram{
	private static final long serialVersionUID = 7598468521841113603L;

	protected Object2ObjectMap<String, AbstractNGram<?>> m_ngrams;
	
	public AbstractNGram(int n, ISmoothing smoothing){
		super(n, smoothing);
		m_ngrams = new Object2ObjectOpenHashMap<>();
	}
	
	/* Getters */
	public Object2ObjectMap<String, AbstractNGram<?>> getNGramsMap(){
		return m_ngrams;
	}
	
	/* Abstract methods */
	abstract public T getBest();
	abstract public T getUnigramMap();
}
