package cn.sluk3r.play.sort;

/**
 * Created by baiing on 2014/7/11.
 */
public class BubbleSort implements Sort {
    @Override
    public void sort(int[] array) {
        for(int i=0; i < array.length - 1; i++){
            for(int j = i; j < array.length-i-1; j++){
                if(array[j] > array[j+1]){
                    array[j] = array[j] + array[j+1];
                    array[j+1] = array[j] - array[j+1];
                    array[j] = array[j] - array[j+1];
                }
            }
        }
    }
}
