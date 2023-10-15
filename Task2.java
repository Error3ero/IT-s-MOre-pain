package task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Task2 {
    static public int vectorModule(int vector) {
        int module = 0;
        for (char bit : Integer.toBinaryString(vector).toCharArray()) {
            if (bit == '1') {
                module++;
            }
        }
        return module;
    }

    static public void main(String[] args) {
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(System.in))) {
            int n = Integer.parseInt(rd.readLine());
            boolean[][] funcs = new boolean[n][];
            boolean[] post = new boolean[]{true, true, true, true, true};
            char[] buffer;
            int j, argsCount;
            for (int i = 0; i < n; i++) {
                buffer = rd.readLine().toCharArray();
                j = 0;
                while (!Character.isSpaceChar(buffer[j])) {
                    j++;
                }
                argsCount = Integer.parseInt(new String(buffer, 0, j));
                funcs[i] = new boolean[1 << argsCount];
                j++;

                for (int k = j; k < j + (1 << argsCount); k++) {
                    System.err.println("i = " + i + " k = " + k + " j = " + j);
                    funcs[i][k - j] = buffer[k] == '1';
                }
            }
            boolean isSelfDual;
            boolean isMonotone;
            boolean isLinear;
            boolean[] jegalkin;
            int len;
            for (boolean[] func : funcs) {
                len = func.length;
                jegalkin = Arrays.copyOf(func, len);
                isMonotone = true;
                isSelfDual = true;
                isLinear = true;
                for (int i = 0; i < len; i++) {
                    if (isSelfDual && func[i] == func[len - 1 - i]) {
                        isSelfDual = false;
                    }
                    if (isMonotone) {
                        for (int q = 0; q < len; q++) {
                            if (vectorModule(i) > vectorModule(q) && Boolean.compare(func[i], func[q]) < 0) {
                                isMonotone = false;
                                break;
                            }
                        }
                    }
                    if (isLinear && len != 1) {
                        if (vectorModule(i) > 1 && jegalkin[0]) {
                            isLinear = false;
                            break;
                        }
                        j = 0;
                        do {
                            jegalkin[j] = jegalkin[j] ^ jegalkin[j + 1];
                            j++;
                        } while (j < len - 1 - i);
                    }
                }
                post[0] = post[0] && !func[0];
                post[1] = post[1] && func[len - 1];
                post[2] = post[2] && isSelfDual;
                post[3] = post[3] && isMonotone;
                post[4] = post[4] && isLinear;
            }
            System.err.println(Arrays.toString(post));
            if (post[0] || post[1] || post[2] || post[3] || post[4]) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        } catch (IOException e) {
            System.err.println("Runtime exception: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds exception: " + e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
