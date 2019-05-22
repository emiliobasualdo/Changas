package ar.edu.itba.paw.models;

import java.io.InputStream;

public class Picture {
    private byte[] imageByteStream;
    private long ownerId;

    public Picture(Picture.Builder changaPictureBuilder) {
        this.imageByteStream = changaPictureBuilder.getImageByteStream();
        this.ownerId = changaPictureBuilder.getOwnerId();
    }

    public byte[] getImageByteStream() {
        return imageByteStream;
    }

    public long getChangaId() {
        return ownerId;
    }

    public static class Builder {
        private byte[] imageByteStream;
        private long ownerId;

        public Builder(long ownerId, byte[] imageByteStream) {
            this.imageByteStream = imageByteStream;
            this.ownerId = ownerId;
        }

        public byte[] getImageByteStream() {
            return imageByteStream;
        }

        public long getOwnerId() {
            return ownerId;
        }

    }

}
