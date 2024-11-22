import java.util.ArrayList;
public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

    public MaxHeap() {
        super();
    }

    public MaxHeap(ArrayList<T> heapList) {
        super(heapList);
    }

    public MaxHeap(ArrayList<T> keyList , ArrayList<T> dataList) {

        super(keyList , dataList);
    }
    @Override
    protected boolean compare(T first, T second) {
        return first.compareTo(second) > 0;
    }

    @Override
    protected Heap<T> createHeap(ArrayList<T> heapList) {
        return new MaxHeap<>(heapList);
    }
    @Override
    protected Heap<T> createHeap(ArrayList<T> keyList , ArrayList<T> dataList) {
        return new MaxHeap<>(keyList , dataList);
    }
}
