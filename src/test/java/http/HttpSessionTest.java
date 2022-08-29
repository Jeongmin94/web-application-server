package http;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HttpSessionTest {

    private final HttpSession session = new HttpSession();

    @Test
    public void setAndGetIdTest() {
        UUID id = UUID.randomUUID();

        session.setId(id.toString());

        assertThat(session.getId(), is(id.toString()));
    }

    @Test
    public void attributeTest() {
        String key1 = "key1";
        Integer value1 = 100;
        String key2 = "key2";
        Long value2 = 1000L;

        session.setAttribute(key1, value1);
        session.setAttribute(key2, value2);

        assertThat(session.getAttributesSize(), is(2));
        assertThat(session.getAttribute(key1), is(value1));
        assertThat(session.getAttribute(key2), is(value2));

        session.removeAttribute(key1);

        assertThat(session.getAttributesSize(), is(1));
        assertNull(session.getAttribute(key1));
        assertThat(session.getAttribute(key2), is(value2));
    }

    @Test
    public void invalidateTest() {
        session.invalidate();

        assertThat(session.getAttributesSize(), is(0));
        assertNull(session.getId());
    }
}