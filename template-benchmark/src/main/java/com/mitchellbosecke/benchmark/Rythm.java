package com.mitchellbosecke.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.rythmengine.RythmEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Rythm extends BaseBenchmark {

    RythmEngine engine;

    String template;

    @Setup
    public void setup() throws IOException {
        Properties properties = new Properties();
        properties.put("log.enabled", false);
        properties.put("feature.smart_escape.enabled", false);
        properties.put("feature.transform.enabled", false);
        engine = new RythmEngine(properties);

        template = readResource("templates/stocks.rythm.html");
    }

    @Benchmark
    public String benchmark() {
        return engine.renderString(template, getContext());
    }
    public static String readResource(String name) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(Rythm.class.getClassLoader().getResourceAsStream(name))
        )) {
            for (;;) {
                int c = in.read();
                if (c == -1) {
                    break;
                }
                builder.append((char) c);
            }
        }
        return builder.toString();
    }
}
