public class MyArrayList {
    private Cow[] elems;
    private int size;

    // Runtime: O(1)
    public MyArrayList() {
        this(10);
    }

    // Runtime: O(1)
    public MyArrayList(int capacity) {
        this.elems = new Cow[capacity];
        this.size = 0;
    }

    // Resizing by factor of 2 is O(n)
    private void resize(int newSize) {
        Cow[] temp = new Cow[newSize];
        System.arraycopy(elems, 0, temp, 0, size); // O(n)
        elems = temp;
    }

    // Runtime: O(1)*, amortized runtime as resizing over large n value will make add have an average constant run time
    public void add(Cow c) {
        if (size == elems.length) {
            resize(size * 2);
        }

        elems[size] = c;
        size++;
    }

    // Runtime: O(1)
    public int size() {
        return size;
    }

    // Runtime: O(1)
    public Cow get(int index) {
        if (index < 0 || index >= size || !(elems[index] instanceof Cow)){
            throw new IndexOutOfBoundsException("No Cow at given index");
        }
        return elems[index];
    }

    // Runtime: O(n)*, b/c have to shift elements over
    public Cow remove(int index) {
        Cow removedCow = this.get(index);

        // Remove from last position. No need to shift array.
        if (index == size - 1) {
            this.removeLast();
        } else {
            elems[index] = null;

            for (int i = index; i < elems.length - 1; i++) {
                elems[i] = elems[i+1];
            }
            size--;
        }

        if (elems.length > 10 && size <= elems.length * .25) {
            resize(elems.length / 2);
        }

        return removedCow;
    }

    private void removeLast() {
        elems[elems.length - 1] = null;
        size--;
    }

    // Runtime: O(n)*, b/c have to shift elements over
    public void add(int index, Cow c) {
        // What if add at index greater than size but less than elem.length? Normally add to back or add to middle.
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == elems.length) {
            resize(size * 2);
        }

        for (int i = elems.length - 2; i > index; i--) {
            elems[i] = elems[i-1];
        }

        elems[index] = c;

        size++;
    }
}