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
package edu.emory.clir.clearnlp.extraction.attribute.corpus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.emory.clir.clearnlp.collection.pair.IntIntPair;
import edu.emory.clir.clearnlp.dependency.DEPTree;
import edu.emory.clir.clearnlp.extraction.attribute.utils.chunk.AbstractChucker;
import edu.emory.clir.clearnlp.extraction.attribute.utils.chunk.Chunk;
import edu.emory.clir.clearnlp.extraction.attribute.utils.chunk.EnglishProperNounChunker;
import edu.emory.clir.clearnlp.reader.AbstractReader;
import edu.emory.clir.clearnlp.reader.TSVReader;
import edu.emory.clir.clearnlp.util.IOUtils;
import edu.emory.clir.clearnlp.util.Joiner;
import edu.emory.clir.clearnlp.util.Splitter;
import edu.emory.clir.clearnlp.util.constant.StringConst;
import edu.emory.clir.clearnlp.util.lang.TLanguage;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 22, 2015
 */
public class EntityTokenCorpusReconstructor extends AbstractCorpusRecontructor{

	private NLPDecoder decoder;
	private Set<String> s_NERLables;
	private AbstractReader<DEPTree> reader;
	
	public EntityTokenCorpusReconstructor(CorpusType type, String in_dir, String out_dir) {
		super(type, in_dir, out_dir);	init();
	}
	
	public EntityTokenCorpusReconstructor(CorpusType type, String in_dir, String out_dir, Set<String> extensions) {
		super(type, in_dir, out_dir, extensions);	init();
	}
	
	public EntityTokenCorpusReconstructor(CorpusType type, String in_dir, String out_dir, Set<String> extensions, Set<String> NERLabels) {
		super(type, in_dir, out_dir, extensions);
		s_NERLables = NERLabels;	init();
	}
	
	public EntityTokenCorpusReconstructor(CorpusType type, List<String> filePaths, String in_dir, String out_dir, Set<String> NERLabels){
		super(type, filePaths, in_dir, out_dir);
		s_NERLables = NERLabels;	init();
	}
	
	private void init(){
		switch (type) {
			case RAW:	decoder = new NLPDecoder(TLanguage.ENGLISH);				break;
			case TSV:	reader = new TSVReader(0, 1, 2, 3, 9, 4, 5, 6, 7, 8); 		break;
		}
	}

	@Override
	public void reconstruct(List<String> filePaths) {
		
		String line;
		DEPTree tree; 
		List<Chunk> chunks; 
		List<DEPTree> trees;
		List<String> sentence;
		AbstractChucker chunker = new EnglishProperNounChunker(s_NERLables);
		
		InputStream input;
		PrintWriter writer;
		BufferedReader raw_reader;
		
		for(String filePath : filePaths){
			try {
				input = IOUtils.createFileInputStream(filePath);
				writer = new PrintWriter(IOUtils.createBufferedPrintStream(getOutputPath(filePath)));
				
				switch(type){
					case RAW:
						raw_reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(input)));
						raw_reader.readLine();
						
						while( (line = raw_reader.readLine()) != null){
							trees = decoder.toDEPTrees(Splitter.splitTabs(line)[1]);
							
							for(DEPTree t : trees){
								chunks = chunker.getChunk(t);
								sentence = reconstructAux(t, chunks);
								writer.println(Joiner.join(sentence, StringConst.SPACE));
							}
						}
						
						break;
					case TSV:
						reader.open(new BufferedInputStream(input));
						
						while( (tree = reader.next()) != null){
							chunks = chunker.getChunk(tree);
							sentence = reconstructAux(tree, chunks);
							writer.println(Joiner.join(sentence, StringConst.SPACE));					
						}
						break;
				}
				
				input.close();
				writer.close();
			} catch (Exception e) { e.printStackTrace(); }
		}
	}
	
	private List<String> reconstructAux(DEPTree tree, List<Chunk> chunks){
		IntIntPair span;
		List<String> sentence = new ArrayList<>();
		int i, j = 0, t_size = tree.size(), c_size = chunks.size();
		
		List<String> entityToken;
		span = (j < c_size)? chunks.get(j).getChunkSpan() : null;
		for(i = 1; i < t_size; i++){			
			if(span != null && span.i1 == i){
				entityToken = chunks.get(j).getStrippedChunkNodes().stream()
							.map(n -> n.getWordForm()).collect(Collectors.toList());
				
				sentence.add(Joiner.join(entityToken, StringConst.EMPTY));
				span = (j < c_size)? chunks.get(j).getChunkSpan() : null;
				i = span.i2;	continue;
			}
			sentence.add(tree.get(i).getLemma());
		}
		return sentence;
	}

	@Override
	public AbstractCorpusRecontructor clone() {
		if(l_extensions != null && s_NERLables != null)
			return new EntityTokenCorpusReconstructor(type, inputDirPath, outputDirPath, new HashSet<String>(l_extensions), new HashSet<String>(s_NERLables));
		else if(l_extensions == null)
			return new EntityTokenCorpusReconstructor(type, inputDirPath, outputDirPath, null, new HashSet<String>(s_NERLables));
		else if(s_NERLables == null)
			return new EntityTokenCorpusReconstructor(type, inputDirPath, outputDirPath, new HashSet<String>(l_extensions), null);
		else 
			return new EntityTokenCorpusReconstructor(type, inputDirPath, outputDirPath, null, null);
	}
}
