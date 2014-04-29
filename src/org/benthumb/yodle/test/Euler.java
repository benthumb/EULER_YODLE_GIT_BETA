package org.benthumb.yodle.test;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileReader;

public class Euler {
  /**
   * @param args
   */
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    ArrayList<List> listOfLists = new ArrayList<List>();
    // **************** READ FILE **************** //
    // File f = new File(
    // "C:\\Users\\Paul\\workspace\\EULER_YODLE\\src\\org\\benthumb\\yodle\\test\\triangle.txt");
    File f = new File(
        "C:\\Users\\Paul\\workspace\\EULER_YODLE\\src\\org\\benthumb\\yodle\\test\\small_triangle_yodle.txt");

    int n = 0;
    List<String> prevList = new LinkedList<String>();
    List<String> currList = new LinkedList<String>();
    int[] nPrevLine = null;
    int[] ans = null;
    int subIter = -1;

    try {
      // **************** For each line: split on " " **************** //
      BufferedReader bufr = new BufferedReader(new FileReader(f));
      for (int i = 0; i < f.length(); i++) {
        String line = bufr.readLine();
        if (line != null) {
          String[] tempList = line.split(" ");
          listOfLists.add(Arrays.asList(tempList));
        }
      }
      bufr.close();
      
      if (listOfLists != null) {
        for (int k = 0; k < listOfLists.size(); k++) {
          if (k + 1 < listOfLists.size()) {
            if (k == 0) {
              System.out.println("-------------------- FIRST ITERATION ...");
              prevList = listOfLists.get(k);
              currList = listOfLists.get(k + 1);
              ans = Euler.sumNeighbors(prevList, currList);
              nPrevLine = Euler.filterByLogicalGe(ans, subIter);
              subIter++;
              ans = null;
            } else {
              System.out
                  .println("-------------------- SUBSEQUENT ITERATION ...");
              currList = listOfLists.get(k + 1);
              ans = Euler.sumNeighbors(nPrevLine, currList);
              nPrevLine = null;
              nPrevLine = Euler.filterByLogicalGe(ans, subIter);
              subIter++;
              ans = null;
            }
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Couldn't read file: " + e.getMessage());
    }
  }

  public static int[] filterByLogicalGe(int[] list, int iter) {
    int lSize = ((list.length - 2) / 2) + 2;
    int[] str = new int[lSize];
    int lftI = 1;
    int rgtI = 2;

    if (list.length == 2) {
      str[0] = list[0];
      str[1] = list[1];
      return str;
    }
    int finalSlot = list.length - 2;
    int rvsI = 1;
    int slot;
    int subArrl = list.length - 3;

    subArrl = subArrl - iter;
    for (int i = 0; i < (subArrl); i++) {
      slot = finalSlot - (finalSlot - rvsI);
      if (list[lftI] > list[rgtI]) {
        str[slot] = list[lftI];
      } else {
        str[slot] = list[rgtI];
      }
      rvsI++;
      lftI = lftI + 2;
      rgtI = rgtI + 2;
    }
    str[0] = list[0];
    str[str.length - 1] = list[list.length - 1];

    for (int r = 0; r < str.length; r++) {
      System.out.println(">>>> Got number from logical ge filtered array: "
          + str[r]);
    }
    return str;
  }

  // Perfect sums neighbors and populates an array of primitives accordingly ...
  static int[] sumNeighbors(List<String> lstA, List<String> lstB) {
    int arrAllo = 2 * lstA.size();
    int[] staArr = new int[arrAllo];

    // Get of nested loops and use one counter ... I think this is probably the
    // answer.
    // Left
    int leftItr = 0;
    int rightItr = 1;
    for (int j = 0; j < lstA.size(); j++) {
      int adv = j + 1;
      int prev = Integer.parseInt(lstA.get(j));
      int nxtL = Integer.parseInt(lstB.get(j));
      int nxtR = Integer.parseInt(lstB.get(adv));

      int nSumL = nxtL + prev;
      int nSumR = nxtR + prev;
      staArr[leftItr] = nSumL;
      staArr[rightItr] = nSumR;

      leftItr = leftItr + 2;
      rightItr = rightItr + 2;

    }
    // Show contents
    for (int k = 0; k < staArr.length; k++) {
      // System.out.println("List B: " + lstB.get(k));
      // System.out.println("Populated static array: " + staArr[k]);
    }
    return staArr;
  }

  static int[] sumNeighbors(int[] lstA, List<String> lstB) {
    int arrAllo = 2 * lstA.length;
    int[] staArr = new int[arrAllo];

    // Get of nested loops and use one counter ... I think this is probably the
    // answer.
    // Left
    int leftItr = 0;
    int rightItr = 1;
    int calcIter = 1;
    for (int j = 0; j < lstA.length; j++) {
      int adv = j + 1;
      int prev = lstA[j];

      int nxtL = Integer.parseInt(lstB.get(j));
      int nxtR = Integer.parseInt(lstB.get(adv));

      int nSumL = nxtL + prev;
      calcIter++;
      int nSumR = nxtR + prev;
      calcIter++;

      staArr[leftItr] = nSumL;
      staArr[rightItr] = nSumR;

      leftItr = leftItr + 2;
      rightItr = rightItr + 2;
    }
    // Show contents
    for (int k = 0; k < staArr.length; k++) {
      System.out.println("Populated static array: " + staArr[k]);
    }
    System.out.println("------------------- Returning array: ");
    return staArr;
  }
}
