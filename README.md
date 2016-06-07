# flattener

A simple library that allow one to easily turn a Java bean of arbitrary complexity (with circlic references included) into a flat string to string map and vise versa. Since a flat map is much more efficient and easier to work with, this opens great potential for the creation of other generic APIs and applications. Below are some generic APIs that may be created as follow up to this API.

* Generic bean Serialization / deserialization
* Generic bean mapping externalized and with complex mapping rules + calculations involved
* Generic object diffs
* Generic object updates using delta
* Generic support of undo/redo of objects
* Generic persistence allow easy navigation from snapshot to snapshot
* Generic object equals
* Generic rules built for objects and configuration driven (e.g., rules to determine which object is similar to which)
