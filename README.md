## Spring Response Filter

This is a sample implementation of filtering JSON responses at the web layer in a spring application. For an API that consumes data from a data layer with the following structure, it supports queries of the kind `/data?key1=foo&key2=bar&key5=zoo`, with a simple annotation.

```json
[
    {
      key1: "foo",
      key2: "foo",
      key3: "foo",
      ...
    }, {
      key1: "foo",
      key2: "foo",
      key3: "foo",
      ...
    }, {
      key1: "foo",
      key2: "foo",
      key3: "foo",
      ...
    }
    ...
]
```
