# Core Collections

## Fundamental Interfaces

The ``ValueList<V>`` interface provides the non-modifying methods common to ``ArrayList`` and ``LinkedList``.

The ``MutableValueList<V>`` interface extends ``ValueList<V>`` to provide most of the methods that modify lists.

## Keyed Collections

### Keyed Collections Having the Map Interface

The ``KeyedList<K, V>`` interface extends the ``Map<K, V>`` interface.
For a ``KeyedList<K, V>``, each item in the list is a ``KeyMutableValueContainer<K, V>``.
Thus, each value has its own key. 

The ``KeyedArrayList<K, V>`` and ``KeyedLinkedList<K, V>`` classes
implement the ``KeyedList<K, V>`` interface.
* The ``KeyedArrayList<K, V>`` class also implements the ``ArrayListBased`` class,
allowing capacity adjustments.
* The ``KeyedLinkedList<K, V>`` class implements methods from ``LinkedList<K, V>``,
such as ``getFirst()`` and ``pop()``.

### Multivalued Keyed Collections

These are collections with a key, ``K``, and a list of values, ``V``.
Typically, the key is for use in another collection, and applies to the entire list.

The ``KeyValueList<K, V>`` interface extends ``Keyed<K>`` and ``CommonCollectionSize``.
A ``KeyValueList<K, V>`` is a ``List<V>`` plus a key for the entire collection.

The ``KeyMutableValueList<K, V>`` interface extends ``KeyValueList<K, V>``,
adding ``MutableValueList<V>``.

The ``KeyValueArrayList<K, V>`` and ``KeyValueLinkedList<K, V>`` classes
implements the ``KeyValueList<K, V>`` and ``KeyMutableValueList<K, V>`` interfaces.
* The ``KeyValueArrayList<K, V>`` class also implements the ``ArrayListBased`` class,
allowing capacity adjustments.
* The ``KeyValueLinkedList<K, V>`` class implements methods from ``LinkedList<K, V>``,
such as ``getFirst()`` and ``pop()``.

### Class-identifying Collections

Due to type erasure, sometimes a class must be stored with the collection.
Class-identifying collections do this.
The interfaces and classes described here provide one ``ClassKey`` that applies to the entire collection.

The ``ClassIdentifiedElements`` interface indicates that the collection carries a class identification.
This need not be the exact class of the values in the collection.
For example, a class whose values store primitive ``double`` values might have ``double.class`` in
its class identification.

The ``ClassIdentifiedElementsStrict`` interface conveys that the class identification does directly relate
to the class of the values in the collection.

The ``ClassIdentKeyedList<K, V>`` interface extends the ``KeyedList<K, V>`` interface,
with these implementations:
* ``ClassIdentKeyValueArrayList<K, V>``, which extends ``KeyValueArrayList<K, V>``;
* ``ClassIdentKeyedLinkedList<K, V>``, which extends ``KeyValueLinkedList<K, V>``.

The ``ClassIdentKeyValueList<K, V>`` interface merges the ``KeyValueList<K, V>``
and ``ClassIdentifiedElements`` interfaces,
and the ``ClassIdentKeyMutableValueLists<K, V>`` interface merges the ``KeyMutableValueList<K, V>``
and ``ClassIdentifiedElements`` interfaces.
These classes implement the ``ClassIdentKeyMutableValueLists<K, V>`` interface:
* ``ClassIdentKeyValueArrayList<K, V>``, which extends ``KeyValueArrayList<K, V>``;
* ``ClassIdentKeyValueLinkedList<K, V>``, which extends ``KeyValueLinkedList<K, V>``.

There is also a ``ClassIdentMap<K, V>`` interface,
and a ``ClassIdentHashMap<K, V>`` class implementing that interface.
The ``ClassIdentKeyedList<K, V>`` interface also extends ``ClassIdentMap<K, V>``.

## Common Keys

There are several classes intended for use as keys.

### NamedKey

Although the ``NamedKey`` has a text name, this is purely for diagnostic use.
Two distinct instances of ``NamedKey`` will never be equal.

### NameToken

The equality test for ``NameToken`` implementations depends on the name.
There is also a null key value, ``NameToken.Anon``, which is never equal to itself or any other value.

### Sequential Keys

Sequential keys implement the ``EquatableSequentialKey`` interface.
Classes of keys for specific purposes should be derived from ``EquatableSequentialKeyBase``,
so that meaningless comparisons of incommensurable keys can be caught.
