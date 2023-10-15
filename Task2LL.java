package task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Task2LL {
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
            LinkedList<LinkedList<Boolean>> funcs = new LinkedList<>();
            boolean[] post = new boolean[]{true, true, true, true, true};
            String buffer;
            int j, argsCount;
            for (int i = 0; i < n; i++) {
                buffer = rd.readLine();
                j = 0;
                while (!Character.isSpaceChar(buffer.charAt(j))) {
                    j++;
                }
                argsCount = Integer.parseInt(buffer.substring(0, j));
                funcs.add(new LinkedList<Boolean>());
                j++;
                for (int k = j; k < j + (1 << argsCount); k++) {
                    funcs.get(i).add(buffer.charAt(k) == '1');
                }
            }
            boolean isSelfDual;
            boolean isMonotone;
            boolean isLinear;
            LinkedList<Boolean> jegalkin;
            int len;
            for (LinkedList<Boolean> func : funcs) {
                len = func.size();
                jegalkin = new LinkedList<Boolean>(func);
                isMonotone = true;
                isSelfDual = true;
                isLinear = true;
                for (int i = 0; i < len; i++) {
                    if (isSelfDual && func.get(i) == func.get(len - 1 - i)) {
                        isSelfDual = false;
                    }
                    if (isMonotone) {
                        for (int q = 0; q < len; q++) {
                            if (vectorModule(i) > vectorModule(q) && Boolean.compare(func.get(i), func.get(q)) < 0) {
                                isMonotone = false;
                                break;
                            }
                        }
                    }
                    if (isLinear) {
                        if (vectorModule(i) > 1 && jegalkin.get(0)) {
                            isLinear = false;
                            break;
                        }
                        j = 0;
                        do {
                            jegalkin.set(j, jegalkin.get(j) ^ jegalkin.get(j + 1));
                            j++;
                        } while (j < len - 1 - i);
                    }
                }
                post[0] = post[0] && !func.get(0);
                post[1] = post[1] && func.get(len - 1);
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
        }
        catch (IOException e) {
            System.err.println("Runtime exception: " + e.getMessage());
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Index out of bounds exception: " + e.getMessage());
            System.exit(2);
        }
    }
}
