package org.beetl.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 删除项目下Java文件的多余空行
 */
public class DeleteMulBlankLine {
    public static void main(String[] args) throws IOException {
        System.out.println("删除多余空行");
        Path projectPath = Paths.get("/data/Code/project/z8g/beetl");
        System.out.println(projectPath);

        List<File> fileList = listAllFile(projectPath.toFile());
        for (File file : fileList) {
            System.out.println("[删除多余空行] filePath = " + file.getAbsolutePath());
            deleteMulBlankLine(file);
        }
    }

    /**
     * 通过根目录获取该目录下所有文件
     *
     * @param rootFolder 根目录
     * @return 该目录下所有文件
     */
    static List<File> listAllFile(File rootFolder) {
        if (rootFolder == null || !rootFolder.exists() || !rootFolder.isDirectory()) {
            return Collections.emptyList();
        }

        List<File> result = new ArrayList<>();
        Stack<File> stack = new Stack<>();
        stack.add(rootFolder);
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            File[] subFiles = folder.listFiles();
            if (subFiles == null || subFiles.length == 0) {
                continue;
            }
            for (File subFile : subFiles) {
                if (subFile.isFile()) {
                    result.add(subFile);
                } else {
                    stack.add(subFile);
                }
            }
        }
        return result;
    }

    /**
     * 删除 {@code javaFile} 中多余的空行
     *
     * @param javaFile *.java 文件
     */
    public static void deleteMulBlankLine(File javaFile) throws IOException {
        if (javaFile == null || !javaFile.exists() || !javaFile.isFile() || !javaFile.getName().endsWith(".java")) {
            return;
        }

        StringBuilder newContent = new StringBuilder();
        boolean lastLineIsBlank = false;
        List<String> lineList = Files.readAllLines(javaFile.toPath());
        System.out.println(lineList);
        for (String line : lineList) {
            boolean currLineIsBlank = line.trim().length() == 0;

            if (lastLineIsBlank) {
                if (currLineIsBlank) {
                    // 不添加
                } else {
                    newContent.append(line).append('\n');
                }
            } else {
                if (currLineIsBlank) {
                    newContent.append('\n');
                } else {
                    newContent.append(line).append('\n');
                }
            }
            lastLineIsBlank = currLineIsBlank;
        }
        PrintWriter pw = new PrintWriter(javaFile);
        pw.write(newContent.toString());
        pw.flush();
        pw.close();
    }

}
