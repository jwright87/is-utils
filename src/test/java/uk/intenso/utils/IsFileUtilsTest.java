package uk.intenso.utils;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IsFileUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(IsFileUtilsTest.class);

    private String location = "location";
    File file = new File(location);

    @BeforeEach
    public void setup() throws IOException {
        file = new File(location);
        IsFileUtils.writeToFile(file, "Line 1\n\nLine 3", false);
    }

    @Test
    public void shouldReadStringFromClasspath() {
        String result = IsFileUtils.readClasspathFile(IsFileUtils.class.getClassLoader(),
                "test-file.txt");

        assertEquals("Test\nData\n3rd line", result);
    }

    @Test
    public void readFileTest() throws IOException {

        String result = IsFileUtils.readFile("location");
        assertEquals("Line 1\n\nLine 3", result);
    }

    @Test
    public void shouldPrintWorkingDirectory() {
        assertTrue(IsFileUtils.cwd().endsWith("/is-utils"));
    }

    @Test
    public void shouldWriteToFile() throws IOException {
        IsFileUtils.writeToFile(file, "Hello", false);
        System.out.println("*" + IsFileUtils.readFile(location) + "*");
        assertTrue(IsFileUtils.readFile("location").endsWith("Hello"));
    }


    @AfterAll
    public void tearDown() {
        file.deleteOnExit();
    }
}