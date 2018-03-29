package com.rhino.translateapi.server.impl;

public interface SpeechTtsServerImpl {

    String translateTts(String path,Integer type, Integer textLen, String content, String tl);

    String translate(Integer type,String text,String source,String target);

    String speechAsr(Integer audioType,String filePath, Integer heizi, String languageCode,Integer type);

    /**
     * 语义理解
     * @param userId
     * @param content
     * @return
     */
    String textSemantic(String userId, String content);
}
