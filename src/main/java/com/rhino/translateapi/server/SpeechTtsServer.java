package com.rhino.translateapi.server;

import com.google.cloud.speech.v1.*;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.protobuf.ByteString;
import com.google.cloud.translate.Translate.TranslateOption;
import com.rhino.translateapi.properties.ApiBean;
import com.rhino.translateapi.server.impl.SpeechTtsServerImpl;
import com.rhino.translateapi.utils.DateUtils;
import com.rhino.translateapi.utils.IatUtils;
import com.rhino.translateapi.utils.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
public class SpeechTtsServer implements SpeechTtsServerImpl {

    private static final Logger logger = LoggerFactory.getLogger(SpeechTtsServer.class);

    @Autowired
    private ApiBean apiBean;

    @Override
    public String translateTts(String filePath,Integer type, Integer textLen, String content, String tl) {
        //即将访问的url
        HashMap<String, Object> hashMap = new HashMap<>();
        if (type == 1) {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = apiBean.getTts_url() + "?ie=UTF-8&total=1&idx=0&textlen=" + textLen + "&client=tw-ob&q=" + content + "&tl=" + tl;
            CloseableHttpResponse response = null;
            try {
                logger.info(url);
                HttpGet httpGet = new HttpGet(url);
                //执行请求
                response = httpClient.execute(httpGet);
                //打印请求的状态码  请求成功为200
                System.out.println(response.getStatusLine().getStatusCode());
                //打印请求的实体内容 返回json格式
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream contents = entity.getContent();
                    BufferedInputStream bin = new BufferedInputStream(contents);
                    String path = filePath + File.separator + "google" + File.separator + DateUtils.getDate2() + File.separator + System.currentTimeMillis() + ".mp3";
                    File file = new File(path);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    OutputStream out = new FileOutputStream(file);
                    int size = 0;
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((size = bin.read(buf)) != -1) {
                        len += size;
                        out.write(buf, 0, size);
                    }
                    bin.close();
                    out.close();
                    hashMap.put("success", 0);
                    hashMap.put("result", path);
                }
            } catch (Exception e) {
                e.printStackTrace();
                hashMap.put("success", -1);
                hashMap.put("result", "");
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JsonUtils.mapToJson(hashMap);
    }

    @Override
    public String translate(Integer type, String text, String source, String target) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (type == 1) {
            try {
                Translate translate = TranslateOptions.getDefaultInstance().getService();
                TranslateOption sourceTranslateOption = TranslateOption.sourceLanguage(source);
                TranslateOption targetTranslateOption = TranslateOption.targetLanguage(target);
                Translation translation = translate.translate(text, sourceTranslateOption, targetTranslateOption);
                hashMap.put("success", 0);
                hashMap.put("result", translation.getTranslatedText());
            } catch (Exception e) {
                hashMap.put("success", -1);
                hashMap.put("result", "");
                e.printStackTrace();
            }
        }
        return JsonUtils.mapToJson(hashMap);
    }

    /**
     * 语音识别
     *
     * @param audioType
     * @param filePath
     * @param heizi
     * @param languageCode
     * @param type
     * @return
     */
    @Override
    public String speechAsr(Integer audioType, String filePath, Integer heizi, String languageCode, Integer type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (type == 1) {
            try {
                SpeechClient speech = SpeechClient.create();
                Path path = Paths.get(filePath);
                byte[] data = Files.readAllBytes(path);
                ByteString audioBytes = ByteString.copyFrom(data);
                RecognitionConfig.AudioEncoding audioEncoding = RecognitionConfig.AudioEncoding.LINEAR16;
                if (audioType == 1) {
                    audioEncoding = RecognitionConfig.AudioEncoding.FLAC;
                }
                RecognitionConfig config = RecognitionConfig.newBuilder()
                        .setEncoding(audioEncoding) //必需对所有RecognitionAudio消息中发送的音频数据进行编码。 FLAC或LINEAR16
                        .setSampleRateHertz(heizi)   //有效值是：8000-48000。16000是最佳的。为获得最佳效果，请将音频源的采样率设置为16000 Hz。如果这是不可能的，使用音频源的本地采样率（而不是重新采样）。
                        .setLanguageCode(languageCode) //必需提供的音频语言作为BCP-47语言标签。例如：“en-US"音频中语言种类
                        .build();
                RecognitionAudio audio = RecognitionAudio.newBuilder()
                        .setContent(audioBytes)
                        .build();
                RecognizeResponse response = speech.recognize(config, audio);
                List<SpeechRecognitionResult> results = response.getResultsList();
                String ps = "";
                for (SpeechRecognitionResult result : results) {
                    SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                    ps = alternative.getTranscript();
                    logger.info("识别内容：" + ps);
                }
                hashMap.put("success", 0);
                hashMap.put("result", ps);
                speech.close();
            } catch (Exception e) {
                e.printStackTrace();
                hashMap.put("success", -1);
                hashMap.put("result", "");
            }
        } else if (type == 2) {
            //科大讯飞识别

        }
        return JsonUtils.mapToJson(hashMap);
    }

    @Override
    public String textSemantic(String userId, String content) {
        String url = "https://api.xfyun.cn/v1/aiui/v1/text_semantic";
        return IatUtils.requestTextSemantic(url,userId,content);
    }


}
