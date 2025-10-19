package de.kheuwes.footballforwall.model.historie;

public class FileItem {
    private String fileName;
    private String filePath;
    private String typ;

    public FileItem(String fileName, String filePath, String typ) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.typ = typ;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getTyp() {
        return typ;
    }
    
    public void setTyp(String typ) {
        this.typ = typ;
    }
}
