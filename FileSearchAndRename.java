import java.io.*;

public class FileSearchAndRename {
    public static void main(String[] args) {
        String directoryPath = "C:/path/to/directory"; // specify the directory to search
        String searchString = ".txt"; // specify the search string
        String replaceString = "_new"; // specify the string to replace the search string
        recursiveSearchAndRename(directoryPath, searchString, replaceString);
    }
    
    public static void recursiveSearchAndRename(String directoryPath, String searchString, String replaceString) {
        File directory = new File(directoryPath);
        File[] fileList = directory.listFiles();
        
        for (File file : fileList) {
            if (file.isFile() && file.getName().contains(searchString)) {
                String oldFilePath = file.getAbsolutePath();
                String newFilePath = oldFilePath.replace(searchString, replaceString);
                File newFile = new File(newFilePath);
                if (newFile.exists()) {
                    System.out.println("A file with the name " + newFile.getName() + " already exists.");
                } else {
                    boolean success = file.renameTo(newFile);
                    if (!success) {
                        System.out.println("Failed to rename file " + file.getName());
                    } else {
                        System.out.println("Renamed file " + file.getName() + " to " + newFile.getName());
                    }
                }
            } else if (file.isDirectory()) {
                recursiveSearchAndRename(file.getAbsolutePath(), searchString, replaceString);
            }
        }
    }
}
