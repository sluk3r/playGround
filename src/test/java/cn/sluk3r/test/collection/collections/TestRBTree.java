package cn.sluk3r.test.collection.collections;


import cn.sluk3r.play.collection.PersistentRedBlackTreeSet;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Tests for the RedBlackTree by throwing lots of random data at it
 *
 * Copyright 2009 Tom Larkworthy
 * This program is distributed under the terms of the GNU General Public License as per http://www.gnu.org/licenses/gpl.txt Version 3, 29th June 2007
 * Other licensing options are availible.
 * @author Tom.Larkworthy@spectral-robotics.com
 */
public class TestRBTree extends TestCase {
    public static final Comparator<Integer> INTEGER_COMP = new Comparator<Integer>() {

        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };



    /**
     * tests that a thousand integers randomly added to the tree are remembered and stored in order
     */
    public void testPersistantInsert() {
        PersistentRedBlackTreeSet<Integer> tree = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);

        ArrayList<Integer> shuffledIntegers = getShuffledIntegers(1000);

        //add shuffled integers to the tree
        for (Integer integer : shuffledIntegers) {
            tree = tree.insert(integer);
        }

        assertTrue(tree.size() == 1000);

        int count = 0;
        //check they are iterated in the correct order
        for (Integer integer : tree) {
            assertEquals(integer.intValue(), count++);
        }
    }

    /**
     * tests that a thousand integers randomly added to the tree can be removed in a random order
     * successfully
     */
    public void testPersistantDelete() {
        PersistentRedBlackTreeSet<Integer> tree = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);

        ArrayList<Integer> shuffledIntegers = getShuffledIntegers(1000);

        //insert all
        for (Integer integer : shuffledIntegers) {
            tree = tree.insert(integer);
        }

        //reshuffle them
        Collections.shuffle(shuffledIntegers);

        int size = 1000;
        assertTrue(tree.size() == size);

        for (Integer integer : shuffledIntegers) {
            assertTrue(tree.contains(integer));
            tree = tree.delete(integer);

            assertEquals(tree.size(), --size);

            assertFalse(tree.contains(integer));

            //check we fail to remove elements not present
            tree = tree.delete(integer);
            assertEquals(size, tree.size());
            tree = tree.delete(1001);
            assertEquals(size, tree.size());
            tree = tree.delete(-1);
            assertEquals(size, tree.size());
        }

        assertFalse(tree.iterator().hasNext());
    }

    private static ArrayList<Integer> getShuffledIntegers(int number) {
        ArrayList<Integer> shuffledIntegers = new ArrayList<Integer>(number);
        for (int i = 0; i < 1000; i++) {
            shuffledIntegers.add(i);
        }
        Collections.shuffle(shuffledIntegers);
        return shuffledIntegers;
    }

    /**
     * performs a 100K random walk of inserting and removing random integers, and choosing whether to keep the old
     * tree or the new tree.
     */
    public void testPersistantDelete2() {
        PersistentRedBlackTreeSet<Integer> tree = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);

        for (int i = 0; i < 100000; i++) {

            int el = (int) (Math.random() * 10);
            PersistentRedBlackTreeSet<Integer> del = tree.delete(el);
            PersistentRedBlackTreeSet<Integer> ins = tree.insert(el);
            assertTrue(tree.equals(ins) || tree.equals(del));

            double choice = Math.random();

            if(choice < .33){
                //System.out.println("del");
                tree = del;
            }else if(choice < .66){
                //System.out.println("ins");
                tree = ins;
            }//else System.out.println("keep");
        }
    }

    /**
     * tests if certain intervals can be inserted
     */
    public void testPersistentInsertSubtree(){
        PersistentRedBlackTreeSet<Integer> tree1 = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);
        PersistentRedBlackTreeSet<Integer> tree2 = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);
        ArrayList<Integer> tree1Contents = new ArrayList<Integer>();
        ArrayList<Integer> tree2Contents = new ArrayList<Integer>();

        for(int i=0;i<=1000;i++){
            tree1 = tree1.insert(i);
            tree1Contents.add(i);


        }

        for(int i=1001;i<=2000;i++){
            tree2 = tree2.insert(i);
            tree2Contents.add(i);
        }


        for(int i=2001;i<=3000;i++){
            tree1 = tree1.insert(i);
            tree1Contents.add(i);
        }
        Collections.sort(tree1Contents, INTEGER_COMP);
        Collections.sort(tree2Contents, INTEGER_COMP);

        assertEquals(tree1.getElements(), tree1Contents);
        assertEquals(tree2.getElements(), tree2Contents);

        ArrayList<Integer> treeContents = new ArrayList<Integer>();
        treeContents.addAll(tree1Contents);
        treeContents.addAll(tree2Contents);
        Collections.sort(treeContents, INTEGER_COMP);

        PersistentRedBlackTreeSet<Integer> combined = tree1.insertSubTree(tree2);
        //check new tree is the combination
        assertEquals(combined.getElements(), treeContents);
        //and check originals are intact
        assertEquals(tree1.getElements(), tree1Contents);
        assertEquals(tree2.getElements(), tree2Contents);


    }


    public void testPersistantIterator(){
        PersistentRedBlackTreeSet<Integer> tree1 = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);
        PersistentRedBlackTreeSet<Integer> tree2 = new PersistentRedBlackTreeSet<Integer>(INTEGER_COMP);
        ArrayList<Integer> tree1Contents = new ArrayList<Integer>();
        ArrayList<Integer> tree2Contents = new ArrayList<Integer>();

        for(int i=0;i<=1000;i++){
            tree1 = tree1.insert(i);
            tree1Contents.add(i);


        }

        for(int i=1001;i<=2000;i++){
            tree2 = tree2.insert(i);
            tree2Contents.add(i);
        }


        for(int i=2001;i<=3000;i++){
            tree1 = tree1.insert(i);
            tree1Contents.add(i);
        }
        Collections.sort(tree1Contents, INTEGER_COMP);
        Collections.sort(tree2Contents, INTEGER_COMP);

        assertEquals(tree1.getElements(), tree1Contents);
        assertEquals(tree2.getElements(), tree2Contents);

        Iterator<Integer> tree1Iter = tree1.iterator();
        Iterator<Integer> tree2Iter = tree2.iterator();

        int cursur = 0;
        while(tree1Iter.hasNext()){
            int el1 = tree1Iter.next();
            assertEquals(el1, (int)tree1Contents.get(cursur));
            cursur++;
        }

        cursur = 0;
        while(tree2Iter.hasNext()){
            int el2 = tree2Iter.next();

            assertEquals(el2, (int)tree2Contents.get(cursur));
            cursur++;
        }
    }


}