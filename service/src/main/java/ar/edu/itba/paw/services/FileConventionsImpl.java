package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.util.FileConventions;
import org.springframework.stereotype.Service;

@Service
public class FileConventionsImpl implements FileConventions {

    public String createName(String modelName, String modelData) {
        return modelName + "_" + modelData;
    }
}
