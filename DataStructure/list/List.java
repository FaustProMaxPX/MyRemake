package list;

/**
 * The structure that stores data in a linear list
 * @author Faust
 */
public interface List <T> {

    /**
     * add a data at the head of list
     * @param data the data needs to be added, not null.
     */
    void addFirst(T data);

    /**
     * add a data at the end of list
     * @param data the data needs to be added, not null
     */
    void addLast(T data);

    /**
     * add a data at the given index in the list
     * @param data the data needs to be added, not null
     * @param index the position of the new data, index >= 0 and index <= list's size
     */
    void add(T data, int index);

    /**
     * set the data at position {index} to {data}
     * @param data the new data need to be put in
     * @param index the position of that data
     */
    void set(T data, int index);

    /**
     * get the size of list
     * @return the number of datas in the list
     */
    int size();

    /**
     * get the data in the position {index}
     * @param index the position of wanted data, index >= 0 and index < list's size
     * @return data in the position {index} 
     */
    T get(int index);
}