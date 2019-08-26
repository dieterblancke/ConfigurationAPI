package com.dbsoftwares.configuration.yaml;

import com.dbsoftwares.configuration.ConfigurationTest;
import com.dbsoftwares.configuration.api.IConfiguration;
import com.dbsoftwares.configuration.api.ISection;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class YamlConfigurationTest extends ConfigurationTest {

    @Test
    public void testCreateAndSaveConfiguration() throws IOException {
        final File file = new File(resourcesFolder, "yaml/test1.yml");

        try {
            createFileIfNotExists(file);
        } catch (IOException e) {
            fail("An error occured trying to create the file: " + file.getPath() + "!");
        }

        final IConfiguration configuration = IConfiguration.loadYamlConfiguration(file);
        final StringBuilder path = new StringBuilder("test");

        for (int i = 0; i < 3; i++) {
            path.append(".test");

            final ISection section = configuration.createSection(path.toString());

            section.set("hello", i);
            section.set("hello2.test", i + 21);
        }

        try {
            configuration.save();
        } catch (Exception e) {
            fail("An error occured trying to save the file: " + file.getPath() + "!");
        }

        assertEquals("test:\n" +
                "  test:\n" +
                "    hello: 0\n" +
                "    hello2:\n" +
                "      test: 21\n" +
                "    test:\n" +
                "      hello: 1\n" +
                "      hello2:\n" +
                "        test: 22\n" +
                "      test:\n" +
                "        hello: 2\n" +
                "        hello2:\n" +
                "          test: 23", String.join("\n", Files.readAllLines(Paths.get(file.toURI()))));
    }

    @Test
    public void testLoadConfiguration() {
        final File file = new File(resourcesFolder, "yaml/test2.yml");
        final IConfiguration configuration = IConfiguration.loadYamlConfiguration(file);

        assertEquals("particularly", configuration.getString("tell"));
        assertEquals(false, configuration.getBoolean("fog.alike.use"));
        assertEquals(-1462433391, (int) configuration.getInteger("fog.vast"));
        assertEquals(-1396882676.185273, configuration.getDouble("anyone.completely"), 0.01);
        assertEquals("talk", configuration.getString("anyone.was.matter.sleep.money.structure.monkey.stone"));
    }

    @Test
    public void testLoadSectionsListConfiguration() {
        final File file = new File(resourcesFolder, "yaml/test3.yml");
        final IConfiguration configuration = IConfiguration.loadYamlConfiguration(file);

        assertEquals(3, configuration.getSectionList("test").size());

        configuration.getSectionList("test").forEach(section -> {
            assertEquals("world", section.getString("hello"));

            section.getSectionList("testing").forEach(section1 ->
                    assertEquals(456, (int) section1.getInteger("123"))
            );
        });
    }
}