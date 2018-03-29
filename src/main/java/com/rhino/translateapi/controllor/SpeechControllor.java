package com.rhino.translateapi.controllor;

import com.rhino.translateapi.bena.TranslateTts;
import com.rhino.translateapi.server.impl.SpeechTtsServerImpl;
import com.rhino.translateapi.utils.EncodUtils;
import com.rhino.translateapi.utils.FileUpload;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 语音处理接口
 */
@RestController
@RequestMapping(value = "/speech")
@Api(value = "语音处理接口")
public class SpeechControllor {

    private static final Logger logger= LoggerFactory.getLogger(SpeechControllor.class);

    @Autowired
    private SpeechTtsServerImpl speechTtsServer;

    @ApiOperation(value="语音识别", notes="将语音识别为文字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "识别使用的接口来源,1:谷歌",required = true,paramType = "query",dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name = "audioType",value = "音频格式，FLAC 传1 ，RAW 传 2",required = true,paramType = "query",dataType = "int",defaultValue = "2"),
            @ApiImplicitParam(name = "upfile",value = "要识别的视频源，格式FLAC，RAW",required = true,paramType = "form",dataType = "file"),
            @ApiImplicitParam(name = "heizi",value = "合成音频采样率有效值是：8000-48000。16000是最佳的",required = true,paramType = "query",dataType = "int",defaultValue = "16000"),
            @ApiImplicitParam(name = "languageCode",value = "音频国家编码",required = true,dataType = "String",defaultValue = "cmn-Hans-CN",paramType = "query")
    })
    @RequestMapping(value = "speechAsr",method = RequestMethod.POST,consumes = "multipart/form-data")
    public String speechAsr(@RequestParam Integer audioType,@RequestParam Integer type,@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request,@RequestParam Integer heizi,@RequestParam String languageCode) {
        logger.info("语音识别");
        Map<String, Object> param = new HashMap<>();
        String filePath = FileUpload.uploadImage(upfile,request,param);
        logger.info(filePath);
        if ("true".equals(param.get("success").toString())) {
            //文件本地上传成功
            return speechTtsServer.speechAsr(audioType,filePath,heizi,languageCode,type);
        } else {
            //文件本地上传失败
            String msg = param.get("msg").toString();
            return "[{\"result\":\""+ msg +"\",\"success\":-1}]";
        }
    }

    @ApiOperation(value="语言翻译", notes="将一种语言翻译成另外一种语言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "翻译使用的接口来源,1:谷歌",required = true,paramType = "query",dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name = "text",value = "需要翻译的文本内容",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "source",value = "翻译内容的国家编码,en:英国",required = true,dataType = "String",paramType = "query",defaultValue = "en"),
            @ApiImplicitParam(name = "target",value = "翻译后的国家编码,zh-CN:中国",required = true,dataType = "String",paramType = "query",defaultValue = "zh-CN")
    })
    @RequestMapping(value = "translate",method = RequestMethod.POST)
    public String translate(@RequestParam Integer type, @RequestParam String text, @RequestParam String source, @RequestParam String target) {
        text = EncodUtils.encodeStr(text);
        return speechTtsServer.translate(type,text,source,target);
    }

    @ApiOperation(value="语音合成", notes="将文字合成语音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "合成使用的接口来源，1:谷歌",required = true,paramType = "query",dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name = "textLen",value = "合成文字长度",required = true,paramType = "query",dataType = "int",defaultValue = "32"),
            @ApiImplicitParam(name = "content",value = "需要合成文字内容",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "tl",value = "合成的语音国家简称",required = true,dataType = "String",defaultValue = "cmn-Hans-CN",paramType = "query")
    })
    @RequestMapping(value = "translateTts",method = RequestMethod.GET)
    public String translateTts(@RequestParam Integer type, @RequestParam Integer textLen, @RequestParam String content, @RequestParam String tl,HttpServletRequest request) {
        logger.info("语音合成");
        content = EncodUtils.encodeStr(content);
        String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "speech";
        return speechTtsServer.translateTts(path,type,textLen,content,tl);
    }

    @ApiOperation(value="语义理解", notes="识别文字意图，并返回相应说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户ID，用于多轮对话",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "content",value = "需要识别的内容",required = true,dataType = "String",paramType = "query")
    })
    @RequestMapping(value = "textSemantic",method = RequestMethod.GET)
    public String textSemantic(@RequestParam String userId,@RequestParam String content) {
        logger.info("语义理解");
        content = EncodUtils.encodeStr(content);
        return speechTtsServer.textSemantic(userId,content);
    }

}
