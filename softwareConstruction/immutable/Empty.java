public class Empty<E> implements ImList<E>{
    
    Empty() {}

    @Override
    public ImList<E> cons(E elt) {
        return new Cons<E>(elt, this);
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImList<E> rest() {
        throw new UnsupportedOperationException();
    }
}

    
