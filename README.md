# Flattener

A simple library that allows one to easily turn a Java bean of arbitrary complexity (with cyclic references included) into a flat string to string map and vise versa. 

This can be viewed as a new java serialization / deserialization mechanism. However, unlike most of the other well accepted serialization methods, such as XML or Json serialization, Flattener keeps the attribute data type in the serialized form and thus it is much simpler to deserialize.

Since a flat map is much more efficient and easier to work with, this opens up great potentials for the creation of other generic APIs and applications. Below are some that can be created as follow up to this API.

* Generic Serialization / deserialization for any bean (does not have to be declared as Serializable))
* Generic bean mapping externalized and with ability to support complex mapping rules + calculations (see <a href="https://github.com/beanone/xmapper">xmapper</a>)
* Generic object diffs and updates using delta (see <a href="https://github.com/beanone/beanone">beanone</a>)
* Generic persistence allow easy navigation from snapshot to snapshot (see <a href="https://github.com/beanone/beanone">beanone</a>)
* Generic support of undo/redo of objects
* Generic object equals (as a special case for the below)
* Generic rules built for objects-configuration driven (e.g., rules to determine similar beans)

Another very important benefit that many might not realize is that, the act of flattening the object, essentially separates the object structure out so that you have an opportunity to work with object structure meta data directly. It is this advantage that makes it possible to easily implement a sophisticated generic and extensible object mapper (see <a href="https://github.com/beanone/xmapper">xmapper</a>).

## Usage

### For flattening:

	Map<String, String> flatted = new FlattenerTool().flat(aBean);

### For unflattening:

	Object aBean = new FlattenerTool().unflat(flatted);

By default, Flattener can handle primitive Java types and their wrapper classes, String, java.util.Date, java.sql.Date, java.sql.Time, java.sql.Timestamp, BigInterger, BigDecimal, AtomicInteger, AtomicLong, AtomicBoolean and simple enum values. All of which are treated as primitive flattener types (types that can be serialized to and from String).

All java.util.Collection objects are handled by a CollectionFlattener and CollectionUnflattener. java.util.Map objects are handled by MapFlattener and MapUnflattener. All array objects are handled by ArrayFlattener and ArrayUnflattener. All other objects are handled by DefaultFlattener and DefaultUnflattener by default.

## Advanced Usage

As seen above, this component already supports most of what one would need. However, there will still be cases where you need to extend it. For such advanced uses, you can define your own primitive flattener type and complex types.

To register your own primitive flattener types, invoke the FlattenerTool.registerConverter() method. Every such type must be given an abbreviation (the third parameter). This abbreviation will be rendered as part of the value in the form of "abbr, value" during serialization, which can then be used by the consumer of the generated map to determine the data type of the value when needed. The ValueConverter is the second parameter of the method, and by default, it converts the object to String using the toString() method of the object. You can override it if needed. You implement the valueOf() method of the ValueConverter to define how String is de-serialized into your object.

For complex types that you want to rendered to more than one string attributes (as oppose to the case with primitive flattener types), and if you don't like the flattener default serialized form of the object, you can define your own custom Flatteners and Unflatteners and register them with the FlattenerTool for it to be included. To implement a custom Flattener, simply extend the AbstractFlattener and override doFlat() method. You can also directly implement the Flattener interface. To implement custom Unflattener, simply extend AbstractUnflattener and override doCreateObject() and doPopulate() methods. You can also directly implement Unflattener.


