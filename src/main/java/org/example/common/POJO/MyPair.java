package org.example.common.POJO;

public class MyPair<K,V>
{
    public volatile K key;
    public volatile V value;
    public MyPair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
    public K getKey() { return key; }
    public V getValue() { return value; }

    @Override
    public String toString()
    {
        return key.toString() + " " + value.toString();
    }
}
