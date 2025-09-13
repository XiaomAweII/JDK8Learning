/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.util;

/**
 * 这个类提供了{@link List}接口的骨架实现，用于最小化实现基于"随机访问"数据存储（如数组）的
 * 此接口所需的工作。对于顺序访问数据（如链表），应优先使用{@link AbstractSequentialList}而不是此类。
 *
 * <p>要实现一个不可修改的列表，程序员只需要扩展此类并为{@link #get(int)}和
 * {@link List#size() size()}方法提供实现。
 *
 * <p>要实现一个可修改的列表，程序员必须额外重写{@link #set(int, Object) set(int, E)}方法
 * （否则会抛出{@code UnsupportedOperationException}）。如果列表大小可变，程序员还必须额外
 * 重写{@link #add(int, Object) add(int, E)}和{@link #remove(int)}方法。
 *
 * <p>根据{@link Collection}接口规范的建议，程序员通常应该提供一个无参数构造函数和一个集合
 * 构造函数。
 *
 * <p>与其他抽象集合实现不同，程序员<i>不</i>必须提供迭代器实现；迭代器和列表迭代器是由此类
 * 实现的，基于以下"随机访问"方法：
 * {@link #get(int)},
 * {@link #set(int, Object) set(int, E)},
 * {@link #add(int, Object) add(int, E)}和
 * {@link #remove(int)}。
 *
 * <p>此类中每个非抽象方法的文档都详细描述了其实现。如果所实现的集合允许更高效的实现，则可以
 * 重写这些方法中的每一个。
 *
 * <p>此类是
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java集合框架</a>的成员。
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @since 1.2
 */

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    /**
     * 唯一的构造函数。（供子类构造函数调用，通常是隐式的。）
     */
    protected AbstractList() {
    }

    /**
     * 将指定的元素追加到此列表的末尾（可选操作）。
     *
     * <p>支持此操作的列表可能会对可添加到此列表的元素施加限制。特别是，某些
     * 列表将拒绝添加null元素，而其他列表将对可添加的元素类型施加限制。列表
     * 类应在其文档中明确说明对可添加元素的任何限制。
     *
     * <p>此实现调用{@code add(size(), e)}。
     *
     * <p>请注意，除非重写了{@link #add(int, Object) add(int, E)}，否则此实现
     * 将抛出{@code UnsupportedOperationException}。
     *
     * @param e 要追加到此列表的元素
     * @return {@code true}（由{@link Collection#add}指定）
     * @throws UnsupportedOperationException 如果此列表不支持{@code add}操作
     * @throws ClassCastException 如果指定元素的类阻止其添加到此列表
     * @throws NullPointerException 如果指定的元素为null且此列表不允许null元素
     * @throws IllegalArgumentException 如果此元素的某些属性阻止其添加到此列表
     */
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    abstract public E get(int index);

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }


    // Search Operations

    /**
     * {@inheritDoc}
     *
     * <p>This implementation first gets a list iterator (with
     * {@code listIterator()}).  Then, it iterates over the list until the
     * specified element is found or the end of the list is reached.
     *
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public int indexOf(Object o) {
        ListIterator<E> it = listIterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return it.previousIndex();
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return it.previousIndex();
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation first gets a list iterator that points to the end
     * of the list (with {@code listIterator(size())}).  Then, it iterates
     * backwards over the list until the specified element is found, or the
     * beginning of the list is reached.
     *
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        if (o==null) {
            while (it.hasPrevious())
                if (it.previous()==null)
                    return it.nextIndex();
        } else {
            while (it.hasPrevious())
                if (o.equals(it.previous()))
                    return it.nextIndex();
        }
        return -1;
    }


    // Bulk Operations

    /**
     * Removes all of the elements from this list (optional operation).
     * 此调用返回后列表将为空。
     *
     * <p>此实现调用 {@code removeRange(0, size())}。
     *
     * <p>注意，除非重写了 {@code remove(int
     * index)} 或 {@code removeRange(int fromIndex, int toIndex)}，
     * 否则此实现将抛出 {@code UnsupportedOperationException}。
     *
     * @throws UnsupportedOperationException 如果此列表不支持 {@code clear} 操作
     */
    public void clear() {
        removeRange(0, size());
    }

    /**
     * {@inheritDoc}
     *
     * <p>此实现获取指定集合的迭代器并对其进行迭代，使用 {@code add(int, E)} 
     * 将从迭代器获得的元素一次一个地插入到此列表的适当位置。
     * 许多实现会重写此方法以提高效率。
     *
     * <p>注意，除非重写了 {@link #add(int, Object) add(int, E)}，
     * 否则此实现将抛出 {@code UnsupportedOperationException}。
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }


    // Iterators

    /**
     * 返回此列表中按适当顺序排列的元素的迭代器。
     *
     * <p>此实现返回迭代器接口的直接实现，依赖于底层列表的 {@code size()}、
     * {@code get(int)} 和 {@code remove(int)} 方法。
     *
     * <p>注意，除非重写了列表的 {@code remove(int)} 方法，
     * 否则此方法返回的迭代器将在其 {@code remove} 方法中抛出
     * {@link UnsupportedOperationException}。
     *
     * <p>此实现在面对并发修改时可能抛出运行时异常，
     * 如（受保护的）{@link #modCount} 字段的规范中所述。
     *
     * @return 此列表中按适当顺序排列的元素的迭代器
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * {@inheritDoc}
     *
     * <p>此实现返回 {@code listIterator(0)}。
     *
     * @see #listIterator(int)
     */
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * {@inheritDoc}
     *
     * <p>此实现返回 {@code ListIterator} 接口的直接实现，该实现扩展了
     * {@code iterator()} 方法返回的 {@code Iterator} 接口的实现。
     * {@code ListIterator} 实现依赖于底层列表的 {@code get(int)}、
     * {@code set(int, E)}、{@code add(int, E)} 和 {@code remove(int)} 方法。
     *
     * <p>注意，除非重写了列表的 {@code remove(int)}、{@code set(int, E)} 和
     * {@code add(int, E)} 方法，否则此实现返回的列表迭代器将在其
     * {@code remove}、{@code set} 和 {@code add} 方法中抛出
     * {@link UnsupportedOperationException}。
     *
     * <p>此实现在面对并发修改时可能抛出运行时异常，
     * 如（受保护的）{@link #modCount} 字段的规范中所述。
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public ListIterator<E> listIterator(final int index) {
        rangeCheckForAdd(index);

        return new ListItr(index);
    }

    private class Itr implements Iterator<E> {
        /**
         * 下一次调用 next 时要返回的元素的索引。
         */
        int cursor = 0;

        /**
         * 最近一次调用 next 或 previous 返回的元素的索引。
         * 如果通过调用 remove 删除了该元素，则重置为 -1。
         */
        int lastRet = -1;

        /**
         * 迭代器认为底层 List 应该具有的 modCount 值。
         * 如果违反了这个期望值，则迭代器检测到并发修改。
         */
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {
            checkForComodification();
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public E previous() {
            checkForComodification();
            try {
                int i = cursor - 1;
                E previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                AbstractList.this.add(i, e);
                lastRet = -1;
                cursor = i + 1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>此实现返回一个继承 {@code AbstractList} 的列表。该子类在私有字段中存储
     * 子列表在底层列表中的偏移量、子列表的大小（可以在其生命周期内改变）以及
     * 底层列表的预期 {@code modCount} 值。子类有两个变体，其中一个实现了
     * {@code RandomAccess}。如果此列表实现了 {@code RandomAccess}，则返回的
     * 列表将是实现 {@code RandomAccess} 的子类的实例。
     *
     * <p>子类的 {@code set(int, E)}、{@code get(int)}、{@code add(int, E)}、
     * {@code remove(int)}、{@code addAll(int, Collection)} 和
     * {@code removeRange(int, int)} 方法在进行边界检查和调整偏移量后，
     * 都委托给底层抽象列表上的相应方法。{@code addAll(Collection c)} 方法
     * 仅返回 {@code addAll(size, c)}。
     *
     * <p>{@code listIterator(int)} 方法返回底层列表上列表迭代器的一个
     * "包装对象"，该迭代器是使用底层列表上的相应方法创建的。{@code iterator}
     * 方法仅返回 {@code listIterator()}，而 {@code size} 方法仅返回子类的
     * {@code size} 字段。
     *
     * <p>所有方法首先检查底层列表的实际 {@code modCount} 是否等于其预期值，
     * 如果不是，则抛出 {@code ConcurrentModificationException}。
     *
     * @throws IndexOutOfBoundsException 如果端点索引值超出范围
     *         {@code (fromIndex < 0 || toIndex > size)}
     * @throws IllegalArgumentException 如果端点索引顺序不正确
     *         {@code (fromIndex > toIndex)}
     */
    public List<E> subList(int fromIndex, int toIndex) {
        return (this instanceof RandomAccess ?
                new RandomAccessSubList<>(this, fromIndex, toIndex) :
                new SubList<>(this, fromIndex, toIndex));
    }

    // Comparison and hashing

    /**
     * Compares the specified object with this list for equality.  Returns
     * {@code true} if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements {@code e1} and
     * {@code e2} are <i>equal</i> if {@code (e1==null ? e2==null :
     * e1.equals(e2))}.)  In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.<p>
     *
     * This implementation first checks if the specified object is this
     * list. If so, it returns {@code true}; if not, it checks if the
     * specified object is a list. If not, it returns {@code false}; if so,
     * it iterates over both lists, comparing corresponding pairs of elements.
     * If any comparison returns {@code false}, this method returns
     * {@code false}.  If either iterator runs out of elements before the
     * other it returns {@code false} (as the lists are of unequal length);
     * otherwise it returns {@code true} when the iterations complete.
     *
     * @param o the object to be compared for equality with this list
     * @return {@code true} if the specified object is equal to this list
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    /**
     * Returns the hash code value for this list.
     *
     * <p>This implementation uses exactly the code that is used to define the
     * list hash function in the documentation for the {@link List#hashCode}
     * method.
     *
     * @return the hash code value for this list
     */
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     *
     * <p>This method is called by the {@code clear} operation on this list
     * and its subLists.  Overriding this method to take advantage of
     * the internals of the list implementation can <i>substantially</i>
     * improve the performance of the {@code clear} operation on this list
     * and its subLists.
     *
     * <p>This implementation gets a list iterator positioned before
     * {@code fromIndex}, and repeatedly calls {@code ListIterator.next}
     * followed by {@code ListIterator.remove} until the entire range has
     * been removed.  <b>Note: if {@code ListIterator.remove} requires linear
     * time, this implementation requires quadratic time.</b>
     *
     * @param fromIndex index of first element to be removed
     * @param toIndex index after last element to be removed
     */
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> it = listIterator(fromIndex);
        for (int i=0, n=toIndex-fromIndex; i<n; i++) {
            it.next();
            it.remove();
        }
    }

    /**
     * The number of times this list has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the
     * list, or otherwise perturb it in such a fashion that iterations in
     * progress may yield incorrect results.
     *
     * <p>This field is used by the iterator and list iterator implementation
     * returned by the {@code iterator} and {@code listIterator} methods.
     * If the value of this field changes unexpectedly, the iterator (or list
     * iterator) will throw a {@code ConcurrentModificationException} in
     * response to the {@code next}, {@code remove}, {@code previous},
     * {@code set} or {@code add} operations.  This provides
     * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
     * the face of concurrent modification during iteration.
     *
     * <p><b>Use of this field by subclasses is optional.</b> If a subclass
     * wishes to provide fail-fast iterators (and list iterators), then it
     * merely has to increment this field in its {@code add(int, E)} and
     * {@code remove(int)} methods (and any other methods that it overrides
     * that result in structural modifications to the list).  A single call to
     * {@code add(int, E)} or {@code remove(int)} must add no more than
     * one to this field, or the iterators (and list iterators) will throw
     * bogus {@code ConcurrentModificationExceptions}.  If an implementation
     * does not wish to provide fail-fast iterators, this field may be
     * ignored.
     */
    protected transient int modCount = 0;

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }
}

class SubList<E> extends AbstractList<E> {
    private final AbstractList<E> l;
    private final int offset;
    private int size;

    SubList(AbstractList<E> list, int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > list.size())
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
        l = list;
        offset = fromIndex;
        size = toIndex - fromIndex;
        this.modCount = l.modCount;
    }

    public E set(int index, E element) {
        rangeCheck(index);
        checkForComodification();
        return l.set(index+offset, element);
    }

    public E get(int index) {
        rangeCheck(index);
        checkForComodification();
        return l.get(index+offset);
    }

    public int size() {
        checkForComodification();
        return size;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        checkForComodification();
        l.add(index+offset, element);
        this.modCount = l.modCount;
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        checkForComodification();
        E result = l.remove(index+offset);
        this.modCount = l.modCount;
        size--;
        return result;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        checkForComodification();
        l.removeRange(fromIndex+offset, toIndex+offset);
        this.modCount = l.modCount;
        size -= (toIndex-fromIndex);
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        int cSize = c.size();
        if (cSize==0)
            return false;

        checkForComodification();
        l.addAll(offset+index, c);
        this.modCount = l.modCount;
        size += cSize;
        return true;
    }

    public Iterator<E> iterator() {
        return listIterator();
    }

    public ListIterator<E> listIterator(final int index) {
        checkForComodification();
        rangeCheckForAdd(index);

        return new ListIterator<E>() {
            private final ListIterator<E> i = l.listIterator(index+offset);

            public boolean hasNext() {
                return nextIndex() < size;
            }

            public E next() {
                if (hasNext())
                    return i.next();
                else
                    throw new NoSuchElementException();
            }

            public boolean hasPrevious() {
                return previousIndex() >= 0;
            }

            public E previous() {
                if (hasPrevious())
                    return i.previous();
                else
                    throw new NoSuchElementException();
            }

            public int nextIndex() {
                return i.nextIndex() - offset;
            }

            public int previousIndex() {
                return i.previousIndex() - offset;
            }

            public void remove() {
                i.remove();
                SubList.this.modCount = l.modCount;
                size--;
            }

            public void set(E e) {
                i.set(e);
            }

            public void add(E e) {
                i.add(e);
                SubList.this.modCount = l.modCount;
                size++;
            }
        };
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList<>(this, fromIndex, toIndex);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void checkForComodification() {
        if (this.modCount != l.modCount)
            throw new ConcurrentModificationException();
    }
}

class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
    RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
        super(list, fromIndex, toIndex);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList<>(this, fromIndex, toIndex);
    }
}
