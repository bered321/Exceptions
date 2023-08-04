import java.io.*;

public class FileManager {

    public static void processData(String filename) {
        try {
            String data = readFile(filename);
            System.out.println("Файл прочитан");

            String destinationFilename = "destination.txt";
            writeFile(destinationFilename, data);
            System.out.println("Данные успешно записаны в файл: " + destinationFilename);

            String sourceFilePath = filename;
            String destinationFilePath = "copy.txt";
            copyFile(sourceFilePath, destinationFilePath);
            System.out.println("Файл успешно скопирован: " + destinationFilePath);

        } catch (FileReadException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (FileWriteException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        } catch (FileCopyException e) {
            System.out.println("Ошибка копирования файла: " + e.getMessage());
        }
    }

    public static String readFile(String filePath) throws FileReadException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new FileReadException("файл не найден");
        } catch (IOException e) {
            throw new FileReadException("ошибка ввода-вывода");
        }
        return content.toString();
    }

    public static void writeFile(String filePath, String content) throws FileWriteException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            throw new FileWriteException(e.getMessage());
        }
    }

    public static void copyFile(String sourceFilePath, String destinationFilePath) throws FileCopyException {
        try (InputStream inputStream = new FileInputStream(sourceFilePath);
             OutputStream outputStream = new FileOutputStream(destinationFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            throw new FileCopyException("файл не найден");
        } catch (IOException e) {
            throw new FileCopyException("ошибка ввода-вывода");
        }
    }

    public static void main(String[] args) {
        processData("data.txt");
    }
}

class FileReadException extends IOException {
    public FileReadException(String message) {
        super(message);
    }
}

class FileWriteException extends IOException {
    public FileWriteException(String message) {
        super(message);
    }
}

class FileCopyException extends IOException {
    public FileCopyException(String message) {
        super(message);
    }
}

