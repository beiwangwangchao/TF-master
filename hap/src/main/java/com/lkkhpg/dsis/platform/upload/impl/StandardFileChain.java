/*
 *
 */
package com.lkkhpg.dsis.platform.upload.impl;

import java.util.ArrayList;
import java.util.List;

import com.lkkhpg.dsis.platform.upload.FileChain;
import com.lkkhpg.dsis.platform.upload.FileInfo;
import com.lkkhpg.dsis.platform.upload.FileProcessor;
import com.lkkhpg.dsis.platform.upload.Uploader;

/**
 * 处理链上下文.
 * 
 * @author xiaohua
 *
 */
public class StandardFileChain implements FileChain {
    private List<FileInfo> fileInfos = null;

    private List<FileProcessor> processors = new ArrayList<FileProcessor>();

    // 记录当前执行的Processor
    private int processorIndex = 0;

    private Uploader uploader;

    public StandardFileChain(List<FileInfo> fileInfos, Uploader uploader) {
        this.fileInfos = fileInfos;
        this.uploader = uploader;
    }

    public void doProcess() throws Exception {
        if (processors != null && processors.size() > 0 && processorIndex < processors.size()) {
            processors.get(processorIndex++).process(this, uploader, fileInfos);
        }
    }

    public void addProcessor(FileProcessor processor) {
        processors.add(processor);
    }

}
