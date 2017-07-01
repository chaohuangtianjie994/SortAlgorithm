package sort;

import org.junit.Test;
/**
 * 总结排序算法：
 * 冒泡排序、快速排序
 * 直接插入排序、折半插入排序、希尔排序
 * 选择排序、堆排序
 * 归并排序
 * 二分查找算法(递归、迭代)
 * @author ywq
 *
 *
 */
public class Solution {
	@Test
	public void sort(){
		int[] num = {10,2,3,1,67,49,38,65,97,76,13,27,0};
//		for (int i : num) {
//			System.out.print(i+" ");
//		}
//		System.out.println();
		bubbleSort(num);
//		quickSort(num, 0, num.length-1);
		
//		insertSort(num);
//		shellSort(num);
//		halfinsertSort(num);
		
//		selectSort(num);
//		heapSort(num);
		
//		mergeSort(num, 0, num.length-1);
		
		System.out.println(halfSearch2(num,0,num.length-1,3));
		for (int i : num) {
			System.out.print(i+" ");
		}
	}
	
	/**
	 * 从后往前的冒泡排序
	 * @param num
	 */
	public void bubbleSort(int[] num){
		if(num==null)
			return;
		// 设置标志位
		boolean flag = true;
		for (int i = 0; i < num.length&&flag; i++) {
			flag = false;
			for (int j = num.length-1; j > 0; j--) {
				if(num[j]<num[j-1]){
					int temp = num[j];
					num[j] = num[j-1];
					num[j-1] = temp;
					flag = true; // 有交换时，将标志位重置为true
				}
			}
		}
	}
	/**
	 * 快速排序
	 * @param num
	 * @param left
	 * @param right
	 */
	public void quickSort(int[] num,int left,int right){
		if(num==null)
			return ;
		int i = left;
		int j = right;
		int temp = num[i];
		if(i<j){
			while(i<j){
				while(i<j&&num[j]>=temp){
					j--;
				}
				num[i] = num[j];
				while(i<j&&num[i]<=temp){
					i++;
				}
				num[j] = num[i];
				
				num[i] = temp; // 此处不可遗漏，将基准值插入到指定位置
			}
			quickSort(num, left, i-1);
			quickSort(num, i+1, right);
		}
	}
	
	
	/**
	 * 直接插入排序算法
	 * @param num
	 */
	public void insertSort(int[] num){
		if(num==null)
			return ;
		for (int i = 1; i < num.length; i++) {
			int temp = num[i] ;
			int j ;       // 记得是j>=0不要忘记等号，否则，前两个将不参与比较
			for (j = i-1; j >= 0&&num[j]>temp; j--) {
				num[j+1] = num[j];  //将前面的较大元素往后移动
			}
				num[j+1] = temp;  // 将num[i]放在指定位置
		}
	}
	/**
	 * 希尔排序，分组的直接插入排序
	 * 多加了一成循环，增量d的初始值为数组长度的一半
	 * @param num
	 */
	public void shellSort(int[] num){
		if(num==null)
			return ;
		for(int d = num.length/2 ; d > 0 ; d=d/2){
			for(int i = d;i<num.length;i+=d){
				int temp = num[i];
				int j;
				for(j = i-d;j>=0&&num[j]>temp;j-=d){
					num[j+d] = num[j];
				}
					num[j+d] = temp;
			}
		}
	}
	/**
	 * 折半插入排序:
	 * left = 0;
	 * right = i-1; 目标是将第i个元素插入到前面的某个位置，所以right为i-1
	 * @param num
	 */
	public void halfinsertSort(int[] num){
		if(num==null)
			return ;
		for (int i = 1; i < num.length; i++) {
			int left = 0;
			int right = i-1;
			int temp = num[i] ;
			while(left<=right){
				int mid = (left+right)/2;
				if(num[mid]>temp){
					right = mid-1;
				}else if(num[mid]<temp){
					left = mid+1;
				}
			}
			// 经过上边的循环，知道num[i]应该插入到left位置
			int j ;      
			for (j = i-1; j >= left; j--) {
				num[j+1] = num[j];  //将前面的较大元素往后移动
			}
				num[left] = temp;  // 将num[i]放在指定位置
		}
	}
	
	
	
	/**
	 * 选择排序：外层循环控制着趟数，每趟的num[i]我们认为是最小值
	 * @param num
	 */
	public void selectSort(int[] num){
		if(num==null)
			return ;
		for(int i = 0;i<num.length;i++){
			// 内层循环j=i+1,外层循环控制着循环次数。
			// 即每趟中num[i]这个值就是本趟的最小值。i位置上是最小值 
			for (int j = i+1; j < num.length; j++) {
				if(num[i]>num[j]){
					int temp = num[i];
					num[i] = num[j];
					num[j] = temp;
				}
			}
		}
	}
	/**
	 * 堆排序：
	 * 1、构建大顶堆
	 * 2、交换堆顶和末尾元素
	 * 3、继续构建大顶堆
	 * @param num
	 */
	public void heapSort(int[] num){
		if(num==null)
			return ;
		// 构建大顶堆
		for (int parent = (num.length)/2; parent>=0; parent--) {
			getMaxHeap(num,parent,num.length-1);
		}
		// 取堆顶元素进行交换，重新构建大顶堆
		for(int t = num.length-1;t>0;t--){
			swap(num,0,t);
			getMaxHeap(num, 0, t-1);
		}
	}
	/**
	 * 交换数组中的两个位置的元素
	 * @param num
	 * @param i
	 * @param t
	 */
	private void swap(int[] num, int i, int t) {
		if(num==null)
			return ;
		int temp = num[i];
		num[i] = num[t];
		num[t] = temp;
	}
	/**
	 * 构建大顶堆的方法  
     * s 代表拥有左右孩子节点的节点，即本次要调整位置的节点  
     * length 代表当前堆的长度
	 * @param num
	 * @param s
	 * @param length
	 */
	private void getMaxHeap(int[] num, int s, int length) {
		if(num==null)
			return ;
		int temp = num[s];
		 // j=2*j+1是要找到j的孩子节点 
		for(int j = 2*s+1;j<length;j=2*j+1){ // j=2*s+1为s节点的左孩子，j+1为s节点的右孩子 
			// 将j指向子节点中较大值
			if(num[j]<num[j+1])
				j++;
			// 判断父节点和子节点最大值的关系
			if(temp>=num[j])
				break ; // 如果s节点的值大于其最大的孩子节点值，则，循环结束，本次不做任何改变
			// 否则
			num[s] = num[j]; // 否则将较大的孩子节点的值赋值给父节点   
			s = j; // 将j的值赋值给s，即j成为了下一波的父节点，继续比较
		}
		num[s] = temp; // 将temp放在指定位置
	}
	
	
	
	/**
	 * 归并排序：
	 * 将两个（或两个以上）有序表合并成一个新的有序表 即把待排序序列分为若干个子序列， 
     *     每个子序列是有序的。然后再把有序子序列合并为整体有序序列  
	 * @param num
	 * @param left
	 * @param right
	 */
	public void mergeSort(int[] num,int left,int right){
		if(num==null)
			return ;
		int mid = (left+right)/2;
		if(left<right){
			// 处理左边
			mergeSort(num, left, mid);
			// 处理右边
			mergeSort(num, mid+1, right);
			// 左右归并
			merge(num, left, mid, right);
		}
	}
	private void merge(int[] num, int left, int mid, int right) {
		// 定义一个辅助数组，所以该算法的空间复杂度为O(N)
		int[] temp = new int[right-left+1];
		int i = left;
		int j = mid+1;
		int k = 0;
		// 找出较小值元素放入temp数组中
		while(i<=mid&&j<=right){
			if(num[i]<num[j])
				temp[k++] = num[i++];
			else
				temp[k++] = num[j++];
		}
		// 处理较长部分
		while(i<=mid){
			temp[k++] = num[i++];
		}
		while(j<=right){
			temp[k++] = num[j++];
		}
		// 使用temp中的元素覆盖num中的元素
		for(int k2 = 0;k2<temp.length;k2++){
			num[k2+left] = temp[k2];
		}
	}
	
	/**
	 * 基于循环的二分查找实现算法
	 * @param num
	 * @param target
	 * @return
	 */
	public int halfSearch(int[] num,int target){
		if(num==null)
			return -1;
		int left = 0;
		int right = num.length-1;
		while(left<=right){
			int mid = (left+right)/2;
			int midValue = num[mid];
			if(midValue>target)
				right = mid-1;
			else if(midValue<target)
				left = mid+1;
			else
				return mid;
		}
		return -1;
	}
	/**
	 * 基于递归实现的二分查找算法
	 * @param num
	 * @param left
	 * @param right
	 * @param target
	 * @return
	 */
	public int halfSearch2(int[] num,int left,int right,int target){
		if(num==null)
			return -1;
		int mid = (left+right)/2;
		int midValue = num[mid];
		if(left<=right){
			if(midValue>target)
				return halfSearch2(num, left, mid-1, target);
			else if (midValue<target) 
				return halfSearch2(num, mid+1, right, target);
			else
				return mid;
		}
		return -1;
	}
}
