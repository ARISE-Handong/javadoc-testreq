/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4;

import static org.apache.commons.collections4.functors.EqualPredicate.equalPredicate;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.commons.collections4.iterators.EmptyIterator;
import org.apache.commons.collections4.iterators.EmptyListIterator;
import org.apache.commons.collections4.iterators.EmptyMapIterator;
import org.apache.commons.collections4.iterators.EmptyOrderedIterator;
import org.apache.commons.collections4.iterators.EmptyOrderedMapIterator;
import org.apache.commons.collections4.iterators.EnumerationIterator;
import org.apache.commons.collections4.iterators.NodeListIterator;
import org.apache.commons.collections4.iterators.ObjectArrayIterator;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Tests for IteratorUtils.
 */
public class IteratorUtilsTest {

    /**
     * Collection of {@link Integer}s
     */
    private List<Integer> collectionA = null;

    /**
     * Collection of even {@link Integer}s
     */
    private List<Integer> collectionEven = null;

    /**
     * Collection of odd {@link Integer}s
     */
    private List<Integer> collectionOdd = null;

    private final Collection<Integer> emptyCollection = new ArrayList<>(1);

    private Iterable<Integer> iterableA = null;

    /**
     * Creates a NodeList containing the specified nodes.
     */
    private NodeList createNodeList(final Node[] nodes) {
        return new NodeList() {
            @Override
            public int getLength() {
                return nodes.length;
            }
            @Override
            public Node item(final int index) {
                return nodes[index];
            }
        };
    }

    /**
     * creates an array of four Node instances, mocked by EasyMock.
     */
    private Node[] createNodes() {
        final Node node1 = createMock(Node.class);
        final Node node2 = createMock(Node.class);
        final Node node3 = createMock(Node.class);
        final Node node4 = createMock(Node.class);
        replay(node1);
        replay(node2);
        replay(node3);
        replay(node4);

        return new Node[]{node1, node2, node3, node4};
}

    /**
     * Gets an immutable Iterator operating on the elements ["a", "b", "c", "d"].
     */
    private Iterator<String> getImmutableIterator() {
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        return IteratorUtils.unmodifiableIterator(list.iterator());
    }

    /**
     * Gets an immutable ListIterator operating on the elements ["a", "b", "c", "d"].
     */
    private ListIterator<String> getImmutableListIterator() {
        final List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        return IteratorUtils.unmodifiableListIterator(list.listIterator());
    }

    @Before
    public void setUp() {
        collectionA = new ArrayList<>();
        collectionA.add(1);
        collectionA.add(2);
        collectionA.add(2);
        collectionA.add(3);
        collectionA.add(3);
        collectionA.add(3);
        collectionA.add(4);
        collectionA.add(4);
        collectionA.add(4);
        collectionA.add(4);

        iterableA = collectionA;

        collectionEven = Arrays.asList(2, 4, 6, 8, 10, 12);
        collectionOdd = Arrays.asList(1, 3, 5, 7, 9, 11);
    }

    @Test
    public void testArrayIterator() {
        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray);
        assertTrue(iterator.next().equals("a"));

        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray);
        assertTrue(iterator.next().equals("b"));

        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray);
        iterator.reset();
        assertTrue(iterator.next().equals("a"));

        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(Integer.valueOf(0));
            fail("Expecting IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {
                // expected
        }
		assert();


        final Object[] objArray = {"a", "b", "c"};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator((Object[]) null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
                // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray);
        iterator = IteratorUtils.arrayIterator(objArray, 1);
        assertTrue(iterator.next().equals("b"));

        final Object[] objArray = {"a", "b", "c"};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray, -1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();


        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray);
        iterator = IteratorUtils.arrayIterator(objArray, 3);
        assertTrue(!iterator.hasNext());

        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray);
        iterator.reset();
        try {
            iterator = IteratorUtils.arrayIterator(objArray, 4);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();


        final Object[] objArray = {"a", "b", "c"};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray, 2, 3);
        assertTrue(iterator.next().equals("c"));

        final Object[] objArray = {"a", "b", "c"};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray, 2, 4);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c"};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray, -1, 1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c"};
        try {
	        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(objArray, 2, 1);
            fail("Expecting IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray);
        assertTrue(iterator.next().equals(Integer.valueOf(0)));

        final int[] intArray = {0, 1, 2};
	    ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray);
        assertTrue(iterator.next().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray);
        iterator.reset();
        assertTrue(iterator.next().equals(Integer.valueOf(0)));

        final int[] intArray = {0, 1, 2};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, 1);
        assertTrue(iterator.next().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, -1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, 3);
        assertTrue(!iterator.hasNext());

        final int[] intArray = {0, 1, 2};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, 3);
        iterator.reset();
        try {
            iterator = IteratorUtils.arrayIterator(intArray, 4);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();


        final int[] intArray = {0, 1, 2};
        ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, 2, 3);
        assertTrue(iterator.next().equals(Integer.valueOf(2)));

        final int[] intArray = {0, 1, 2};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, 2, 4);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, -1, 1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        try {
            ResettableIterator<Object> iterator = IteratorUtils.arrayIterator(intArray, 2, 1);
            fail("Expecting IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {
            // expected
        }
		assert();
    }

    @Test
    public void testArrayListIterator() {
        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(!iterator.hasPrevious());

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.previousIndex() == -1);

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.nextIndex() == 0);

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.next().equals("a"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.previous().equals("a"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.next().equals("a"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.previousIndex() == 0);

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.nextIndex() == 1);

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.next().equals("b"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.next().equals("c"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.next().equals("d"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.nextIndex() == 4); // size of list

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray);
        assertTrue(iterator.previousIndex() == 3);

        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(Integer.valueOf(0));
            fail("Expecting IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {
                // expected
        }
		assert();		

        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator((Object[]) null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
                // expected
        }
		assert();		

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 1);
        assertTrue(iterator.previousIndex() == -1);

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 1);
        assertTrue(!iterator.hasPrevious());

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 1);
        assertTrue(iterator.nextIndex() == 0);

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 1);
        assertTrue(iterator.next().equals("b"));

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 1);
        assertTrue(iterator.previousIndex() == 0);

        final Object[] objArray = {"a", "b", "c", "d"};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, -1);
            fail("Expecting IndexOutOfBoundsException.");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 3);
        assertTrue(iterator.hasNext());

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 3);
        try {
            iterator.previous();
            fail("Expecting NoSuchElementException.");
        } catch (final NoSuchElementException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c", "d"};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 5);
            fail("Expecting IndexOutOfBoundsException.");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c", "d"};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 2, 3);
        assertTrue(iterator.next().equals("c"));

        final Object[] objArray = {"a", "b", "c", "d"};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 2, 5);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c", "d"};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, -1, 1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final Object[] objArray = {"a", "b", "c", "d"};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(objArray, 2, 1);
            fail("Expecting IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.previousIndex() == -1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(!iterator.hasPrevious());

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.nextIndex() == 0);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.next().equals(Integer.valueOf(0)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.previousIndex() == 0);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.nextIndex() == 1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.next().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.previousIndex() == 1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.nextIndex() == 2);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.previous().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray);
        assertTrue(iterator.next().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.previousIndex() == -1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(!iterator.hasPrevious());

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.nextIndex() == 0);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.next().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.previous().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.next().equals(Integer.valueOf(1)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.previousIndex() == 0);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.nextIndex() == 1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.next().equals(Integer.valueOf(2)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.previousIndex() == 1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.nextIndex() == 2);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.previous().equals(Integer.valueOf(2)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.previousIndex() == 0);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 1);
        assertTrue(iterator.nextIndex() == 1);


        final int[] intArray = {0, 1, 2};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, -1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 3);
        assertTrue(!iterator.hasNext());

        final int[] intArray = {0, 1, 2};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 4);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 3);
        assertTrue(!iterator.hasPrevious());

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 3);
        assertTrue(iterator.previousIndex() == -1);

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 3);
        assertTrue(iterator.next().equals(Integer.valueOf(2)));

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 3);
        assertTrue(iterator.hasPrevious());

        final int[] intArray = {0, 1, 2};
        ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 3);
        assertTrue(!iterator.hasNext());


        final int[] intArray = {0, 1, 2};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 4);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, -1, 1);
            fail("Expecting IndexOutOfBoundsException");
        } catch (final IndexOutOfBoundsException ex) {
            // expected
        }
		assert();

        final int[] intArray = {0, 1, 2};
        try {
            ResettableListIterator<Object> iterator = IteratorUtils.arrayListIterator(intArray, 2, 1);
            fail("Expecting IllegalArgumentException");
        } catch (final IllegalArgumentException ex) {
            // expected
        }
		assert();
    }

    @Test
    public void testAsIterable() {
        final List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(0));
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        final Iterator<Integer> iterator = list.iterator();

        final Iterable<Integer> iterable = IteratorUtils.asIterable(iterator);
        int expected = 0;
        for(final Integer actual : iterable) {
            assertEquals(expected, actual.intValue());
            ++expected;
        }
        // insure iteration occurred
        assertTrue(expected > 0);

        // single use iterator
        assertFalse("should not be able to iterate twice", IteratorUtils.asIterable(iterator).iterator().hasNext());
    }

    @Test
    public void testAsIterableNull() {
        try {
            IteratorUtils.asIterable(null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
            // success
        }
		assertTrue(true);
    }

    @Test
    public void testAsMultipleIterable() {
        final List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(0));
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        final Iterator<Integer> iterator = list.iterator();

        final Iterable<Integer> iterable = IteratorUtils.asMultipleUseIterable(iterator);
        int expected = 0;
        for(final Integer actual : iterable) {
            assertEquals(expected, actual.intValue());
            ++expected;
        }
        // insure iteration occurred
        assertTrue(expected > 0);

        // multiple use iterator
        expected = 0;
        for(final Integer actual : iterable) {
            assertEquals(expected, actual.intValue());
            ++expected;
        }
        // insure iteration occurred
        assertTrue(expected > 0);
    }


    @Test
    public void testAsMultipleIterableNull() {
        try {
            IteratorUtils.asMultipleUseIterable(null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
            // success
        }
		assert();
    }

    /**
     * Tests methods collatedIterator(...)
     */
    @Test
    public void testCollatedIterator() {
        try {
            IteratorUtils.collatedIterator(null, collectionOdd.iterator(), null);
            fail("expecting NullPointerException");
        } catch (final NullPointerException npe) {
            // expected
        }
		assert();

        try {
            IteratorUtils.collatedIterator(null, null, collectionEven.iterator());
            fail("expecting NullPointerException");
        } catch (final NullPointerException npe) {
            // expected
        }
		assert();

        // natural ordering
        Iterator<Integer> it =
                IteratorUtils.collatedIterator(null, collectionOdd.iterator(), collectionEven.iterator());
        List<Integer> result = IteratorUtils.toList(it);
        assertEquals(12, result.size());

        final List<Integer> combinedList = new ArrayList<>();
        combinedList.addAll(collectionOdd);
        combinedList.addAll(collectionEven);
        Collections.sort(combinedList);
		Iterator<Integer> it =
                IteratorUtils.collatedIterator(null, collectionOdd.iterator(), collectionEven.iterator());
        List<Integer> result = IteratorUtils.toList(it);
        assertEquals(combinedList, result);

        Iterator<Integer> it = IteratorUtils.collatedIterator(null, collectionOdd.iterator(), emptyCollection.iterator());
        List<Integer> result = IteratorUtils.toList(it);
        result = IteratorUtils.toList(it);
        assertEquals(collectionOdd, result);

		final List<Integer> combinedList = new ArrayList<>();
        combinedList.addAll(collectionOdd);
        combinedList.addAll(collectionEven);
        Collections.sort(combinedList);
        final Comparator<Integer> reverseComparator =
                ComparatorUtils.reversedComparator(ComparatorUtils.<Integer>naturalComparator());
        Collections.reverse(collectionOdd);
        Collections.reverse(collectionEven);
        Collections.reverse(combinedList);

        it = IteratorUtils.collatedIterator(reverseComparator,
                                            collectionOdd.iterator(),
                                            collectionEven.iterator());
        result = IteratorUtils.toList(it);
        assertEquals(combinedList, result);
    }

    //-----------------------------------------------------------------------
    /**
     * Test empty iterator
     */
    @Test
    public void testEmptyIterator() {
        assertSame(EmptyIterator.INSTANCE, IteratorUtils.EMPTY_ITERATOR);
        assertSame(EmptyIterator.RESETTABLE_INSTANCE, IteratorUtils.EMPTY_ITERATOR);
        assertEquals(true, IteratorUtils.EMPTY_ITERATOR instanceof Iterator);
        assertEquals(true, IteratorUtils.EMPTY_ITERATOR instanceof ResettableIterator);
        assertEquals(false, IteratorUtils.EMPTY_ITERATOR instanceof OrderedIterator);
        assertEquals(false, IteratorUtils.EMPTY_ITERATOR instanceof ListIterator);
        assertEquals(false, IteratorUtils.EMPTY_ITERATOR instanceof MapIterator);
        assertEquals(false, IteratorUtils.EMPTY_ITERATOR.hasNext());
        IteratorUtils.EMPTY_ITERATOR.reset();
        assertSame(IteratorUtils.EMPTY_ITERATOR, IteratorUtils.EMPTY_ITERATOR);
        IteratorUtils.EMPTY_ITERATOR.reset();
        assertSame(IteratorUtils.EMPTY_ITERATOR, IteratorUtils.emptyIterator());
        try {
            IteratorUtils.EMPTY_ITERATOR.next();
            fail();
        } catch (final NoSuchElementException ex) {}
		assert();
        try {
            IteratorUtils.EMPTY_ITERATOR.remove();
            fail();
        } catch (final IllegalStateException ex) {}
		assert();
    }

    //-----------------------------------------------------------------------
    /**
     * Test empty list iterator
     */
    @Test
    public void testEmptyListIterator() {
        assertSame(EmptyListIterator.INSTANCE, IteratorUtils.EMPTY_LIST_ITERATOR);
        assertSame(EmptyListIterator.RESETTABLE_INSTANCE, IteratorUtils.EMPTY_LIST_ITERATOR);
        assertEquals(true, IteratorUtils.EMPTY_LIST_ITERATOR instanceof Iterator);
        assertEquals(true, IteratorUtils.EMPTY_LIST_ITERATOR instanceof ListIterator);
        assertEquals(true, IteratorUtils.EMPTY_LIST_ITERATOR instanceof ResettableIterator);
        assertEquals(true, IteratorUtils.EMPTY_LIST_ITERATOR instanceof ResettableListIterator);
        assertEquals(false, IteratorUtils.EMPTY_LIST_ITERATOR instanceof MapIterator);
        assertEquals(false, IteratorUtils.EMPTY_LIST_ITERATOR.hasNext());
        assertEquals(0, IteratorUtils.EMPTY_LIST_ITERATOR.nextIndex());
        assertEquals(-1, IteratorUtils.EMPTY_LIST_ITERATOR.previousIndex());
        IteratorUtils.EMPTY_LIST_ITERATOR.reset();
        assertSame(IteratorUtils.EMPTY_LIST_ITERATOR, IteratorUtils.EMPTY_LIST_ITERATOR);
        IteratorUtils.EMPTY_LIST_ITERATOR.reset();
        assertSame(IteratorUtils.EMPTY_LIST_ITERATOR, IteratorUtils.emptyListIterator());
        try {
            IteratorUtils.EMPTY_LIST_ITERATOR.next();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_LIST_ITERATOR.previous();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_LIST_ITERATOR.remove();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.emptyListIterator().set(null);
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.emptyListIterator().add(null);
            fail();
        } catch (final UnsupportedOperationException ex) {}
		assertTrue(true);

    }

    //-----------------------------------------------------------------------
    /**
     * Test empty map iterator
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testEmptyMapIterator() {
        assertSame(EmptyMapIterator.INSTANCE, IteratorUtils.EMPTY_MAP_ITERATOR);
        assertEquals(true, IteratorUtils.EMPTY_MAP_ITERATOR instanceof Iterator);
        assertEquals(true, IteratorUtils.EMPTY_MAP_ITERATOR instanceof MapIterator);
        assertEquals(true, IteratorUtils.EMPTY_MAP_ITERATOR instanceof ResettableIterator);
        assertEquals(false, IteratorUtils.EMPTY_MAP_ITERATOR instanceof ListIterator);
        assertEquals(false, IteratorUtils.EMPTY_MAP_ITERATOR instanceof OrderedIterator);
        assertEquals(false, IteratorUtils.EMPTY_MAP_ITERATOR instanceof OrderedMapIterator);
        assertEquals(false, IteratorUtils.EMPTY_MAP_ITERATOR.hasNext());
        ((ResettableIterator<Object>) IteratorUtils.EMPTY_MAP_ITERATOR).reset();
        assertSame(IteratorUtils.EMPTY_MAP_ITERATOR, IteratorUtils.EMPTY_MAP_ITERATOR);
        ((ResettableIterator<Object>) IteratorUtils.EMPTY_MAP_ITERATOR).reset();
        assertSame(IteratorUtils.EMPTY_MAP_ITERATOR, IteratorUtils.emptyMapIterator());
        try {
            IteratorUtils.EMPTY_MAP_ITERATOR.next();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_MAP_ITERATOR.remove();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_MAP_ITERATOR.getKey();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_MAP_ITERATOR.getValue();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_MAP_ITERATOR.setValue(null);
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);
    }

    //-----------------------------------------------------------------------
    /**
     * Test empty map iterator
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testEmptyOrderedIterator() {
        assertSame(EmptyOrderedIterator.INSTANCE, IteratorUtils.EMPTY_ORDERED_ITERATOR);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_ITERATOR instanceof Iterator);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_ITERATOR instanceof OrderedIterator);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_ITERATOR instanceof ResettableIterator);
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_ITERATOR instanceof ListIterator);
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_ITERATOR instanceof MapIterator);
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_ITERATOR.hasNext());
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_ITERATOR.hasPrevious());
        ((ResettableIterator<Object>) IteratorUtils.EMPTY_ORDERED_ITERATOR).reset();
        assertSame(IteratorUtils.EMPTY_ORDERED_ITERATOR, IteratorUtils.EMPTY_ORDERED_ITERATOR);
        ((ResettableIterator<Object>) IteratorUtils.EMPTY_ORDERED_ITERATOR).reset();
        assertSame(IteratorUtils.EMPTY_ORDERED_ITERATOR, IteratorUtils.emptyOrderedIterator());
        try {
            IteratorUtils.EMPTY_ORDERED_ITERATOR.next();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_ITERATOR.previous();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_ITERATOR.remove();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);
    }

    //-----------------------------------------------------------------------
    /**
     * Test empty map iterator
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testEmptyOrderedMapIterator() {
        assertSame(EmptyOrderedMapIterator.INSTANCE, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR instanceof Iterator);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR instanceof MapIterator);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR instanceof OrderedMapIterator);
        assertEquals(true, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR instanceof ResettableIterator);
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR instanceof ListIterator);
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.hasNext());
        assertEquals(false, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.hasPrevious());
        ((ResettableIterator<Object>) IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR).reset();
        assertSame(IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR, IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR);
        ((ResettableIterator<Object>) IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR).reset();
        assertSame(IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR, IteratorUtils.emptyOrderedMapIterator());
        try {
            IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.next();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.previous();
            fail();
        } catch (final NoSuchElementException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.remove();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.getKey();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.getValue();
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);

        try {
            IteratorUtils.EMPTY_ORDERED_MAP_ITERATOR.setValue(null);
            fail();
        } catch (final IllegalStateException ex) {}
		assertTrue(true);
    }

    @Test
    public void testFind() {
        Predicate<Number> testPredicate = equalPredicate((Number) 4);
        Integer test = IteratorUtils.find(iterableA.iterator(), testPredicate);
        assertTrue(test.equals(4));

        Predicate<Number> testPredicate = equalPredicate((Number) 45);
        Integer test = IteratorUtils.find(iterableA.iterator(), testPredicate);
        assertTrue(test == null);

        Predicate<Number> testPredicate = equalPredicate((Number) 45);
        assertNull(IteratorUtils.find(null,testPredicate));

        try {
            assertNull(IteratorUtils.find(iterableA.iterator(), null));
            fail("expecting NullPointerException");
        } catch (final NullPointerException npe) {
            // expected
        }
		assert();
    }

    @Test
    public void testFirstFromIterator() throws Exception {
        // Iterator, entry exists
        final Iterator<Integer> iterator = iterableA.iterator();
        assertEquals(1, (int) IteratorUtils.first(iterator));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testForEach() {
        final List<Integer> listA = new ArrayList<>();
        listA.add(1);

        final List<Integer> listB = new ArrayList<>();
        listB.add(2);

        final Closure<List<Integer>> testClosure = ClosureUtils.invokerClosure("clear");
        final Collection<List<Integer>> col = new ArrayList<>();
        col.add(listA);
        col.add(listB);
        IteratorUtils.forEach(col.iterator(), testClosure);
        assertTrue(listA.isEmpty() && listB.isEmpty());

        final List<Integer> listA = new ArrayList<>();
        listA.add(1);
        final List<Integer> listB = new ArrayList<>();
        listB.add(2);
        final Collection<List<Integer>> col = new ArrayList<>();
        col.add(listA);
        col.add(listB);
        try {
            IteratorUtils.forEach(col.iterator(), null);
            fail("expecting NullPointerException");
        } catch (final NullPointerException npe) {
            // expected
        }
		assert();

        IteratorUtils.forEach(null, testClosure);
        // null should be OK
        final Collection<List<Integer>> col = new ArrayList<>();
        final Closure<List<Integer>> testClosure = ClosureUtils.invokerClosure("clear");
        col.add(null);
        IteratorUtils.forEach(col.iterator(), testClosure);
    }

    @Test
    public void testForEachButLast() {
        final List<Integer> listA = new ArrayList<>();
        listA.add(1);
        final List<Integer> listB = new ArrayList<>();
        listB.add(2);
        final Closure<List<Integer>> testClosure = ClosureUtils.invokerClosure("clear");
        final Collection<List<Integer>> col = new ArrayList<>();
        col.add(listA);
        col.add(listB);
        List<Integer> last = IteratorUtils.forEachButLast(col.iterator(), testClosure);
        assertTrue(listA.isEmpty() && !listB.isEmpty());

		final List<Integer> listA = new ArrayList<>();
        listA.add(1);
        final List<Integer> listB = new ArrayList<>();
        listB.add(2);
        final Closure<List<Integer>> testClosure = ClosureUtils.invokerClosure("clear");
        final Collection<List<Integer>> col = new ArrayList<>();
        col.add(listA);
        col.add(listB);
        List<Integer> last = IteratorUtils.forEachButLast(col.iterator(), testClosure);
        assertSame(listB, last);

        final Collection<List<Integer>> col = new ArrayList<>();
        final List<Integer> listA = new ArrayList<>();
        listA.add(1);
        final List<Integer> listB = new ArrayList<>();
        listB.add(2);
        col.add(listA);
        col.add(listB);
        try {
            IteratorUtils.forEachButLast(col.iterator(), null);
            fail("expecting NullPointerException");
        } catch (final NullPointerException npe) {
            // expected
        }
		assert();

        IteratorUtils.forEachButLast(null, testClosure);

        final Collection<List<Integer>> col = new ArrayList<>();
        final Closure<List<Integer>> testClosure = ClosureUtils.invokerClosure("clear");
        // null should be OK
        col.add(null);
        col.add(null);
        List<Integer> last = IteratorUtils.forEachButLast(col.iterator(), testClosure);
        assertNull(last);
    }

    @Test
    public void testGetAtIndexFromIterator() throws Exception {
        // Iterator, entry exists
        Iterator<Integer> iterator = iterableA.iterator();
        assertEquals(1, (int) IteratorUtils.get(iterator, 0));

        Iterator<Integer> iterator = iterableA.iterator();
        assertEquals(2, (int) IteratorUtils.get(iterator, 1));

        // Iterator, non-existent entry
        try {
            IteratorUtils.get(iterator, 10);
            fail("Expecting IndexOutOfBoundsException.");
        } catch (final IndexOutOfBoundsException e) {
            // expected
        }
        assertTrue(!iterator.hasNext());
    }

    @Test
    public void testGetIterator() {
        assertTrue("returns empty iterator when null passed", IteratorUtils.getIterator(null) instanceof EmptyIterator);
        assertTrue("returns Iterator when Iterator directly ", IteratorUtils.getIterator(iterableA.iterator()) instanceof Iterator);
        assertTrue("returns Iterator when iterable passed", IteratorUtils.getIterator(iterableA) instanceof Iterator);

    	final Object[] objArray = {"a", "b", "c"};
        assertTrue("returns ObjectArrayIterator when Object array passed", IteratorUtils.getIterator(objArray) instanceof ObjectArrayIterator);

        final Map<String, String> inMap = new HashMap<>();
        assertTrue("returns Iterator when Map passed", IteratorUtils.getIterator(inMap) instanceof Iterator);

        final Node[] nodes = createNodes();
        final NodeList nodeList = createNodeList(nodes);
        assertTrue("returns NodeListIterator when nodeList passed", IteratorUtils.getIterator(nodeList) instanceof NodeListIterator);

        assertTrue("returns EnumerationIterator when Enumeration passed", IteratorUtils.getIterator(new Vector().elements()) instanceof EnumerationIterator);

    }

    @Test
    public void testIndexOf() {
        Predicate<Number> testPredicate = equalPredicate((Number) 4);
        int index = IteratorUtils.indexOf(iterableA.iterator(), testPredicate);
        assertEquals(6, index);

        Predicate<Number> testPredicate = equalPredicate((Number) 45);
        int index = IteratorUtils.indexOf(iterableA.iterator(), testPredicate);
        assertEquals(-1, index);

        Predicate<Number> testPredicate = equalPredicate((Number) 45);
        assertEquals(-1, IteratorUtils.indexOf(null, testPredicate));

        try {
            IteratorUtils.indexOf(iterableA.iterator(), null);
            fail("expecting NullPointerException");
        } catch (final NullPointerException npe) {
            // expected
        }
		assert();
    }

    /**
     * Tests method nodeListIterator(Node)
     */
    @Test
    public void testNodeIterator() {
        final Node[] nodes = createNodes();
        final NodeList nodeList = createNodeList(nodes);
        final Node parentNode = createMock(Node.class);
        expect(parentNode.getChildNodes()).andStubReturn(nodeList);
        replay(parentNode);

        final Iterator<Node> iterator = IteratorUtils.nodeListIterator(parentNode);
        int expectedNodeIndex = 0;
        for (final Node actual : IteratorUtils.asIterable(iterator)) {
            assertEquals(nodes[expectedNodeIndex], actual);
            ++expectedNodeIndex;
        }

        // insure iteration occurred
        assertTrue(expectedNodeIndex > 0);

        // single use iterator
        assertFalse("should not be able to iterate twice", IteratorUtils.asIterable(iterator).iterator().hasNext());
    }

    /**
     * Tests method nodeListIterator(NodeList)
     */
    @Test
    public void testNodeListIterator() {
        final Node[] nodes = createNodes();
        final NodeList nodeList = createNodeList(nodes);

        final Iterator<Node> iterator = IteratorUtils.nodeListIterator(nodeList);
        int expectedNodeIndex = 0;
        for (final Node actual : IteratorUtils.asIterable(iterator)) {
            assertEquals(nodes[expectedNodeIndex], actual);
            ++expectedNodeIndex;
        }

        // insure iteration occurred
        assertTrue(expectedNodeIndex > 0);

        // single use iterator
        assertFalse("should not be able to iterate twice", IteratorUtils.asIterable(iterator).iterator().hasNext());
    }

    @Test
    public void testToArray() {
        final List<Object> list = new ArrayList<>();
        list.add(Integer.valueOf(1));
        list.add("Two");
        list.add(null);
        final Object[] result = IteratorUtils.toArray(list.iterator());
        assertEquals(list, Arrays.asList(result));

        try {
        	IteratorUtils.toArray(null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
            // success
        }
		assert();
    }

    @Test
    public void testToArray2() {
        final List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add(null);
        final String[] result = IteratorUtils.toArray(list.iterator(), String.class);
        assertEquals(list, Arrays.asList(result));

        try {
        	IteratorUtils.toArray(list.iterator(), null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
            // success
        }
		assert();

        try {
        	IteratorUtils.toArray(null, String.class);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
            // success
        }
		assert();
    }

    @Test
    public void testToList() {
        final List<Object> list = new ArrayList<>();
        list.add(Integer.valueOf(1));
        list.add("Two");
        list.add(null);
        final List<Object> result = IteratorUtils.toList(list.iterator());
        assertEquals(list, result);
    }

    @Test
    public void testToListIterator() {
        final List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(0));
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        final Iterator<Integer> iterator = list.iterator();

        final ListIterator<Integer> liItr = IteratorUtils.toListIterator(iterator);
        int expected = 0;
        while(liItr.hasNext()){
        	assertEquals(expected, liItr.next().intValue());
        	++expected;
        }
    }

    @Test
    public void testToListIteratorNull() {
        try {
            IteratorUtils.toListIterator(null);
            fail("Expecting NullPointerException");
        } catch (final NullPointerException ex) {
            // success
        }
		assert();
    }

    /**
     * Test remove() for an immutable Iterator.
     */
    @Test
    public void testUnmodifiableIteratorImmutability() {
        final Iterator<String> iterator = getImmutableIterator();

        try {
            iterator.remove();
            // We shouldn't get to here.
            fail("remove() should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);

        final Iterator<String> iterator = getImmutableIterator();
        iterator.next();

        try {
            iterator.remove();
            // We shouldn't get to here.
            fail("remove() should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);
    }

    //-----------------------------------------------------------------------
    /**
     * Test next() and hasNext() for an immutable Iterator.
     */
    @Test
    public void testUnmodifiableIteratorIteration() {
        final Iterator<String> iterator = getImmutableIterator();
        assertTrue(iterator.hasNext());

        final Iterator<String> iterator = getImmutableIterator();
        assertEquals("a", iterator.next());

        final Iterator<String> iterator = getImmutableIterator();
        assertTrue(iterator.hasNext());

        final Iterator<String> iterator = getImmutableIterator();
        assertEquals("b", iterator.next());

        final Iterator<String> iterator = getImmutableIterator();
        assertTrue(iterator.hasNext());

        final Iterator<String> iterator = getImmutableIterator();
        assertEquals("c", iterator.next());

        final Iterator<String> iterator = getImmutableIterator();
        assertTrue(iterator.hasNext());

        final Iterator<String> iterator = getImmutableIterator();
        assertEquals("d", iterator.next());

        final Iterator<String> iterator = getImmutableIterator();
        assertTrue(!iterator.hasNext());
    }

    /**
     * Test remove() for an immutable ListIterator.
     */
    @Test
    public void testUnmodifiableListIteratorImmutability() {
        final ListIterator<String> listIterator = getImmutableListIterator();

        try {
            listIterator.remove();
            // We shouldn't get to here.
            fail("remove() should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);

        final ListIterator<String> listIterator = getImmutableListIterator();
        try {
            listIterator.set("a");
            // We shouldn't get to here.
            fail("set(Object) should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);

        final ListIterator<String> listIterator = getImmutableListIterator();
        try {
            listIterator.add("a");
            // We shouldn't get to here.
            fail("add(Object) should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);

        final ListIterator<String> listIterator = getImmutableListIterator();
        listIterator.next();
        try {
            listIterator.remove();
            // We shouldn't get to here.
            fail("remove() should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);

        final ListIterator<String> listIterator = getImmutableListIterator();
        try {
            listIterator.set("a");
            // We shouldn't get to here.
            fail("set(Object) should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);

        final ListIterator<String> listIterator = getImmutableListIterator();
        try {
            listIterator.add("a");
            // We shouldn't get to here.
            fail("add(Object) should throw an UnsupportedOperationException");
        } catch (final UnsupportedOperationException e) {
            // This is correct; ignore the exception.
        }
		assertTrue(true);
    }

    /**
     * Test next(), hasNext(), previous() and hasPrevious() for an immutable
     * ListIterator.
     */
    @Test
    public void testUnmodifiableListIteratorIteration() {
        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(!listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("a", listIterator.next());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("b", listIterator.next());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("c", listIterator.next());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("d", listIterator.next());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(!listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("d", listIterator.previous());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("c", listIterator.previous());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("b", listIterator.previous());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertEquals("a", listIterator.previous());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(!listIterator.hasPrevious());

        final ListIterator<String> listIterator = getImmutableListIterator();
        assertTrue(listIterator.hasNext());
    }

}
