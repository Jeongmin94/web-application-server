package http.enumerable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileExtendTest {
    private static final Logger log = LoggerFactory.getLogger(FileExtendTest.class);

    @Test
    public void fileExtendTest() {
        log.debug("[HTML]: {}", FileExtend.HTML.getValue());
        log.debug("[CSS]: {}", FileExtend.CSS.getValue());
        log.debug("[JS]: {}", FileExtend.JS.getValue());
        log.debug("[ICO]: {}", FileExtend.ICO.getValue());

        assertThat(FileExtend.HTML.name(), is("HTML"));
        assertThat(FileExtend.HTML.getValue(), is(".html"));
    }
}