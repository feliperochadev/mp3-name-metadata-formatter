package com.somlaser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.ClassLoader.getSystemClassLoader;
import static java.util.Objects.requireNonNull;


public class Main {
    public static void main(String[] args) throws IOException {
        var converter = new Mp3Converter();
        var rootPath = args.length > 0 ? args[0] :
                new File(requireNonNull(getSystemClassLoader().getResource(".")).getPath()).getPath();
        var rootDirectory = new File(rootPath);
        if (!rootDirectory.isDirectory()) {
            System.out.println("Diretório não existente!");
            return;
        }
        var outputDirectory = "/arquivos convertidos " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        converter.runConverterToDirectory(rootDirectory, rootDirectory.getAbsolutePath(), outputDirectory);
    }
}
