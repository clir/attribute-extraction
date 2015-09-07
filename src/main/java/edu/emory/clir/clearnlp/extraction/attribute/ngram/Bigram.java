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
import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.ISmoothing;
import edu.emory.mathcs.nlp.common.collection.tuple.ObjectDoublePair;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 7, 2015
 */
public class Bigram<T> extends AbstractNGram<T> {
	private static final long serialVersionUID = 4864082024485263042L;

	private Object2ObjectMap<String, Unigram<T>> m_count;
	
	public Bigram() {
		super(2, 0, null);
	}
	
	public Bigram(ISmoothing smoothing) {
		super(2, 0, smoothing);
	}
	
	public Bigram(int skip, ISmoothing smoothing) {
		super(2, skip, smoothing);
	}


	@Override
	public int getKeyCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void estimateLikelihood() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObjectDoublePair<T> getBest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addAux(int inc, T... words) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected double getLikelihoodAux(T... words) {
		// TODO Auto-generated method stub
		return 0;
	}

}
