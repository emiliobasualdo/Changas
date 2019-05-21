package ar.edu.itba.paw.models;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ChangaPicture {
    private InputStream imageByteStream;
    private long changaId;

    public ChangaPicture(ChangaPicture.Builder changaPictureBuilder) {
        this.imageByteStream = changaPictureBuilder.getImageByteStream();
        this.changaId = changaPictureBuilder.getChangaId();
    }

    public InputStream getImageByteStream() {
        return imageByteStream;
    }

    public long getChangaId() {
        return changaId;
    }

    public static class Builder {
        private InputStream imageByteStream;
        private long changaId;

        public Builder(long changaId, InputStream imageByteStream) {
            this.imageByteStream = imageByteStream;
            this.changaId = changaId;
        }

        public InputStream getImageByteStream() {
            return imageByteStream;
        }

        public long getChangaId() {
            return changaId;
        }

    }

}
