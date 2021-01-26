package wraith.progressia.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import wraith.progressia.Progressia;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {

    public static void createFile(String path, String contents, boolean overwrite) {
        File file = new File(path);
        if (file.exists() && !overwrite) {
            return;
        }
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setReadable(true);
        file.setWritable(true);
        file.setExecutable(true);
        if (contents == null || "".equals(contents)) {
            return;
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(File file) {
        String output = "";
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter("\\Z");
            output = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static JsonObject getJsonObject(String json) {
        try {
            return new JsonParser().parse(json).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            Progressia.LOGGER.error("Error while parsing following json:\n\n" + json);
            return null;
        }
    }

    public static File[] getFiles(String path) {
        return new File(path).listFiles();
    }

    public static void saveFilesFromJar(String dir, String outputDir, boolean overwrite) {
        JarFile jar = null;
        try {
            jar = new JarFile(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException | URISyntaxException e) {
            ModMetadata metadata = FabricLoader.getInstance().getModContainer(Progressia.MOD_ID).get().getMetadata();
            String filename = "mods/wraith-" + Progressia.MOD_ID + "-" + metadata.getVersion() + ".jar";
            try {
                jar = new JarFile(new File(filename));
            } catch (IOException ignored) {
            }
        }

        if (jar != null) {
            Enumeration<JarEntry> entries = jar.entries();
            while(entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if ((entry.getName().endsWith(".png") || entry.getName().endsWith(".mcmeta")) && !dir.contains("textures")) {
                    continue;
                }
                if (!entry.getName().startsWith(dir) || (!entry.getName().endsWith(".json") && !entry.getName().endsWith(".png") && !entry.getName().endsWith(".mcmeta"))) {
                    continue;
                }
                String[] segments = entry.getName().split("/");
                String filename = segments[segments.length - 1];
                if (entry.isDirectory()) {
                    continue;
                }
                InputStream is = Utils.class.getResourceAsStream("/" + entry.getName());
                String path = "config/progressiaa/" + outputDir + ("".equals(outputDir) ? "" : File.separator) + filename;
                if (filename.endsWith(".png")) {
                    if (Files.exists(new File(path).toPath()) && overwrite) {
                        try {
                            Files.delete(new File(path).toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!Files.exists(new File(path).toPath())) {
                        try {
                            new File(path).getParentFile().mkdirs();
                            Files.copy(is, new File(path).toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        inputStreamToFile(is, new File(path), overwrite);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Launched from IDE.");
            File[] files = FabricLoader.getInstance().getModContainer(Progressia.MOD_ID).get().getPath(dir).toFile().listFiles();
            for(File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                String[] segments = file.getName().split("/");
                String filename = segments[segments.length - 1];
                String path = "config/progressia/" + outputDir + ("".equals(outputDir) ? "" : File.separator) + filename;
                if (filename.endsWith(".png")) {
                    if (Files.exists(new File(path).toPath()) && overwrite) {
                        try {
                            Files.delete(new File(path).toPath());
                        } catch (IOException e) {
                            Progressia.LOGGER.warn("ERROR OCCURRED WHILE DELETING OLD TEXTURES FOR " + filename);
                            e.printStackTrace();
                        }
                    }
                    if (!Files.exists(new File(path).toPath())) {
                        try {
                            Progressia.LOGGER.info("Regenerating " + filename);
                            new File(path).getParentFile().mkdirs();
                            Files.copy(file.toPath(), new File(path).toPath());
                        } catch (IOException e) {
                            Progressia.LOGGER.warn("ERROR OCCURRED WHILE REGENERATING " + filename + " TEXTURE");
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        createFile(path, org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8), overwrite);
                    } catch (IOException e) {
                        Progressia.LOGGER.warn("ERROR OCCURRED WHILE REGENERATING " + filename + " TEXTURE");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file, boolean overwrite) throws IOException{
        if (!file.exists() || overwrite) {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, file);
        }
    }

    public static InputStream stringToInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

}
