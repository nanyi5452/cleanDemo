package com.example.xiaomao.sqlitebyrx.fileIO;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 16-1-14.
 */
public class ArrayAnalyser {


    public static void dumpArray(int[] ints){
        Log.i("AAA","--------dumping---------");
        StringBuilder sb1=new StringBuilder();
        sb1.append("---  ");
        for (int ii=0;ii<ints.length;ii++){
            sb1.append("  "+ints[ii]);
        }
        sb1.append("  ---");
        Log.i("AAA",sb1.toString());
    }

    public static void dumpArray(int[] ints,String TAG){
        Log.i(TAG,"--------dumping---------");
        StringBuilder sb1=new StringBuilder();
        sb1.append("---  ");
        for (int ii=0;ii<ints.length;ii++){
            sb1.append("  "+ints[ii]);
        }
        sb1.append("  ---");
        Log.i(TAG,sb1.toString());
    }



    /**
     * find the backward differential of an integer array
     * @param intArray:array to be differentiated, the size of the array has to be larger than 1
     * @return int[] of length-1, differentiated array
     */
    public static int[] diff_array(int[] intArray){
        int arrayLength=intArray.length;
        int[] array_temp1=new int[arrayLength-1]; // subtractee --减去array
        System.arraycopy(intArray,0,array_temp1,0,arrayLength-1);
        int[] array_temp2=new int[arrayLength-1]; // subtracter --被减array
        System.arraycopy(intArray,1,array_temp2,0,arrayLength-1);
        int[] array_diff=new int[arrayLength-1]; //  differnce array, to be returned
        for (int ii=0;ii<arrayLength-1;ii++){    // find the difference
            array_diff[ii]=array_temp2[ii]-array_temp1[ii];
        }
        return array_diff;
    }


    /**
     * find cross-zero indexes in an integer array, cross zero index is  defined for (either one of the) below conditions :
     *   condition 0: int[ii]<0 and int[ii+1]>0
     *   condition 1: int[ii]<0 and int[ii+1]==0 and int[ii+2]>0
     *   condition 2: int[ii]<0 and int[ii+1]==0 and int[ii+2]==0 and int[ii+3]>0,
     *
     * @param intArray
     * @return ArrayList of indexes
     */
    public static List<Integer> findCrossZeros(int[] intArray){
        List<Integer> mlist=new ArrayList<Integer>();
        int arrayLength=intArray.length;
        for (int ii=0;ii<arrayLength-3;ii++){
            boolean condA=intArray[ii]<0;
            boolean condB=intArray[ii+1]>0;
            boolean condC=(intArray[ii+1]==0)&&((intArray[ii+2]>0));
            boolean condD=(intArray[ii+1]==0)&&(intArray[ii+2]==0)&&((intArray[ii+3]>0));
            if (condA&&(condB||condC||condD)) mlist.add(ii);
        }
        return mlist;
    }

    /**
     * save function as  findCrossZeros  different return type: return an array instead of arrayList
     * @param intArray
     * @return
     */
    public static int[] findCrossZeros_array(int[] intArray){
        List<Integer> mlist=findCrossZeros(intArray);
        int[] ret_array=new int[mlist.size()];
        Iterator<Integer> mlistIterator=mlist.iterator();
        int ii=0;
        while(mlistIterator.hasNext()){
            ret_array[ii]=mlistIterator.next();
            ii++;
        }
        return ret_array;
    }

    // to shift the index (index+1) --> for finding local minimums
    private static int[] findCrossZeros_array_indexShift(int[] intArray){
        List<Integer> mlist=findCrossZeros(intArray);
        int[] ret_array=new int[mlist.size()];
        Iterator<Integer> mlistIterator=mlist.iterator();
        int ii=0;
        while(mlistIterator.hasNext()){
            ret_array[ii]=mlistIterator.next()+1;
            ii++;
        }
        return ret_array;
    }


    // find the max int in an int array
    public static int findMax_inIntArray(int[] ints){
        int max=ints[0];
        for (int ii=0;ii<ints.length;ii++){
            if (ints[ii]>max) max=ints[ii];
        }
        return max;
    }
    // find the min int value in an int array
    public static int findMin_inIntArray(int[] ints){
        int min=ints[0];
        for (int ii=0;ii<ints.length;ii++){
            if (ints[ii]<min) min=ints[ii];
        }
        return min;
    }
    // find the index corresponding to min int value in an int array
    public static int findMinIndex_inIntArray(int[] ints){
        int min=ints[0];
        int minIndex=0;
        for (int ii=0;ii<ints.length;ii++){
            if (ints[ii]<min) {
                min=ints[ii];
                minIndex=ii;
            }
        }
        return minIndex;
    }


    // find the total of a double array
    public static double findSum(double[] doubles,int startBin,int endBin){
        double total=0;
        for (int aa=startBin;aa<endBin+1;aa++) total=total+doubles[aa];
        return total;
    }


    /**
     * find simple local minimum of an array --->
     * @param intArray
     * @return
     */
    public static int[] findLocalMins(int[] intArray){
        int[] differentiated=diff_array(intArray);
        int[] localMin=findCrossZeros_array_indexShift(differentiated);
//        dumpArray(localMin);
        return localMin;
    }


    /****
     * this function filter out entries with too close a distance to the previous entry
     * @param refinedMinList  arraylist of integers to be refined, the caller has to make sure this list is not empty..
     * @param minimumInterval  minimum distance
     * @return refined arraylist
     *
     * unit tested --> ArrayAnalyserTest.testInterval() testInterval2()
     *
     * ----》 this function may result in first identified minimum unstable  --> this is caused by the arbitrary choice of first point
     *
     * ****** refinedWithInterval2  works better ***********
     */
    public static List<Integer> refinedWithInterval(List<Integer> refinedMinList,int minimumInterval){
        Integer currentIntTemp=refinedMinList.get(0);  //  arbitrary choice of first point
        List<Integer> refinedMinList2=new ArrayList<Integer>();  // refine the indexes to make sure the minimum time interval is met
        refinedMinList2.add(refinedMinList.get(0));
        for(int tempInt=1;tempInt<refinedMinList.size();tempInt++){   // front order
            if ((refinedMinList.get(tempInt)-currentIntTemp)>minimumInterval){
                refinedMinList2.add(refinedMinList.get(tempInt));
                currentIntTemp=refinedMinList.get(tempInt);
            }
        }
        return refinedMinList2;
    }
    // this should be used instead of refinedWithInterval
    public static List<Integer> refinedWithInterval2(List<Integer> refinedMinList,int minimumInterval){
        List<Integer> refinedMinList2=new ArrayList<Integer>();  // refine the indexes to make sure the minimum time interval is met
        for(int tempInt=0;tempInt<refinedMinList.size()-1;tempInt++){
            if ((refinedMinList.get(tempInt+1)-refinedMinList.get(tempInt))>minimumInterval){
                refinedMinList2.add(refinedMinList.get(tempInt));
            }
        }
        return refinedMinList2;
    }



    // convert an Integer arraylist to an int array
    public static int[] convert2intArray(List<Integer> list){
        int[] convertedArray=new int[list.size()];
        Iterator<Integer> iterator=list.iterator();
        int tempIndex=0;
        while (iterator.hasNext()){
            convertedArray[tempIndex]=iterator.next();
            tempIndex++;
        }
        return convertedArray;
    }

    // convert an Integer arraylist to an int array
    public static List<Integer> convert2intList(int[] array){
        List<Integer> convertedList=new ArrayList<Integer>();
        int length=array.length;
        for (int aa=0;aa<length;aa++){
            convertedList.add(array[aa]);
        }
        return convertedList;
    }



    /**
     * find the flux for every breath cycle
     * @param breathSignal  : breath signal wave form , normally int array of length 300--> 1minute data
     * @param cycleStartIndex : starting index of each breath cycle ,
     * @return
     */
    public static int[] findBreathFlux(int[] breathSignal,int[] cycleStartIndex){
        if (cycleStartIndex.length<2){
            int[] flux_ret={0};
            return flux_ret;
        }
        else {
            int return_length=cycleStartIndex.length-1;
            int[] flux_ret=new int[return_length];
            for (int aa=0;aa<return_length;aa++){
                // find the peak value within a breath cycle
                int maxStartCopyIndex=cycleStartIndex[aa];
                // find the peak within 8sec(corresponding to 40) or simply use: int endCopyIndex=cycleStartIndex[aa+1]
                int maxEndCopyIndex=(((cycleStartIndex[aa+1]-maxStartCopyIndex)>40)?(maxStartCopyIndex+40):cycleStartIndex[aa+1]);
                // int endCopyIndex=cycleStartIndex[aa+1];
                int[] temparrayMax=getSubArray(breathSignal,maxStartCopyIndex,maxEndCopyIndex);
                int peakValue=findMax_inIntArray(temparrayMax);


                // find the minimum value within a breath cycle---> minimum around (+- 1.6sec ) the cycleStartIndex
                int minStartCopyIndex=((cycleStartIndex[aa]-8>0)?(cycleStartIndex[aa]-8):0);
                int minEndCopyIndex=cycleStartIndex[aa]+8;  // will array go out of bounds??
                int[] temparrayMin=getSubArray(breathSignal,minStartCopyIndex,minEndCopyIndex);
                int troughValue=findMin_inIntArray(temparrayMin);

//----------------// check if minimums differ from the cycleStartIndexes ??:---------------------
//                int troughIndex=findMinIndex_inIntArray(temparrayMin)+minStartCopyIndex;
//                if ((troughIndex-cycleStartIndex[aa])!=0) Log.i("AAA","troughIndex:"+troughIndex+"---cycle start"+cycleStartIndex[aa]);
//----------------//----------------------------------------------------------------------------
                // find the difference --> flux
                flux_ret[aa]=peakValue-troughValue;
            }
//            dumpArray(flux_ret);  // print the breath flux values
            return flux_ret;
        }
    }


    //-------subArray function1
    // return a sub-array specified by starting and ending indexes....
    public static int[] getSubArray(int[] originalArray,int startIndex, int endEndex){
        int arrayCopyLength=endEndex-startIndex+1;
        int[] temparray=new int[arrayCopyLength];
        System.arraycopy(originalArray,startIndex,temparray,0,arrayCopyLength);
        return temparray;
    }
    //---------subArray function2
    // return a sub-array specified by an array of each indexes to be returned....
    public static int[] getSubArray(int[] originalArray,int[] indexes){
        int arrayRetSize=indexes.length;
        int[] temparray=new int[arrayRetSize];
        for (int aa=0;aa<arrayRetSize;aa++){
            temparray[aa]=originalArray[indexes[aa]];
        }
        return temparray;
    }


    // check if there is a smaller value around an expected (local) minim
    public static int findSmallerIndex(int[] intArray,int originalIndex){
        int arrayLength=intArray.length;
        int minStartCopyIndex=((originalIndex-8>0)?(originalIndex-8):0); // make sure array does not go out of bounds
        int minEndCopyIndex=((originalIndex+8)>(arrayLength-1))?(arrayLength-1):(originalIndex+8);  // make sure array does not go out of bounds
        int[] temparrayMin=getSubArray(intArray,minStartCopyIndex,minEndCopyIndex);
        int troughIndex=findMinIndex_inIntArray(temparrayMin);
        return troughIndex;
    }

    // cast an int array to a double array
    private static double[] intArray2doubleArray(int[] ints){
        double[] retDouble=new double[ints.length];
        for(int aa=0;aa<ints.length;aa++) retDouble[aa]=(double)ints[aa];
        return retDouble;
    }





}


