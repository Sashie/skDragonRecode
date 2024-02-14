package me.sashie.skdragon.project.util;

import java.util.Arrays;

public class DynamicArray<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private Object[] array;
	private int size;

	public DynamicArray() {
		this.array = new Object[DEFAULT_CAPACITY];
		this.size = 0;
	}

	public DynamicArray(int initialCapacity) {
		this.array = new Object[Math.max(DEFAULT_CAPACITY, initialCapacity)];
		this.size = 0;
	}

	public int size() {
		return size;
	}

	public void add(T element) {
		ensureCapacity();
		array[size++] = element;
	}

	public void remove(T element) {
		int indexToRemove = -1;

		for (int i = 0; i < size; i++) {
			if (array[i] != null && array[i].equals(element)) {
				indexToRemove = i;
				break;
			}
		}

		if (indexToRemove != -1) {
			removeAtIndex(indexToRemove);
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return (T) array[index];
	}

	private void removeAtIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}

		System.arraycopy(array, index + 1, array, index, size - index - 1);
		array[--size] = null;
	}

	private void ensureCapacity() {
		if (size == array.length) {
			array = Arrays.copyOf(array, array.length * 2);
		}
	}

	}