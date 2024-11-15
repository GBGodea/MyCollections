package mycollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class MyArrayList<T> implements Collection<T>, Iterable<T> {
    private Object[] arr;
    int defaultCapacity = 10;
    int size = 0;

    public MyArrayList() {
        arr = new Object[defaultCapacity];
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iter = new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && arr[currentIndex] != null;
            }

            @Override
            public T next() {
                return (T) arr[currentIndex++];
            }
        };
        return iter;
    }

    public T get(int index) {
        if (index > size - 1) {
            throw new IndexOutOfBoundsException("Your index: " + index + " is out of bound! " + "Available index: " + (size - 1));
        }
        return (T) arr[index];
    }

    public void set(int index, T value) {
        if (index > size - 1) {
            throw new IndexOutOfBoundsException("Your index: " + index + " is out of bound! " + "Available index: " + (size - 1));
        }

        arr[index] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() < 1;
    }

    @Override
    public boolean contains(Object o) {
        for (Object element : arr) {
            if (o.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(arr, size);
    }

    @Override
    public T[] toArray(Object[] a) {
        a = Arrays.copyOf(a, size, a.getClass());
        System.arraycopy(arr, 0, a, 0, size);
        return (T[]) a;
    }


    //        Object[] result = new Object[size() + 1];

    //        int i = 0;
//        int j = 0;
//        while(i < result.length) {
//            if(i == index) {
//                result[i] = value;
//                i++;
////                j++;
//                continue;
//            }
//            result[i] = arr[j];
//            i++;
//            j++;
//        }

//    public void add(int index, T value) {
//        if (index > size()) {
//            throw new IndexOutOfBoundsException("Выбран индекс: " + index + " Но максимальный размер: " + size());
//        }
//
//
//        Object[] left = Arrays.copyOfRange(arr, 0, index + 1);
//        Object[] right = Arrays.copyOfRange(arr, index, size());
//        Object[] result = new Object[left.length + right.length];
//        left[index] = value;
//
//        System.arraycopy(left, 0, result, 0, left.length);
//        System.arraycopy(right, 0, result, index + 1, right.length);
//        arr = result;
//    }

    //        Object[] obj = new Object[capacity];
//        if(size() == arr.length) {
//           obj = new Object[capacity *= 2];
//        }
//
//        for(int i = 0; i < arr.length; i++) {
//            obj[i] = arr[i];
//        }
//        size++;

//    if(value.getClass().isInstance(arr)) {
//        if (index > size() || index < 0) {
//            throw new IndexOutOfBoundsException("Index chosen: " + index + ", available diapason: 0 - " + size());
//        }
//
//        resize();
//
//        System.arraycopy(arr, index, arr, index + 1, size() - index - 1);
//
//        arr[index] = value;
//    } else {
//        throw new ClassCastException("You can't add: " + value.getClass().getName() + " in " + arr.getClass().getName());
//    }

    public void add(int index, T value) {

        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index chosen: " + index + ", available diapason: 0 - " + size());
        }

        System.arraycopy(arr, index, arr, index + 1, size() - index);

        arr[index] = value;
        resize();
    }

    //    @Override
    public boolean add(T value) {
        arr[size] = value;
        resize();
        return true;

    }

    public void resize() {
        size++;
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length << 1);
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Number) {
            try {
                System.arraycopy(arr, Integer.parseInt((o).toString()) + 1, arr, Integer.parseInt((o).toString()), size - Integer.parseInt((o).toString()) - 1);
                arr = Arrays.copyOf(arr, size--);
            } catch (ArrayIndexOutOfBoundsException boundsException) {
                throw new IndexOutOfBoundsException("Allowable array length to take data: " + (size - 1) + " Your input " + o);
            }
            return true;
        } else {
            for (int i = 0; i < size; i++) {
                if (arr[i].toString().equals(o.toString())) {
                    System.arraycopy(arr, i + 1, arr, i, size - i - 1);
                    arr = Arrays.copyOf(arr, size--);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        int cSize = c.size();
        if (cSize == 0) {
            return false;
        }

        arr = Arrays.copyOf(arr, size + cSize);
        System.arraycopy(c.toArray(), 0, arr, size, cSize);
        size += cSize;
        return true;
    }

    @Override
    public void clear() {
        arr = new Object[defaultCapacity];
        size = 0;
    }

    @Override
    public boolean retainAll(Collection c) {
        MyArrayList<T> array = new MyArrayList<>();
        for (Object cElement : c) {
            for (int i = 0; i < size; i++) {
                if (cElement.equals(arr[i])) {
                    array.add((T) cElement);
                }
            }
        }

        if(!array.isEmpty()) {
            size = array.size();
            arr = array.toArray();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        int counter = 0;
        for (Object cElement : c) {
            for (int i = 0; i < arr.length; i++) {
                if (cElement.equals(arr[i])) {
                    System.arraycopy(arr, i + 1, arr, i, size - i - 1);
                    arr = Arrays.copyOf(arr, size--);
                }
            }
        }
        return counter == c.size();
    }

    @Override
    public boolean containsAll(Collection c) {
        int counter = 0;
        for (Object cElement : c) {
            for (Object arrElement : arr) {
                if (cElement.equals(arrElement)) {
                    counter++;
                    break;
                }
            }
        }
        return counter == c.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                str.append(arr[i]);
                break;
            } else if (arr[i] != null) {
                str.append(arr[i]).append(",").append(" ");
            }
        }
        str.append("]");
        return str.toString();
    }
}
