/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <i>集合层次结构</i> 中的根接口.
 * 集合表示一组对象, 称之为 <i>元素</i>.
 * 一些集合允许重复元素，而另一些则不允许.
 * 一些集合是有序的，另一些是无序的.
 * JDK 不提供此接口的任何 <i>直接</i> 实现: JDK提供了更具体的子接口，如<tt>Set</tt> 和 <tt>List</tt>.
 * 这个接口通常用于在需要最大通用性的情况下传递和操作集合
 *
 * <p><i>Bags</i> 或 <i>multisets</i> (可能包含重复元素的无序集合) 应该直接实现此接口.
 *
 * <p>所有通用的 <tt>Collection</tt> 实现类 (通常通过其子接口之一简介实现<tt>Collection</tt>)
 * 都应该提供两个 "标准" 构造方法:
 * 一个 void (无参构造) , 用于创建一个空集合,
 * 以及一个带有<tt>Collection</tt>类型单个参数的构造函数, 用于创建一个与其参数具有相同元素的新集合
 * 事实上, 后一个构造函数允许用户赋值任何集合, 从而生成所需实现类型的等效集合.
 * 由于接口不能包含构造函数，所以无法强制执行这个约定，但是Java平台库中所有通用的<tt>Collection</tt>
     * 实现类都遵守这个约定。
     *
     * <p>此接口中包含的"破坏性"方法（即会修改其所操作的集合的方法）在集合不支持该操作时
     * 会抛出<tt>UnsupportedOperationException</tt>异常。在这种情况下，如果方法调用
     * 不会对集合产生任何影响，这些方法可以（但不是必须）抛出<tt>UnsupportedOperationException</tt>异常。
     * 例如，在不可修改的集合上调用{@link #addAll(Collection)}方法时，如果要添加的集合为空，
     * 可以（但不是必须）抛出异常。
 *
 * <p><a name="optional-restrictions">
     * 某些集合实现对其可以包含的元素有限制。</a>例如，某些实现禁止null元素，
     * 某些实现对其元素类型有限制。试图添加不合格的元素会抛出未检查异常，通常是
     * <tt>NullPointerException</tt>或<tt>ClassCastException</tt>。试图查询
     * 不合格元素是否存在可能会抛出异常，也可能仅返回false；一些实现会表现出前者的
     * 行为，而另一些则表现出后者的行为。更一般地说，对不合格元素进行操作时，如果
     * 操作完成不会导致将不合格元素插入到集合中，那么根据实现的选择，可能会抛出异常，
     * 也可能会成功执行。这样的异常在此接口的规范中被标记为"可选的"。
 *
 * <p>每个集合都要自行决定其同步策略。如果实现没有提供更强的保证，
     * 当一个集合正在被另一个线程修改时，调用该集合的任何方法都可能导致未定义的行为；
     * 这包括直接调用、将集合传递给可能执行调用的方法，以及使用现有迭代器来检查集合。
 *
 * <p>集合框架接口中的许多方法都是基于{@link Object#equals(Object) equals}方法定义的。
     * 例如，{@link #contains(Object) contains(Object o)}方法的规范说："当且仅当此集合
     * 包含至少一个元素<tt>e</tt>满足<tt>(o==null ? e==null : o.equals(e))</tt>时返回
     * <tt>true</tt>"。这个规范<i>不</i>应被理解为使用非空参数<tt>o</tt>调用
     * <tt>Collection.contains</tt>时会对任何元素<tt>e</tt>调用<tt>o.equals(e)</tt>。
     * 实现类可以自由实现优化，以避免调用<tt>equals</tt>，例如，通过首先比较两个元素的哈希码。
     * （{@link Object#hashCode()}规范保证具有不相等哈希码的两个对象不能相等。）更一般地说，
     * 各种集合框架接口的实现可以在实现者认为适当的情况下自由利用底层{@link Object}方法的指定行为。
 *
 * <p>某些执行集合递归遍历的操作可能会在自引用实例（集合直接或间接包含自身）的情况下
     * 抛出异常。这包括{@code clone()}、{@code equals()}、{@code hashCode()}和
     * {@code toString()}方法。实现类可以选择处理自引用场景，但是目前大多数实现并不这样做。
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implSpec
     * 默认方法实现（继承的或其他方式的）不应用任何同步协议。如果{@code Collection}实现
     * 有特定的同步协议，那么它必须重写默认实现以应用该协议。
 *
 * @param <E> the type of elements in this collection
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Set
 * @see     List
 * @see     Map
 * @see     SortedSet
 * @see     SortedMap
 * @see     HashSet
 * @see     TreeSet
 * @see     ArrayList
 * @see     LinkedList
 * @see     Vector
 * @see     Collections
 * @see     Arrays
 * @see     AbstractCollection
 * @since 1.2
 */
// 容器类的顶层接口
public interface Collection<E> extends Iterable<E> {
    // Query Operations

    /**
     * 返回当前容器的元素个数.
     * 如果条件超过<tt>Integer.MAX_VALUE</tt>(最大值), 那就返回 returns <tt>Integer.MAX_VALUE</tt>.
     *
     * @return 当前容器当中的元素个数
     */
    // 返回当前容器的元素个数
    int size();

    /**
     * 如果容器内不包含任何元素返回true
     *
     * @return <tt>true</tt> if this collection contains no elements
     */
    // 判断当前容器是否为空
    boolean isEmpty();

    /**
     * 如果此集合包含指定元素，则返回<tt>true</tt>。
     * 更正式地说，当且仅当此集合包含至少一个元素<tt>e</tt>，使得
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>时，返回<tt>true</tt>。
     *
     * @param o 要测试其在此集合中是否存在的元素
     * @return <tt>true</tt> 如果此集合包含指定的元素
     * @throws ClassCastException 如果指定元素的类型与此集合不兼容
     *                           (<a href="#optional-restrictions">可选</a>)
     * @throws NullPointerException 如果指定的元素为null且此集合不允许null元素
     *                              (<a href="#optional-restrictions">可选</a>)
     */
    // 判断当前容器中是否包含元素o
    boolean contains(Object o);

    /**
     * 返回此集合中元素的迭代器。对于返回元素的顺序不作任何保证
     * （除非此集合是提供保证的某个类的实例）。
     *
     * @return 此集合中元素的<tt>Iterator</tt>
     */
    // 返回当前容器的迭代器
    Iterator<E> iterator();

    /**
     * 返回一个包含此集合中所有元素的数组。
     * 如果此集合对其迭代器返回元素的顺序有任何保证，
     * 则此方法必须以相同的顺序返回这些元素。
     *
     * <p>返回的数组将是"安全的"，因为此集合不会保留对它的任何引用。
     * （换句话说，即使此集合是由数组支持的，此方法也必须分配一个新数组）。
     * 因此调用者可以自由修改返回的数组。
     *
     * <p>此方法充当基于数组和基于集合的API之间的桥梁。
     *
     * @return 一个包含当前容器所有元素的数组
     */
    // 以数组形式返回当前容器中的元素
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this collection;
     * the runtime type of the returned array is that of the specified array.
     * If the collection fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this collection.
     *
     * <p>If this collection fits in the specified array with room to spare
     * (i.e., the array has more elements than this collection), the element
     * in the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * collection <i>only</i> if the caller knows that this collection does
     * not contain any <tt>null</tt> elements.)
     *
     * <p>If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>假设<tt>x</tt>是一个已知只包含字符串的集合。
     * 以下代码可用于将集合转储到新分配的<tt>String</tt>数组中：
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * <p>
     * 注意，<tt>toArray(new Object[0])</tt>在功能上与<tt>toArray()</tt>相同。
     *
     * @param <T> 用于存放集合的数组的运行时类型
     * @param a   用于存储此集合元素的数组，如果它足够大；否则，将分配一个相同运行时
     *            类型的新数组用于此目的。
     * @return 包含此集合所有元素的数组
     * @throws ArrayStoreException 如果指定数组的运行时类型不是此集合中每个元素的
     *                            运行时类型的超类型
     * @throws NullPointerException 如果指定的数组为null
     */
    // 将当前容器中的元素存入数组a后返回，需要将容器中的元素转为T类型
    <T> T[] toArray(T[] a);

    // Modification Operations

    /**
     * 确保此集合包含指定的元素（可选操作）。如果此集合因调用而改变，则返回<tt>true</tt>。
     * （如果此集合不允许重复且已经包含指定的元素，则返回<tt>false</tt>）。<p>
     * <p>
     * 支持此操作的集合可能会对可以添加到此集合的元素施加限制。特别是，某些
     * 集合将拒绝添加<tt>null</tt>元素，而其他集合将对可以添加的元素类型
     * 施加限制。集合类应该在其文档中明确说明对可添加元素的任何限制。<p>
     * <p>
     * 如果集合因为任何原因（除了已经包含该元素之外）拒绝添加特定元素，它
     * <i>必须</i>抛出异常（而不是返回<tt>false</tt>）。这保持了集合在此调用
     * 返回后始终包含指定元素的不变性。
     *
     * @param e 要确保在此集合中存在的元素
     * @return <tt>true</tt> 如果此集合因调用而改变
     * @throws UnsupportedOperationException 如果此集合不支持<tt>add</tt>操作
     * @throws ClassCastException 如果指定元素的类阻止其被添加到此集合
     * @throws NullPointerException 如果指定的元素为null且此集合不允许null元素
     * @throws IllegalArgumentException 如果元素的某些属性阻止其被添加到此集合
     * @throws IllegalStateException 如果由于插入限制，此时无法添加元素
     */
    // 向当前容器中添加元素e
    boolean add(E e);

    /**
     * 从此集合中移除一个指定元素的实例，如果该元素存在的话（可选操作）。
     * 更正式地说，如果此集合包含一个或多个这样的元素，则移除一个元素<tt>e</tt>，
     * 使得<tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>。
     * 如果此集合包含指定的元素，则返回<tt>true</tt>（或者等价地说，
     * 如果此集合因调用而改变，则返回<tt>true</tt>）。
     *
     * @param o 要从此集合中移除的元素（如果存在）
     * @return <tt>true</tt> 如果一个元素因此调用而被移除
     * @throws ClassCastException 如果指定元素的类型与此集合不兼容
     *                           (<a href="#optional-restrictions">可选</a>)
     * @throws NullPointerException 如果指定的元素为null且此集合不允许null元素
     *                              (<a href="#optional-restrictions">可选</a>)
     * @throws UnsupportedOperationException 如果此集合不支持<tt>remove</tt>操作
     */
    // 移除指定元素o，并返回是否移除成功
    boolean remove(Object o);


    // Bulk Operations

    /**
     * 如果此集合包含指定集合中的所有元素，则返回<tt>true</tt>。
     *
     * @param c 要检查是否包含在此集合中的集合
     * @return <tt>true</tt> 如果此集合包含指定集合中的所有元素
     * @throws ClassCastException 如果指定集合中的一个或多个元素的类型与此集合不兼容
     *                           (<a href="#optional-restrictions">可选</a>)
     * @throws NullPointerException 如果指定的集合包含一个或多个null元素且此集合不允许
     *                              null元素(<a href="#optional-restrictions">可选</a>)，
     *                              或者如果指定的集合为null
     * @see #contains(Object)
     */
    // 判断指定容器中的元素是否都包含在当前容器中
    boolean containsAll(Collection<?> c);

    /**
     * 将指定集合中的所有元素添加到此集合中（可选操作）。如果在操作进行时修改了
     * 指定的集合，则此操作的行为是未定义的。（这意味着如果指定的集合就是此集合，
     * 并且此集合非空，则此调用的行为是未定义的。）
     *
     * @param c 包含要添加到此集合的元素的集合
     * @return <tt>true</tt> 如果此集合因调用而改变
     * @throws UnsupportedOperationException 如果此集合不支持<tt>addAll</tt>操作
     * @throws ClassCastException 如果指定集合中某个元素的类阻止其被添加到此集合
     * @throws NullPointerException 如果指定的集合包含null元素且此集合不允许null元素，
     *                              或者如果指定的集合为null
     * @throws IllegalArgumentException 如果指定集合中某个元素的某些属性阻止其被添加到
     *                                  此集合
     * @throws IllegalStateException 如果由于插入限制，此时无法添加所有元素
     * @see #add(Object)
     */
    // 将指定容器中的所有元素添加到当前容器中
    boolean addAll(Collection<? extends E> c);

    /**
     * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).  After this call returns,
     * this collection will contain no elements in common with the specified
     * collection.
     *
     * @param c collection containing elements to be removed from this collection
     * @return <tt>true</tt> if this collection changed as a result of the
     * call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     *                                       is not supported by this collection
     * @throws ClassCastException 如果此集合中的一个或多个元素与指定集合不兼容
      *                           (<a href="#optional-restrictions">可选</a>)
      * @throws NullPointerException 如果此集合包含一个或多个null元素且指定集合不支持
      *                              null元素(<a href="#optional-restrictions">可选</a>)，
      *                              或者如果指定的集合为null
      * @see #remove(Object)
      * @see #contains(Object)
     */
    // (匹配则移除)移除当前容器中所有与给定容器中的元素匹配的元素
    boolean removeAll(Collection<?> c);

    /**
     * 移除此集合中满足给定断言的所有元素。在迭代过程中或由断言引发的错误或运行时异常
     * 将传递给调用者。
     *
     * @param filter 一个断言，对于要移除的元素返回{@code true}
     * @return {@code true} 如果有任何元素被移除
     * @throws NullPointerException 如果指定的过滤器为null
     * @throws UnsupportedOperationException 如果无法从此集合中移除元素。
     *                                      如果匹配的元素无法被移除，或者总的来说，
     *                                      不支持移除操作，实现类可能会抛出此异常。
     * @implSpec 默认实现使用{@link #iterator}遍历集合的所有元素。每个匹配的元素都使用
     * {@link Iterator#remove()}移除。如果集合的迭代器不支持移除操作，则在第一个匹配的
     * 元素上将抛出{@code UnsupportedOperationException}。
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * 仅保留此集合中包含在指定集合中的元素（可选操作）。换句话说，
     * 移除此集合中所有未包含在指定集合中的元素。
     *
     * @param c 包含要在此集合中保留的元素的集合
     * @return <tt>true</tt> 如果此集合因调用而改变
     * @throws UnsupportedOperationException 如果此集合不支持<tt>retainAll</tt>操作
     * @throws ClassCastException            if the types of one or more elements
     *                                       in this collection are incompatible with the specified
     *                                       collection
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this collection contains one or more
     *                                       null elements and the specified collection does not permit null
     *                                       elements
     *                                       (<a href="#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    // (匹配则保留) 保留当前容器中所有与给定容器中的元素匹配的元素
    boolean retainAll(Collection<?> c);

    /**
     * Removes all of the elements from this collection (optional operation).
     * 此方法返回后，集合将为空。
     *
     * @throws UnsupportedOperationException 如果此集合不支持<tt>clear</tt>操作
     */
    // 清空当前容器中的所有元素
    void clear();


    // Comparison and hashing

    /**
     * 比较指定对象与此集合是否相等。<p>
     * <p>
     * 虽然<tt>Collection</tt>接口没有对<tt>Object.equals</tt>的一般约定添加任何规定，
     * 但是"直接"实现<tt>Collection</tt>接口的程序员（换句话说，创建一个是<tt>Collection</tt>
     * 但不是<tt>Set</tt>或<tt>List</tt>的类）如果选择重写<tt>Object.equals</tt>，必须
     * 谨慎行事。这样做并非必要，最简单的做法是依赖<tt>Object</tt>的实现，但是实现者可能
     * 希望实现"值比较"来替代默认的"引用比较"。（<tt>List</tt>和<tt>Set</tt>接口要求进行
     * 这样的值比较。）<p>
     * <p>
     * <tt>Object.equals</tt>方法的一般约定声明equals必须是对称的（换句话说，当且仅当
     * <tt>b.equals(a)</tt>时，<tt>a.equals(b)</tt>成立）。<tt>List.equals</tt>和
     * <tt>Set.equals</tt>的约定声明列表只能等于其他列表，集合只能等于其他集合。因此，
     * 对于既不实现<tt>List</tt>也不实现<tt>Set</tt>接口的集合类，其自定义的<tt>equals</tt>
     * 方法在将此集合与任何列表或集合进行比较时必须返回<tt>false</tt>。（根据同样的逻辑，
     * 不可能编写一个同时正确实现<tt>Set</tt>和<tt>List</tt>接口的类。）
     *
     * @param o object to be compared for equality with this collection
     * @return <tt>true</tt> if the specified object is equal to this
     * collection
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     * @see List#equals(Object)
     */
    boolean equals(Object o);

    /**
     * 返回此集合的哈希码值。虽然<tt>Collection</tt>接口没有对<tt>Object.hashCode</tt>
     * 方法的一般约定添加任何规定，但程序员应该注意，任何重写<tt>Object.equals</tt>方法的
     * 类也必须重写<tt>Object.hashCode</tt>方法，以满足<tt>Object.hashCode</tt>方法的
     * 一般约定。特别是，<tt>c1.equals(c2)</tt>意味着<tt>c1.hashCode()==c2.hashCode()</tt>。
     *
     * @return the hash code value for this collection
     * @see Object#hashCode()
     * @see Object#equals(Object)
     */
    int hashCode();

    /**
     * 在此集合的元素上创建一个{@link Spliterator}。
     * <p>
     * 实现类应该记录spliterator报告的特征值。如果spliterator报告了{@link Spliterator#SIZED}
     * 并且此集合不包含任何元素，则不需要报告这些特征值。
     *
     * <p>可以返回更高效spliterator的子类应该重写默认实现。为了保持{@link #stream()}和
     * {@link #parallelStream()}}方法的预期惰性行为，spliterator应该具有{@code IMMUTABLE}
     * 或{@code CONCURRENT}特征，或者是<em><a href="Spliterator.html#binding">延迟绑定</a></em>的。
     * 如果这些都不可行，重写类应该描述spliterator的绑定和结构干扰的文档策略，
     * 并且应该重写{@link #stream()}和{@link #parallelStream()}方法，使用spliterator的
     * {@code Supplier}来创建流，如下所示：
     * <pre>{@code
     *     Stream<E> s = StreamSupport.stream(() -> spliterator(), spliteratorCharacteristics)
     * }</pre>
     * <p>这些要求确保由{@link #stream()}和{@link #parallelStream()}方法产生的流
     * 将反映终端流操作启动时集合的内容。
     *
     * @return a {@code Spliterator} over the elements in this collection
     * @implSpec The default implementation creates a
     * <em><a href="Spliterator.html#binding">late-binding</a></em> spliterator
     * from the collections's {@code Iterator}.  The spliterator inherits the
     * <em>fail-fast</em> properties of the collection's iterator.
     * <p>
     * The created {@code Spliterator} reports {@link Spliterator#SIZED}.
     * @implNote The created {@code Spliterator} additionally reports
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>如果spliterator不覆盖任何元素，那么报告除{@code SIZED}和{@code SUBSIZED}之外的
      * 额外特征值，并不能帮助客户端控制、专门化或简化计算。然而，这确实使得空集合可以
      * 共享使用一个不可变的空spliterator实例（参见{@link Spliterators#emptySpliterator()}），
      * 并使客户端能够确定这样的spliterator是否不覆盖任何元素。
     * @since 1.8
     */
    // 返回描述此容器中元素的Spliterator
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * 返回以此集合为源的顺序{@code Stream}。
     *
     * <p>当{@link #spliterator()}方法无法返回{@code IMMUTABLE}、
     * {@code CONCURRENT}或<em>延迟绑定</em>的spliterator时，应该重写此方法。
     * （详见{@link #spliterator()}）
     *
     * @return 此集合中元素的顺序{@code Stream}
     * @implSpec 默认实现从集合的{@code Spliterator}创建一个顺序{@code Stream}。
     * @since 1.8
     */
    // 获取当前容器的顺序数据流
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 返回以此集合为源的可能并行的{@code Stream}。此方法允许返回顺序流。
     *
     * <p>当{@link #spliterator()}方法无法返回{@code IMMUTABLE}、
     * {@code CONCURRENT}或<em>延迟绑定</em>的spliterator时，应该重写此方法。
     * （详见{@link #spliterator()}）
     *
     * @return 此集合中元素的可能并行的{@code Stream}
     * @implSpec 默认实现从集合的{@code Spliterator}创建一个并行{@code Stream}。
     * @since 1.8
     */
    // 获取当前容器的并行数据流
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
