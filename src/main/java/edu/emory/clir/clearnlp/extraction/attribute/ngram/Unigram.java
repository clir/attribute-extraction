package edu.emory.clir.clearnlp.extraction.attribute.ngram;

import edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing.ISmoothing;

public class Unigram implements ISmoothing {

	@Override
	public void estimateMaximumLikelihoods(AbstractNGram ngram) {
		
	}

	@Override
	public double getUnseenLikelihood() {
		return 0;
	}

	@Override
	public ISmoothing createInstance() {
		return null;
	}

	
	
	
}
