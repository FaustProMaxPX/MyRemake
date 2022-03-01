/**
 * The interface of an immutable list
 * 
 */
public interface ImList<E> {

    /**
     * return an empty list
     * @param <E> the type of data in this list
     * @return an empty list1
     */
    public static <E> ImList<E> empty()
    {
        return new Empty<>();
    }

    /**
     * generate an list contains elt and all element of the this list
     * elt will be the first element in new ImList
     * @param elt the new element, not null
     * @return the new ImList where elt is the first element and remains are the elements in this list 
     */
    ImList<E> cons(E elt);

    /**
     * get the first element in this list
     * @return the first element in the list
     */
    E first();

    /**
     * get the list of rest elements
     * @return a list of all rest elements
     */
    ImList<E> rest();    
}