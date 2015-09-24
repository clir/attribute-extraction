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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.emory.clir.clearnlp.util.FileUtils;
import edu.emory.clir.clearnlp.util.constant.CharConst;

/**
 * @author 	Yu-Hsin(Henry) Chen ({@code yu-hsin.chen@emory.edu})
 * @version	1.0
 * @since 	Sep 21, 2015
 */
public abstract class AbstractCorpusRecontructor implements Runnable{
	protected CorpusType type;
	protected List<String> l_filePaths;
	protected Set<String> l_extensions;
	protected String inputDirPath, outputDirPath;
	
	public AbstractCorpusRecontructor(CorpusType type, String in_dir, String out_dir){
		this.type = type;
		setIODirecotries(in_dir, out_dir);
	}
	
	public AbstractCorpusRecontructor(CorpusType type, List<String> filePaths, String in_dir, String out_dir){
		this.type = type;
		inputDirPath = in_dir;
		outputDirPath = out_dir;
		l_filePaths = filePaths;
	}
	
	public AbstractCorpusRecontructor(CorpusType type, String in_dir, String out_dir, Set<String> extension){
		this.type = type;
		l_extensions = extension;
		setIODirecotries(in_dir, out_dir);
	}
	
	public void setFilePaths(List<String> filePaths){
		l_filePaths = filePaths;
	}
	
	protected void setIODirecotries(String in_dir, String out_dir){
		inputDirPath = in_dir;	outputDirPath = out_dir;
		l_filePaths = getFilePaths();
		
		File dir; String dir_path;
		for(String filePath : l_filePaths){
			dir_path = filePath.substring(0, filePath.lastIndexOf(CharConst.FW_SLASH));
			dir_path = getOutputPath(dir_path);
			if(!(dir = new File(dir_path)).exists())	dir.mkdirs();
		}
	}
	
	public String getInputDir(){
		return inputDirPath;
	}
	
	public String getOutputDir(){
		return outputDirPath;
	}
	
	public CorpusType getCorpusType(){
		return type;
	}
	
	protected List<String> getFilePaths(){
		if(l_extensions == null || l_extensions.size() == 0)
			return FileUtils.getFileList(inputDirPath, "", true);
		
		List<String> list = new ArrayList<>();
		for(String ext : l_extensions)
			list.addAll(FileUtils.getFileList(inputDirPath, ext, true));
		return list;
	}
	
	protected String getOutputPath(String inputPath){
		int index = inputPath.indexOf(inputDirPath);
		if(index >= 0)
			return outputDirPath + inputPath.substring(index + inputDirPath.length());
		return outputDirPath + FileUtils.getBaseName(inputPath);
	}
	
	abstract public void reconstruct(List<String> filePaths);
	public void reconstruct(){	
		reconstruct(l_filePaths);	
	};
	
	public abstract AbstractCorpusRecontructor clone(); 

	@Override
	public void run() {
		reconstruct(l_filePaths);
	}
}
