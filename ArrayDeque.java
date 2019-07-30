public class ArrayDeque<T> {

    private T[] items;
    private int front;
    private int back;
    private int size;
    private int rFactor = 2;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        front = back = 4;
        size = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i = i + 1) {
            a[i] = get(i);
        }
        items = a;
        front = 0;
        back = size - 1;
    }

    public void addFirst(T item) {
        if (isEmpty()) {
            items[front] = item;
            // empty so place item at front
            size += 1;
        } else if (size == items.length) {
            // array full - need to resize for new entries
            resize(size * rFactor); // resizing operations
            addFirst(item); // now addFirst
        } else {
            front = Math.floorMod(front - 1, items.length);
            // loop to tail of array if front - 1 index exceeds array head
            items[front] = item;
            size += 1;
        }
    }

    public void addLast(T item) {
        if (isEmpty()) {
            items[back] = item;
            size += 1;
        } else if (size == items.length) {
            resize(size * rFactor);
            addLast(item);
        } else {
            back = Math.floorMod(back + 1, items.length);
            // loop to head of array if front + 1 index exceeds array tail
            items[back] = item;
            size += 1;
        }
    }

    private double usageRatio() {
        return size / (double) items.length;
    }


    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T item = items[front];
            items[front] = null; // no loitering
            if (front != back) {  // if more than one item than update front
                front = Math.floorMod(front + 1, items.length);
                // loops around to array head to update front if exceeding array tail
            }
            size -= 1;
            if ((items.length > 16) && (usageRatio() < 0.25)) {
                // if usage ratio < 0.25, half array size
                // but we don't care about usage for arrays less than length 16
                resize(items.length / 2);
            }
            return item;
        }
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T item = items[back]; // last item
            items[back] = null; // no loitering
            if (front != back) { // update front if more than one item
                back = Math.floorMod(back - 1, items.length);
                // loops around to array tail to update back if exceeding array head
            }
            size -= 1;
            if ((items.length > 16) && (usageRatio() < 0.25)) {
                // if usage ratio < 0.25, half array size
                // but we don't care about usage for arrays less than length 16
                resize(items.length / 2);
            }
            return item;
        }
    }

    public T get(int index) {
        int arrayIndex = Math.floorMod(front + index, items.length);
        // finds corresponding index in array:
        // array index = (front + user index) % length
        // the mod is for looping to the array head if exceeding array tail
        return items[arrayIndex];
    }

    public void printDeque() {
        for (int i = 0; i < size; i = i + 1) {
            System.out.print(get(i));
            System.out.print(" ");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
