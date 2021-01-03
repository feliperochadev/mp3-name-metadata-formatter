package com.somlaser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.somlaser.ConverterFormat.*;


public class Main {
    public static void main(String[] args) throws IOException {
        var rootPath = args.length > 0 ? args[0] : new File(".").getCanonicalPath();
        var converterArg = args.length > 1 ? args[1] : "";
        var convertFormat = getLowerCaseArgValues().contains(converterArg) ? LOWER_CASE : UPPER_CASE;
        var converter = new Mp3Converter(convertFormat);
        var rootDirectory = new File(rootPath);
        if (!rootDirectory.isDirectory()) {
            System.out.println("\u001B[31mDiretório não existente!");
            return;
        }
        var pathType = System.getProperty("os.name").toLowerCase().contains("win") ? "\\" : "/";
        var outputDirectory = pathType + "arquivos convertidos " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        converter.runConverterToDirectory(rootDirectory, rootDirectory.getAbsolutePath(), outputDirectory);
        System.out.println("\n" + converter.getFilesConverted() + " arquivos convertidos com sucesso na pasta " + outputDirectory);
    }
}
