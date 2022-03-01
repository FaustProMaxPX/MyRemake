public class Cons<E> implements ImList<E> {

    private final E elt;
    private final ImList<E> rest;

    Cons(E elt, ImList<E> rest)
    {
        this.elt = elt;
        this.rest = rest;
    }

    @Override
    public ImList<E> cons(E elt) {
        return new Cons<E>(elt, this);
    }

    @Override
    public E first() {
        return elt;
    }

    @Override
    public ImList<E> rest() {
        return rest;
    }
    
}
