package com.example.volleylead;

import java.io.File;

public class DataForFolder {
    File file;
    int level;
    boolean open;
    public DataForFolder(File file,int level, boolean open) {
        // TODO Auto-generated constructor stub
        this.file=file;
        this.level=level;
        this.open=open;
    }
}
