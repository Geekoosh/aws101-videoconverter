package com.geekoosh.aws.video.services.video.ffmpeg;

import com.geekoosh.aws.video.services.video.VideoConverter;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoConverterFFMPEG implements VideoConverter {
    @Autowired
    private FFMPEGConfig ffmpegConfig;

    private String runFFMPEG(String cmdTemplate, Map<String, String> values) throws Exception {
        StrSubstitutor sub = new StrSubstitutor(values);
        String cmd = sub.replace(cmdTemplate);

        StringBuffer output = new StringBuffer();
        Process p;
        p = Runtime.getRuntime().exec(cmd);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String line;
        while ((line = reader.readLine())!= null) {
            output.append(line + "\n");
        }
        reader.close();

        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = reader.readLine())!= null) {
            output.append(line + "\n");
        }

        reader.close();

        return output.toString();
    }

    private String generateOutputName(String basePath, String extension) {
        return Paths.get(basePath, UUID.randomUUID().toString() + "." + extension).toString();
    }

    private Map<String, String> mapValues(String input, String output) {
        Map<String, String> valuesMap = new HashMap();
        valuesMap.put("ffmpeg", ffmpegConfig.getFfmpegPath());
        valuesMap.put("input", input);
        valuesMap.put("output", output);

        return valuesMap;
    }

    @Override
    public String generateThumbnail(String path) throws Exception {
        Map<String, String> values = mapValues(path, generateOutputName(ffmpegConfig.getWorkingFolder(), "png"));
        System.out.println(runFFMPEG(ffmpegConfig.getThumbnailCmd(), values));
        return values.get("output");
    }

    @Override
    public String convertHighResolution(String path) throws Exception {
        Map<String, String> values = mapValues(path, generateOutputName(ffmpegConfig.getWorkingFolder(), "mp4"));
        System.out.println(runFFMPEG(ffmpegConfig.getHighResCmd(), values));
        return values.get("output");
    }

    @Override
    public String convertLowResolution(String path) throws Exception {
        Map<String, String> values = mapValues(path, generateOutputName(ffmpegConfig.getWorkingFolder(), "mp4"));
        System.out.println(runFFMPEG(ffmpegConfig.getLowResCmd(), values));
        return values.get("output");
    }
}
