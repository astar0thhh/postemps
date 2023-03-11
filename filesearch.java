import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileSearch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a word to search in the file:");
        String word = scanner.nextLine();
        scanner.close();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("example.txt"));
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(word)) {
                    found = true;
                    break;
                }
            }
            reader.close();
            
            if (found) {
                System.out.println("The word \"" + word + "\" was found in the file.");
            } else {
                System.out.println("The word \"" + word + "\" was not found in the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

}
