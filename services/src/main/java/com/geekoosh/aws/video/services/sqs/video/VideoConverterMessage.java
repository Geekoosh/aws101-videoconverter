package com.geekoosh.aws.video.services.sqs.video;

public class VideoConverterMessage {
    private String input;
    private String output;
    private boolean isPublic;
    private Long convertJobId;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Long getConvertJobId() {
        return convertJobId;
    }

    public void setConvertJobId(Long convertJobId) {
        this.convertJobId = convertJobId;
    }
}
