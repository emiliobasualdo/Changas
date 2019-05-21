package ar.edu.itba.paw.models;

import java.util.LinkedList;
import java.util.List;

public class ChangaPicture {
    private String imageReference;
    private long changaId;

    public ChangaPicture(ChangaPicture.Builder changaPictureBuilder) {
        this.imageReference = changaPictureBuilder.getImageReference();
        this.changaId = changaPictureBuilder.getChangaId();
    }

    public String getImageReference() {
        return imageReference;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }

    public long getChangaId() {
        return changaId;
    }

    public static class Builder {
        private String imageReference;
        private long changaId;

        public Builder(long changaId, String imageReference) {
            this.imageReference = imageReference;
            this.changaId = changaId;
        }

        public long getChangaId() {
            return changaId;
        }

        public String getImageReference() {
            return imageReference;
        }
    }

}
