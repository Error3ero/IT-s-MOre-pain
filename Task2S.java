package task2;

import java.util.Arrays;
import java.util.Scanner;

public class Task2S {
    static public int vectorModule(int vector) {
        int module = 0;
        for (char bit : Integer.toBinaryString(vector).toCharArray()) {
            if (bit == '1') {
                module++;
            }
        }
        return module;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        boolean[][] funcs = new boolean[n][];
        boolean[] post = new boolean[]{true, true, true, true, true};
        char[] buffer;
        int j, argsCount;
        for (int i = 0; i < n; i++) {
            argsCount = sc.nextInt();
            buffer = sc.next().toCharArray();
            funcs[i] = new boolean[1 << argsCount];
            for (int k = 0; k < buffer.length; k++) {
                funcs[i][k] = buffer[k] == '1';
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
                if (isLinear) {
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
    }
}
