# Flattener

A simple library that allows one to easily turn a Java bean of arbitrary complexity (with circlic references included) into a flat string to string map and vise versa. Since a flat map is much more efficient and easier to work with, this opens up great potentials for the creation of other generic APIs and applications. Below are some generic APIs that may be created as follow up to this API.

* Generic Serialization / deserialization for any bean (does not have to be declared as Serializable)
* Generic bean mapping externalized and with ability to support complex mapping rules + calculations
* Generic object diffs and updates using delta
* Generic support of undo/redo of objects
* Generic persistence allow easy navigation from snapshot to snapshot
* Generic object equals (as a special case for the below)
* Generic rules built for objects-configuration driven (e.g., rules to determine similar beans)
