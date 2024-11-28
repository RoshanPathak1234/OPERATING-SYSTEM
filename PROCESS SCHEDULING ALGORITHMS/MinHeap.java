import java.util.ArrayList;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    public MinHeap() {
        super();
    }

    public MinHeap(ArrayList<T> heapList) {
        super(heapList);
    }
    public MinHeap(ArrayList<T> keyList , ArrayList<T> dataList) {

        super(keyList , dataList);
    }

    @Override
    protected boolean compare(T first, T second) {
        return first.compareTo(second) < 0;
    }

    @Override
    protected Heap<T> createHeap(ArrayList<T> heapList) {
        return new MinHeap<>(heapList);
    }
    @Override
    protected Heap<T> createHeap( ArrayList<T> keyList , ArrayList<T> dataList) {
        return new MinHeap<>(keyList , dataList);
    }
}
