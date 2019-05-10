import com.google.gson.Gson;
import lt.bit.java2.Main;
import lt.bit.java2.Result;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testMethod() {

        Main main = new Main();
        String res = main.method(7, 2);

        Gson gson = new Gson();
        Result result = gson.fromJson(res, Result.class);

        assertEquals(result.getX(), 7);
        assertEquals(result.getY(), 2);
        assertEquals(result.getZ(), 3);
        assertNull(result.getError());
    }

    @Test
    public void testZero() {
        Main main = new Main();
        String res = main.method(7, 0);

        Gson gson = new Gson();
        Result result = gson.fromJson(res, Result.class);

        assertNotNull(result.getError());
        assertEquals(result.getX(), 7);
        assertEquals(result.getY(), 0);
        assertEquals(result.getZ(), 0);
    }

}
