package com.java.request.file;

import java.io.File;
import java.io.IOException;

/**
 * 创建文件
 */
public class CreateFile {
    private final File file;
    private boolean flag = false;

    public CreateFile(String fileName) {
        this.file = new File(fileName);
    }

    public void createFile() {
        if (!this.file.exists()) {
            try {
                flag = this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (flag) {
                    System.out.println("文件创建成功");
                } else {
                    System.out.println("文件创建失败");
                }
            }
        }
    }

    public File getFile() {
        if (flag) {
            return this.file;
        } else {
            return null;
        }
    }
}
