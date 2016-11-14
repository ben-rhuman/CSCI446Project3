package aiproject3.Algorithms;

import aiproject3.Algorithms.Pair;


public class MinHeap {

    private Pair[] Heap;
    private int size;
   

    private int FRONT = 1;

    public MinHeap(int maxsize) {
        
        this.size = 0;
        Heap = new Pair[maxsize + 1];
        
        Heap[0] = new Pair(-1,0);
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private boolean isLeaf(int pos) {
        if (pos >= (size / 2) && pos <= size) {
            return true;
        }
        return false;
    }

    private void swap(int fpos, int spos) {
        Pair tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            if (Heap[pos].dist > Heap[leftChild(pos)].dist || Heap[pos].dist > Heap[rightChild(pos)].dist) {
                if (Heap[leftChild(pos)].dist < Heap[rightChild(pos)].dist) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    public void insert(Pair p) {
        
        Heap[++size] = p;
        int current = size;

        while (Heap[current].dist < Heap[parent(current)].dist) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + Heap[i].dist + " LEFT CHILD : " + Heap[2 * i].dist
                    + " RIGHT CHILD :" + Heap[2 * i + 1].dist);
            System.out.println();
        }
    }

    public void minHeap() {
        for (int pos = (size / 2); pos >= 1; pos--) {
            minHeapify(pos);
        }
    }

    public Pair remove() {
        Pair popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--];
        minHeapify(FRONT);
        return popped;
    }
}
