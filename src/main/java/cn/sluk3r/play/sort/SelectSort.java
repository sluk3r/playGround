package cn.sluk3r.play.sort;

/**
 * Created by baiing on 2014/7/11.
 */
public class SelectSort implements Sort {

    @Override
    public void sort(int[] array) {
        int out,in;
        int min;
        for(out = 0; out < array.length-1; out++){
            min = out;
            for(in = out+1; in < array.length; in++){
                if(array[in] < array[min]){
                    min = in;
                }
            }
            if(min != out){
                array[out] = array[out] + array[min];
                array[min] = array[out] - array[min];
                array[out] = array[out] - array[min];
            }
        }
    }
}
