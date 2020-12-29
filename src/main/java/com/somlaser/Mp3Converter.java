package com.somlaser;

import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.getNameWithoutExtension;

public class Mp3Converter {
    ConverterFormat FORMAT = ConverterFormat.LOWER_CASE;
    List<String> CONVERSABLE_FILE_EXTENSIONS = List.of("mp3");

    public void runConverterToDirectory(File currentDirectory, String rootPath, String outputPath) throws IOException {
        var directoryListing = currentDirectory.listFiles();
        for (File file : Objects.requireNonNull(directoryListing)) {
            var rootSplitPath = currentDirectory.getAbsolutePath().split(rootPath);
            var newPath = rootSplitPath.length > 1 ? rootPath + outputPath + rootSplitPath[1] :
                    rootPath + outputPath;
            if (file.isDirectory()) {
                if (Files.notExists(Path.of(newPath))) {
                    Files.createDirectories(Paths.get(newPath));
                    runConverterToDirectory(file, rootPath, outputPath);
                }
            }
            else {
                try {
                    var fileExtension = getFileExtension(file.getName()).toLowerCase();
                    if (CONVERSABLE_FILE_EXTENSIONS.contains(fileExtension)) {
                        var result = convert(file.getAbsolutePath(), newPath);
                        if (!result)
                            System.out.println("Falha ao converter arquivo " + file.getName());
                    }
                } catch (Exception e) {
                    System.out.println("Erro " + e.getMessage() +" ao processar o arquivo: " + file.getName());
                }
            }
        }
    }

    public boolean convert(String filePath, String outputPath) throws InvalidDataException, IOException,
            UnsupportedTagException, NotSupportedException {
        var mp3file = new Mp3File(filePath);

        ID3v1 id3v1Tag;
        if (mp3file.hasId3v1Tag()) {
            id3v1Tag = mp3file.getId3v1Tag();
        } else {
            id3v1Tag = new ID3v1Tag();
            mp3file.setId3v1Tag(id3v1Tag);
        }
        id3v1Tag.setTrack(format(id3v1Tag.getTrack()));
        id3v1Tag.setArtist(format(id3v1Tag.getArtist()));
        id3v1Tag.setTitle(format(id3v1Tag.getTitle()));
        id3v1Tag.setAlbum(format(id3v1Tag.getAlbum()));
        id3v1Tag.setYear(format(id3v1Tag.getYear()));
        id3v1Tag.setGenre(id3v1Tag.getGenre());
        id3v1Tag.setComment(format(id3v1Tag.getComment()));

        ID3v2 id3v2Tag;
        if (mp3file.hasId3v2Tag()) {
            id3v2Tag = mp3file.getId3v2Tag();
        } else {
            id3v2Tag = new ID3v24Tag();
            mp3file.setId3v2Tag(id3v2Tag);
        }
        id3v2Tag.setTrack(format(id3v2Tag.getTrack()));
        id3v2Tag.setArtist(format(id3v2Tag.getArtist()));
        id3v2Tag.setTitle(format(id3v2Tag.getTitle()));
        id3v2Tag.setAlbum(format(id3v2Tag.getAlbum()));
        id3v2Tag.setYear(format(id3v2Tag.getYear()));
        id3v2Tag.setGenre(id3v2Tag.getGenre());
        id3v2Tag.setComment(format(id3v2Tag.getComment()));
        id3v2Tag.setComposer(format(id3v2Tag.getComposer()));
        id3v2Tag.setPublisher(format(id3v2Tag.getPublisher()));
        id3v2Tag.setOriginalArtist(format(id3v2Tag.getOriginalArtist()));
        id3v2Tag.setAlbumArtist(format(id3v2Tag.getAlbumArtist()));
        id3v2Tag.setCopyright(format(id3v2Tag.getCopyright()));
        id3v2Tag.setUrl(format(id3v2Tag.getUrl()));
        id3v2Tag.setEncoder(format(id3v2Tag.getEncoder()));

        var fileExtension = getFileExtension(mp3file.getFilename());
        var fileName = format(getNameWithoutExtension(mp3file.getFilename()));

        mp3file.save(outputPath + "/" + fileName + "." + fileExtension);
        return true;
    }

    private String format(String original) {
        if (original != null) {
            switch (FORMAT) {
                case UPPER_CASE:
                    return original.toUpperCase();
                case LOWER_CASE:
                    return original.toLowerCase();
                default:
                    return original;
            }
        }
        return original;
    }
}
