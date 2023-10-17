package MyScanner;

import java.io.*;
import java.util.NoSuchElementException;

public class Scarner {
    private final Reader reader;
    private int read;
    private final char[] buffer;
    private final StringBuilder stock;

    private int pos;


    private boolean isSpace(char ch) {
        return (Character.getType(ch) == Character.CONTROL ||
                Character.getType(ch) == Character.PARAGRAPH_SEPARATOR ||
                Character.getType(ch) == Character.LINE_SEPARATOR ||
                Character.getType(ch) == Character.SPACE_SEPARATOR);
    }

    public Scarner(InputStream inputStream) {
        reader = new InputStreamReader(inputStream);
        buffer = new char[1 << 8];
        stock = new StringBuilder();
    }

    public Scarner(String string) {
        reader = new StringReader(string);
        buffer = new char[1 << 8];
        stock = new StringBuilder();
    }

    public Scarner(File file) throws FileNotFoundException {
        reader = new FileReader(file);
        buffer = new char[1 << 8];
        stock = new StringBuilder();
    }

    public boolean hasNext() throws IOException {
        reader.mark(1 << 20);
        do {
            read = reader.read(buffer);
            for (char ch : buffer) {
                if (!isSpace(ch)) {
                    reader.reset();
                    return true;
                }
            }
        } while (read >= 0);

        reader.reset();
        return false;
    }

    public boolean hasNextLine() throws IOException {
        reader.mark(1 << 20);
        do {
            read = reader.read(buffer);
            for (char ch : buffer) {
                if (Character.getType(ch) == Character.CONTROL ||
                        Character.getType(ch) == Character.LINE_SEPARATOR ||
                        Character.getType(ch) == Character.PARAGRAPH_SEPARATOR) {
                    reader.reset();
                    return true;
                }
            }
        } while (read >= 0);
        reader.reset();
        return false;
    }

    public boolean hasNextInt() throws IOException {
        boolean isNum = false;
        boolean isSpB4 = true;
        reader.mark(1 << 20);
        int pos;
        do {
            pos = 0;
            read = reader.read(buffer);
            for (char ch : buffer) {
                if (pos == read) {
                    return isNum;
                }
                if (!isNum && (ch == '-' || ch == '+') && isSpB4) {
                    isNum = true;
                    isSpB4 = false;
                    pos++;
                    continue;
                }
                if ((isSpB4 || isNum) && Character.getType(ch) == Character.DECIMAL_DIGIT_NUMBER) {
                    isSpB4 = false;
                    isNum = true;
                    pos++;
                    continue;
                }
                if (Character.getType(ch) != Character.DECIMAL_DIGIT_NUMBER &&
                        !isSpace(ch)) {
                    isNum = false;
                    isSpB4 = false;
                    pos++;
                    continue;
                }
                if (isSpace(ch)) {
                    isSpB4 = true;
                    if (isNum) {
                        reader.reset();
                        return true;
                    }
                }
                pos++;
            }
        } while (read >= 0);
        reader.reset();
        return false;
    }

    public String next() throws IOException {
        stock.delete(0, stock.length());
        int pos;
        do {
            read = reader.read(buffer);
            pos = 0;
            for (char ch : buffer) {
                if (pos == read) {
                    break;
                }
                if (!isSpace(ch)) {
                    stock.append(ch);
                }
                pos++;
            }
        } while (read >= 0);
        return stock.toString();
    }

    public String nextLine() throws IOException {
        stock.delete(0, stock.length());
        int pos;
        do {
            read = reader.read(buffer);
            pos = 0;
            for (char ch : buffer) {
                if (pos == read) {
                    break;
                }
                if (Character.getType(ch) == Character.CONTROL ||
                        Character.getType(ch) == Character.LINE_SEPARATOR ||
                        Character.getType(ch) == Character.PARAGRAPH_SEPARATOR) {
                    stock.append(ch);
                }
                pos++;
            }
        } while (read >= 0);
        return stock.toString();
    }

    public int nextInt() throws IOException {
        stock.delete(0, stock.length());
        int pos;
        boolean isNum = false;
        boolean isSpB4 = true;
        do {
            pos = 0;
            read = reader.read(buffer);
            for (char ch : buffer) {
                if (pos == read) {
                    return Integer.parseInt(stock.toString());
                }
                if (!isNum && (ch == '-' || ch == '+') && isSpB4) {
                    isNum = true;
                    isSpB4 = false;
                    stock.append(ch);
                    pos++;
                    continue;
                }
                if ((isSpB4 || isNum) && Character.getType(ch) == Character.DECIMAL_DIGIT_NUMBER) {
                    isSpB4 = false;
                    isNum = true;
                    stock.append(ch);
                    pos++;
                    continue;
                }
                if (Character.getType(ch) != Character.DECIMAL_DIGIT_NUMBER &&
                        !isSpace(ch)) {
                    isNum = false;
                    isSpB4 = false;
                    stock.delete(0, stock.length());
                    pos++;
                    continue;
                }
                if (isSpace(ch)) {
                    isSpB4 = true;
                    if (isNum) {
                        reader.reset();
                        return Integer.parseInt(stock.toString());
                    }
                }
                pos++;
            }
        } while (read >= 0);
        return Integer.parseInt(stock.toString());
    }
}
