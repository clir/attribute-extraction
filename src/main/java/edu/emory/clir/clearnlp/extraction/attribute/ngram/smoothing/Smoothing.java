package edu.emory.clir.clearnlp.extraction.attribute.ngram.smoothing;

import edu.emory.clir.clearnlp.extraction.attribute.ngram.AbstractNGram;

public interface Smoothing {
	
	
	
	void estimateMaximumLikelihoods(AbstractNGram ngram);
	
	
	double getUnseenLikelihood();
	
	Smoothing createInstance();
	


}
