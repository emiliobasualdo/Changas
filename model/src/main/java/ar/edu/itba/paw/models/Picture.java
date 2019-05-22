package ar.edu.itba.paw.models;

import java.io.InputStream;

public class Picture {
    private InputStream imageByteStream;
    private long ownerId;

    public Picture(Picture.Builder changaPictureBuilder) {
        this.imageByteStream = changaPictureBuilder.getImageByteStream();
        this.ownerId = changaPictureBuilder.getOwnerId();
    }

    public InputStream getImageByteStream() {
        return imageByteStream;
    }

    public long getChangaId() {
        return ownerId;
    }

    public static class Builder {
        private InputStream imageByteStream;
        private long ownerId;

        public Builder(long ownerId, InputStream imageByteStream) {
            this.imageByteStream = imageByteStream;
            this.ownerId = ownerId;
        }

        public InputStream getImageByteStream() {
            return imageByteStream;
        }

        public long getOwnerId() {
            return ownerId;
        }

    }

}
