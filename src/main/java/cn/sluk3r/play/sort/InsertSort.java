package cn.sluk3r.play.sort;

/**
 * Created by baiing on 2014/7/11.
 */
public class InsertSort implements Sort {
    @Override
    public void sort(int[] array) {
        for(int i=1; i < array.length; i++){
            int insert = array[i];//待插入的元素
            int j = i - 1;
            while(j >= 0 && insert < array[j]){
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = insert;
        }
    }
}
