package com.rhino.translateapi.bena;

/**
 * 语音合成参数实体
 */
public class TranslateTts {
    private Integer type; //翻译类型
    private String textLen; //合成文字长度
    private String content; //需要合成文字内容
    private String tl; //合成的语音国家

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTextLen() {
        return textLen;
    }

    public void setTextLen(String textLen) {
        this.textLen = textLen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTl() {
        return tl;
    }

    public void setTl(String tl) {
        this.tl = tl;
    }
}
