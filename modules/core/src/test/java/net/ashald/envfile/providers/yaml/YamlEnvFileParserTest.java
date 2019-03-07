package net.ashald.envfile.providers.yaml;
import net.ashald.envfile.EnvFileErrorException;
import net.ashald.envfile.providers.dotenv.DotEnvFileParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YamlEnvFileParserTest {

    private YamlEnvFileParser parser = new YamlEnvFileParser(true, false, "none");

    private String getFile(String name) {
        return Paths.get("src","test", "resources", "providers", "yaml", name).toString();
    }

    @Test
    public void testSubstitutions() throws EnvFileErrorException, IOException {
        Map<String, String> context = new HashMap<String, String>() {{
            put("FOO", "BAR");
        }};

        Map<String, String> result = parser.process(Collections.emptyMap(), getFile("substitutions.yaml"), context);
        Assert.assertEquals("", result.get("A"));
        Assert.assertEquals("default", result.get("B"));
        Assert.assertEquals("BAR", result.get("C"));
        Assert.assertEquals("BAR default", result.get("D"));
        Assert.assertEquals("BAR", result.get("E"));
    }

    @Test
    public void testOrder() throws EnvFileErrorException, IOException {
        Map<String, String> result = parser.process(Collections.emptyMap(), getFile("order.yaml"), Collections.emptyMap());
        Assert.assertEquals("A(B(C))", result.get("A"));
    }

}
