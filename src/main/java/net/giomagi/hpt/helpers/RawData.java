package net.giomagi.hpt.helpers;

import net.giomagi.hpt.model.NotFoundException;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class RawData {

    private RawData() {
        // static helper
    }

    public static List<String> fromFile(String filename) {

        try {
            return IOUtils.readLines(new FileInputStream(filename));
        } catch (IOException e) {
            throw new NotFoundException("Failed to load data from " + filename);
        }
    }
}
