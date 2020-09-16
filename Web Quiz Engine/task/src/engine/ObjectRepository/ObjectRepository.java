package engine.ObjectRepository;

public interface  ObjectRepository<T> {
    public void store(T t);

    public T retrieve(int id);

    public T remove(int id);
}
