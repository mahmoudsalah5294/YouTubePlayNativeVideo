package com.mahmoudsalah.youtubeplaynativevideo;

public class ChannelData {
    private String channelurl,imageurl,channelTitle;

    public ChannelData(String channelurl, String imageurl, String channelTitle) {
        this.channelurl = channelurl;
        this.imageurl = imageurl;
        this.channelTitle = channelTitle;
    }

    public ChannelData() {
    }

    public String getChannelurl() {
        return channelurl;
    }

    public void setChannelurl(String channelurl) {
        this.channelurl = channelurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
