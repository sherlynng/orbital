// selection_sort.c
//GIT test edit text

#include <stdio.h>
#define MAX_ELEMENTS 10

void selectionSort(int [], int);
void scanArray(int [], int);
void printArray(int [], int);

int main(void) {
	int list[MAX_ELEMENTS];

	scanArray(list, MAX_ELEMENTS);

	printf("Before sort:\n");
	printArray(list, MAX_ELEMENTS); 

	selectionSort(list, MAX_ELEMENTS);

	printf("After sort:\n");
	printArray(list, MAX_ELEMENTS); 

	return 0;
}

// To sort arr in increasing order
void selectionSort(int arr[], int size) {
	int i, start, min_index, temp;

	for (start = 0; start < size-1; start++) {
		// each iteration of the for loop is one pass

		// find the index of minimum element 
		min_index = start;
		for (i = start+1; i < size; i++) 
			if (arr[i] < arr[min_index]) 
				min_index = i;

		// swap minimum element with element at start index
		temp = arr[start];
		arr[start] = arr[min_index];
		arr[min_index] = temp;
	}
}

// To read values into arr
void scanArray(int arr[], int size) {
	int i;

	printf("Enter %d values: ", size);
	for (i=0; i<size; i++) 
		scanf("%d", &arr[i]);
}

// To print values of arr
void printArray(int arr[], int size) {
	int i;

	for (i=0; i<size; i++) 
		printf("%d ", arr[i]);

	printf("\n");
}

