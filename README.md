# compilation-problem

## Context

In a private project the following usage of hibernate validations did not compile with JDK16,17 but worked fine in JDK11:

```
@NotEmpty(message = "{NotEmpty.option.options}")
private List<@NotEmpty(message = "{NotEmpty.option.options}") String> options = new TreeList<>();
```

Changing to
```
@NotEmpty(message = "{NotEmpty.option.options}")
private List<String> options = new TreeList<>();
```
works, but empty elements in the list are not marked as error.

## Not reproducable :(

This project does not show the behaviour.
