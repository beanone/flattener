# Flattener

A simple library that allows one to easily turn a Java bean of arbitrary complexity (with cyclic references included) into a flat string to string map and vise versa. 

This can be viewed as a new java serialization / deserialization mechanism. However, unlike most of the other well accepted serialization methods, such as XML or Json serialization, Flattener keeps the attribute data type in the serialized form and thus it is much simpler to deserialize.

Since a flat map is much more efficient and easier to work with, this opens up great potentials for the creation of other generic APIs and applications. Below are some that can be created as follow up to this API.

* Generic Serialization / deserialization for any bean (does not have to be declared as Serializable)
* Generic bean mapping externalized and with ability to support complex mapping rules + calculations
* Generic object diffs and updates using delta
* Generic support of undo/redo of objects
* Generic persistence allow easy navigation from snapshot to snapshot
* Generic object equals (as a special case for the below)
* Generic rules built for objects-configuration driven (e.g., rules to determine similar beans)

## Usage

### For flattening:

	Map<String, String> flatted = new FlattenerTool().flat(aBean);

### For unflattening:

	Object aBean = new FlattenerTool().unflat(flatted);
