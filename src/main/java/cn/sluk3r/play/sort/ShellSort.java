package cn.sluk3r.play.sort;

/**
 * Created by baiing on 2014/7/11.
 */
public class ShellSort implements Sort {
    @Override
    public void sort(int[] data) {
        int len = data.length;
        int inner, outer;
        int temp;
        //find initial value of h
        int h = 1;
        while (h <= len / 3)
            h = h * 3 + 1; // (1, 4, 13, 40, 121, ...)

        while (h > 0) // decreasing h, until h=1
        {
            // h-sort the file
            for (outer = h; outer < len; outer++) {
                temp = data[outer];
                inner = outer;
                // one subpass (eg 0, 4, 8)
                while (inner > h - 1 && data[inner - h] >= temp) {
                    data[inner] = data[inner - h];
                    inner -= h;
                }
                data[inner] = temp;
            }
            h = (h - 1) / 3; // decrease h
        }
    }
}
