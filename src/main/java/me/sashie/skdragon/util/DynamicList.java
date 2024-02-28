package me.sashie.skdragon.util;

import java.util.*;

public class DynamicList<E> extends AbstractList<E>
		implements List<E>, RandomAccess, Cloneable {

	private static final int DEFAULT_CAPACITY = 10;
	private Object[] elements;
	private int size = 0;
	private int currentIndex = 0;

	public DynamicList() {
		this(DEFAULT_CAPACITY);
	}

	public DynamicList(int initialCapacity) {
		elements = new Object[initialCapacity];
	}

	public DynamicList(E... e) {
		elements = new Object[e.length];
		addAll(e);
	}

	public E get() {
		return get(currentIndex);
	}

	@Override
	public E get(int index) {
		checkIndex(index);
		return (E) elements[index];
	}

	public E getNext() {
		next();
		return get(currentIndex);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(E element) {
		ensureCapacity(size + 1);
		elements[size++] = element;
		return true;
	}

	@Override
	public void add(int index, E element) {
		checkIndexForAdd(index);
		ensureCapacity(size + 1);
		System.arraycopy(elements, index, elements, index + 1, size - index);
		elements[index] = element;
		size++;
	}

	public void addAll(E... elements) {
		ensureCapacity(size + elements.length);
		System.arraycopy(elements, 0, this.elements, size, elements.length);
		size += elements.length;
	}

	@Override
	public E set(int index, E element) {
		checkIndex(index);
		elements[index] = element;
		return element;
	}

	public E remove() {
		return remove(currentIndex);
	}

	@Override
	public E remove(int index) {
		checkIndex(index);
		E removedElement = get(index);
		if (index != size - 1) {
			System.arraycopy(elements, index + 1, elements, index, size - index - 1);
		}
		elements[--size] = null; // Help with GC
		return removedElement;
	}

	public E next() {
		currentIndex += 1;
		if (currentIndex >= size())
			currentIndex = 0;
		return get(currentIndex);
	}

	public E prev() {
		currentIndex -= 1;
		if (currentIndex <= 0)
			currentIndex = size();
		return get(currentIndex);
	}

	private void ensureCapacity(int minCapacity) {
		if (minCapacity > elements.length) {
			int newCapacity = Math.max(elements.length * 2, minCapacity);
			elements = Arrays.copyOf(elements, newCapacity);
		}
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	private void checkIndexForAdd(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}
}
