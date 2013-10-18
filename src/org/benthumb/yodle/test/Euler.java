package org.benthumb.yodle.test;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileReader;

// Pathway in HTML5 ?? The GRAPH ... add navigation cues l and r would have to go bottom to top ...

public class Euler {

	private static int n = 0;
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<List> listOfLists = new ArrayList<List>();
		// **************** READ FILE **************** //
		//		File f = new File(
		//		 "C:\\Users\\Paul\\workspace\\EULER_YODLE\\src\\org\\benthumb\\yodle\\test\\triangle.txt");
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
			if (listOfLists != null) {
				for (int k = 0; k < listOfLists.size(); k++) {
					//					System.out.println("Contents: "
					//							+ listOfLists.get(k).toString());
					if (k + 1 < listOfLists.size()) {
						System.out.println("+++++++++ BOOM +++++++++");
						if(k == 0){
							System.out.println("-------------------- FIRST ITERATION ...");
							prevList = listOfLists.get(k);
							currList = listOfLists.get(k + 1);
							ans = Euler.sumNeighbors(prevList,currList);
							//for(int l = 0; l < ans.length; l++){
							//System.out.println("Got sum from sum neighbor routine: " + ans[l]);
							nPrevLine = Euler.filterByLogicalGe(ans,subIter);
							subIter++;
							ans = null;
						}else{
							System.out.println("-------------------- SUBSEQUENT ITERATION ...");
							currList = listOfLists.get(k + 1);
							ans = Euler.sumNeighbors(nPrevLine,currList);
							nPrevLine = null;
							//for(int l = 0; l < ans.length; l++){
							//System.out.println("Got sum from sum neighbor routine: " + ans[l]);
							nPrevLine = Euler.filterByLogicalGe(ans,subIter);
							subIter++;
							ans = null;
						}
					}
				}
			}
		}catch (Exception e) {
			System.out.println("Couldn't read file: " + e.getMessage());
		}

		//Euler.filterByLogicalGe();

	}

	// Perfect ... culls the highest sums after ... you know what! Needs a different data structure ... dough
	public static int[] filterByLogicalGe(int[] list,int iter){
		System.out.println("Size of our f'in LIST: " + list.length);
		int lSize = ((list.length-2)/2)+2;
		System.out.println("Determined size of new array: " + lSize);
		int[] str = new int[lSize];
		int lftI = 1;
		int rgtI = 2;
		if(list.length == 2){
			str[0] = list[0];
			//str.append("|");
			str[1] = list[1];
			return str;
		}
		int finalSlot = list.length - 2;
		int rvsI = 1;
		int slot;
		int subArrl = list.length - 3;
		System.out.println("Size of sub-array: " + subArrl);
		// **************** THIS IS THE KEY!  sub-array size must negative iterate **************** //
		//		if(subArrl > 1 && subArrl < 4){
		//			subArrl = subArrl - 1;
		//		}
		//		
		//		if(subArrl > 3){
		//			subArrl = subArrl - 2;
		//		}
		subArrl = subArrl - iter;
		// **************** THIS IS THE KEY! sub-array size must negative iterate **************** //
		for(int i = 0; i < (subArrl); i++){
			System.out.println("Entering LOOP" + i);
			slot = finalSlot - (finalSlot - rvsI);
			if(list[lftI] > list[rgtI]){
				System.out.println("Where dying: left bigger");
				str[slot] = list[lftI];
			}
			else{
				System.out.println("Where dying: right bigger");
				str[slot] = list[rgtI];
			}
			rvsI++;
			lftI = lftI + 2;
			rgtI = rgtI + 2;
		}
		str[0] = list[0];
		str[str.length-1] = list[list.length-1];

		//System.out.println("Returning from logical ge: our final array is SIZE: " + str.length);
		//System.out.println("Show contents of newly populated logical ge filtered array: ");
		for(int r = 0; r < str.length; r++){
			System.out.println(">>>> Got number from logical ge filtered array: " + str[r]);
		}
		return str;
	}

	// Perfect sums neighbors and populates an array of primitives accordingly ...
	static int[] sumNeighbors(List<String> lstA, List<String> lstB){

		int arrAllo = 2*lstA.size();
		int[] staArr = new int[arrAllo];
		System.out.println("What the f**k is our length: " + staArr.length);

		// Get of nested loops and use one counter ... I think this is probably the answer.
		//Left
		int leftItr = 0;
		int rightItr = 1;
		for(int j = 0; j < lstA.size(); j++){
			int adv = j+1;
			int prev = Integer.parseInt(lstA.get(j));
			//			System.out.println("** Got nxt int: " + prev + " Count: " + j);
			int nxtL = Integer.parseInt(lstB.get(j));
			int nxtR = Integer.parseInt(lstB.get(adv));
			//			System.out.println("* Got prev int: " + nxtL + " Count: " + j);
			//			System.out.println("* Got prev int: " + nxtR + " Count: " + j);
			int nSumL = nxtL + prev;
			int nSumR = nxtR + prev;
			//			System.out.println("*** Count - inner loop: " + j);
			//			System.out.println("----> Sum ..." + nSumL);
			//			System.out.println("----> Sum ..." + nSumR);
			staArr[leftItr] = nSumL;
			staArr[rightItr] = nSumR;

			leftItr = leftItr + 2;
			rightItr = rightItr + 2;

		}
		// Show contents
		for(int k = 0; k < staArr.length; k++){
			//System.out.println("List B: " + lstB.get(k));
			//			System.out.println("Populated static array: " + staArr[k]);
		}
		return staArr;
	}

	static int[] sumNeighbors(int[] lstA, List<String> lstB){
		System.out.println(">>>>>>>>>>>> LIST A SIZE: " + lstA.length);
		for(int p = 0; p < lstA.length; p++){
			System.out.println("Contents of LIST A: " + lstA[p]);
		}
		System.out.println(">>>>>>>>>>>> LIST B SIZE: " + lstB.size());
		for(int q = 0; q < lstB.size(); q++){
			System.out.println("Contents of LIST B: " + lstB.get(q));
		}
		int arrAllo = 2*lstA.length;
		int[] staArr = new int[arrAllo];
		System.out.println("What the f**k is our length: " + staArr.length);

		// Get of nested loops and use one counter ... I think this is probably the answer.
		//Left
		int leftItr = 0;
		int rightItr = 1;
		int calcIter = 1;
		for(int j = 0; j < lstA.length; j++){
			int adv = j+1;
			int prev = lstA[j];
			System.out.println("** Got nxt int: " + prev + " Count: " + j);
			int nxtL = Integer.parseInt(lstB.get(j));
			int nxtR = Integer.parseInt(lstB.get(adv));
			System.out.println("* Got prev int: " + nxtL + " Count: " + j);
			System.out.println("* Got prev int: " + nxtR + " Count: " + j);
			int nSumL = nxtL + prev;
			calcIter++;
			int nSumR = nxtR + prev;
			calcIter++;
			System.out.println("*** Count - inner loop: " + j);
			System.out.println("----> Sum ..." + nSumL);
			System.out.println("----> Sum ..." + nSumR);
			staArr[leftItr] = nSumL;
			staArr[rightItr] = nSumR;

			leftItr = leftItr + 2;
			rightItr = rightItr + 2;
			//System.out.println("Calcuation iterator at: " + calcIter);

		}
		// Show contents
		for(int k = 0; k < staArr.length; k++){
			System.out.println("Populated static array: " + staArr[k]);
		}
		System.out.println("------------------- Returning array: ");
		return staArr;

	}
}

