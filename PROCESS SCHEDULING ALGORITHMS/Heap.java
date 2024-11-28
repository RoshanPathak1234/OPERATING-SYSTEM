import java.util.ArrayList;

public abstract class Heap<T extends Comparable<T>> {

    // private identifiers

    private boolean key;

    // protected identifiers

    protected ArrayList<T> list;
    protected ArrayList<T> keyList;
    protected ArrayList<T> dataList;

    // protected abstract identifiers

    protected abstract boolean compare(T first, T second);

    protected abstract Heap<T> createHeap(ArrayList<T> heapList);

    protected abstract Heap<T> createHeap(ArrayList<T> keyList, ArrayList<T> dataList);

    // constructors

    public Heap() {
        list = new ArrayList<>();
        key = false;
    }

    public Heap(ArrayList<T> heapList) {
        list = new ArrayList<>(heapList);
        key = false;
        buildHeap();
    }

    public Heap(ArrayList<T> keyList, ArrayList<T> dataList) {
        this.keyList = new ArrayList<>(keyList);
        this.dataList = new ArrayList<>(dataList);
        key = true;
        buildHeap();
    }

    // private methods

    private void swap(int first, int second) {
        ArrayList<T> heapList = getHeapedList();
        T temp = heapList.get(first);
        heapList.set(first, heapList.get(second));
        heapList.set(second, temp);

        if (key) {
            T tempData = dataList.get(first);
            dataList.set(first, dataList.get(second));
            dataList.set(second, tempData);
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return index * 2 + 1;
    }

    private int right(int index) {
        return index * 2 + 2;
    }

    private ArrayList<T> getHeapedList() {
        return (key ? keyList : list);
    }

    private ArrayList<T> getDataList() {
        return (key ? dataList : list);
    }

    private void upHeap(int index) {
        if (index == 0)
            return;
        ArrayList<T> heapList = getDataList();
        int p = parent(index);
        if (compare(heapList.get(index), heapList.get(p))) {
            swap(index, p);
            upHeap(p);
        }
    }

    private void downHeap(int index) {
        int extreme = index;
        int left = left(index);
        int right = right(index);
        ArrayList<T> heapList = getDataList();

        if (left < heapList.size() && compare(heapList.get(left), heapList.get(extreme))) {
            extreme = left;
        }
        if (right < heapList.size() && compare(heapList.get(right), heapList.get(extreme))) {
            extreme = right;
        }

        if (extreme != index) {
            swap(extreme, index);
            downHeap(extreme);
        }
    }

    private void buildHeap() {
        for (int i = getHeapedList().size() / 2 - 1; i >= 0; i--) {
            downHeap(i);
        }
    }

    private ArrayList<T> heapSortInternal() {
        ArrayList<T> sortedList = new ArrayList<>();
        while (!getHeapedList().isEmpty()) {
            sortedList.add(this.remove());
        }
        return sortedList;
    }

    // public methods

    public void insert(T value) {
        getHeapedList().add(value);
        upHeap(getHeapedList().size() - 1);
    }

    public void insert(ArrayList<T> heapList) {
        getHeapedList().addAll(heapList);
        buildHeap();
    }

    public T remove() {
        ArrayList<T> heapList = getHeapedList();
        ArrayList<T> dataList = getDataList();
        if (heapList.isEmpty())
            throw new IndexOutOfBoundsException("Heap is empty!");

        T value = heapList.get(0);
        T last = heapList.remove(heapList.size() - 1);

        if (!heapList.isEmpty()) {

            heapList.set(0, last);
            downHeap(0);

            if (key) {
                
                T lastData = dataList.remove(dataList.size() - 1);
                dataList.set(0, lastData);
                downHeap(0);
            }
        }

        return value;
    }

    public ArrayList<T> heapSort() {

        Heap<T> temp;

        if (key) {
            ArrayList<T> tempHeap = new ArrayList<>(getHeapedList());
            ArrayList<T> tempDataHeap = new ArrayList<>(getDataList());
            temp = createHeap(tempHeap, tempDataHeap);
        } else {

            ArrayList<T> tempHeap = new ArrayList<>(getHeapedList());
            temp = createHeap(tempHeap);

        }
        return temp.heapSortInternal();
    }

    public ArrayList<T> getHeap() {
        return new ArrayList<>(getHeapedList());
    }
}