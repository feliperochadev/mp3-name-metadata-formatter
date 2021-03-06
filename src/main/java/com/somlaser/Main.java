package com.somlaser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.somlaser.ConverterFormat.*;
import static com.somlaser.Mp3Converter.getPathType;


public class Main {
    public static void main(String[] args) throws IOException {
        String rootPath = args.length > 0 ? args[0] : new File(".").getCanonicalPath();
        String converterArg = args.length > 1 ? args[1] : "";
        ConverterFormat convertFormat = getLowerCaseArgValues().contains(converterArg) ? LOWER_CASE : UPPER_CASE;
        Mp3Converter converter = new Mp3Converter(convertFormat);
        File rootDirectory = new File(rootPath);
        if (!rootDirectory.isDirectory()) {
            System.out.println("\u001B[31mDiretório não existente!");
            return;
        }
        String outputDirectory = getPathType() + "arquivos-convertidos-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        converter.runConverterToDirectory(rootDirectory, rootDirectory.getAbsolutePath(), outputDirectory, true);
        System.out.println("\n" + converter.getFilesConverted() + " arquivos convertidos com sucesso na pasta " + outputDirectory + "\n");
    }
}
