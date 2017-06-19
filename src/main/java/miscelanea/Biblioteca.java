package miscelanea;

public class Biblioteca<T>
{
    private Object[] elements;
    private int amount;
    private int maximum;

    public Biblioteca(int numberElements)
    {
        elements = new Object[numberElements];
        maximum = numberElements;
        amount = 0;
    }

    public void add(T element)
    {
        if (amount < maximum)
        {
            elements[amount] = element;
            amount++;
        }
    }

    @SuppressWarnings("unchecked")
	public T query(int index)
    {
        if (index < maximum)
            return (T)elements[index];
        else
            return null;
    }
}
