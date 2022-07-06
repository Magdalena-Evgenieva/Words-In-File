import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Enter file name:");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.next();
        File f = new File(filename);

        try {
            if (!f.isFile() || !f.canRead() || !f.exists())
                throw new FileNotFoundException();
            if (f.length() <= 0L) {
                System.out.println("File is empty!");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Wrong filename!");
            e.printStackTrace();
            System.exit(0);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.toString());
            e.printStackTrace();
            System.exit(0);
        }
        int option;
        do {
            System.out.println("Choose one option:\n");
            System.out.println("1: Find number of symbols and words in file.");
            System.out.println("2: Swap two words in file.");
            System.out.println("3: Swap two lines in file.");
            System.out.println("4: Exit");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    numberOfSymbolsAndWords(f);
                    break;
                case 2:
                    swapWords(f);
                    break;
                case 3:
                    swapLines(f);
                    break;
                case 4:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        } while (option < 4);
    }

    public static void numberOfSymbolsAndWords(File f) throws FileNotFoundException {
        FileReader fr = new FileReader(f.getAbsolutePath());
        BufferedReader reader = new BufferedReader(fr);
        try {
            ArrayList<ArrayList<String>> newFileContent = new ArrayList<>();
            String p = reader.readLine();
            while (p != null) {
                ArrayList<String> b = new ArrayList<>(Arrays.asList(p.split("\\s+")));
                newFileContent.add(b);
                p = reader.readLine();
            }
            int words = 0;
            int symbols = 0;
            //  System.out.println(newFileContent);
            for (int i = 0; i < newFileContent.size(); i++) {
                for (int j = 0; j < newFileContent.get(i).size(); j++) {
                    words++;
                    symbols += newFileContent.get(i).get(j).length();
                }
            }
            System.out.println("Words in file '" + f.getPath() + "' are: " + words);
            System.out.println("Symbols in file '" + f.getPath() + "' are: " + symbols);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void swapWords(File f) throws Exception {
        System.out.println("Enter first word line:");
        Scanner scanner = new Scanner(System.in);
        int firstLine = scanner.nextInt();
        System.out.println("Enter first word index:");
        int firstLineWord = scanner.nextInt();
        System.out.println("Enter second word line:");
        int secondLine = scanner.nextInt();
        System.out.println("Enter second word index:");
        int secondLineWord = scanner.nextInt();

        FileReader input = new FileReader(f.getAbsolutePath());
        BufferedReader reader = new BufferedReader(input);
        try {
            ArrayList<String[]> newFileContent = new ArrayList<>();
            String p = reader.readLine();
            while (p != null) {
                String[] b = p.split("\\s+");
                newFileContent.add(b);

                p = reader.readLine();
            }
            if (firstLine > newFileContent.size() || secondLine > newFileContent.size() || firstLine <= 0 || secondLine <= 0) {
                throw new IndexOutOfBoundsException("One or both rows are out of bounds.");
            }
            if (firstLineWord > newFileContent.get(firstLine - 1).length || secondLineWord > newFileContent.get(secondLine - 1).length ||
                    firstLineWord <= 0 || secondLineWord <= 0) {
                throw new IndexOutOfBoundsException("One or both words are out of bounds.");
            }

            String temp = newFileContent.get(firstLine - 1)[firstLineWord - 1];
            newFileContent.get(firstLine - 1)[firstLineWord - 1] = newFileContent.get(secondLine - 1)[secondLineWord - 1];
            newFileContent.get(secondLine - 1)[secondLineWord - 1] = temp;

            printWriter(f, newFileContent);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                input.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void swapLines(File f) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of lines to switch:");
        System.out.println("Enter first line");
        int firstLine = scanner.nextInt();
        System.out.println("Enter second line");
        int secondLine = scanner.nextInt();

        FileInputStream inp = new FileInputStream(f.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
        try {
            ArrayList<String[]> newFileContent = new ArrayList<>();
            String p = reader.readLine();
            while (p != null) {
                String[] b;
                b = p.split("\\s+");
                newFileContent.add(b);

                p = reader.readLine();
            }
            if (firstLine > newFileContent.size() || secondLine > newFileContent.size()) {
                throw new IndexOutOfBoundsException("One or both rows are out of bounds.");
            }
            String[] temp1 = newFileContent.get(firstLine - 1);
            String[] temp2 = newFileContent.get(secondLine - 1);


            newFileContent.set(firstLine - 1, temp2);
            newFileContent.set(secondLine - 1, temp1);

            printWriter(f, newFileContent);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            try {
                inp.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printWriter(File f, ArrayList<String[]> newFileContent) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(f.getAbsolutePath());
        for (String[] line : newFileContent) {
            for (String word : line) {
                writer.print(word + " ");
            }
            writer.println();
        }
        writer.close();
    }
}



