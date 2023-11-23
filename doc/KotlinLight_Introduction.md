# Kotlin Light

## Context

Actual multiplatform solutions have lot of issues :

* Some need to learn another language

* Have performance issue

* Memory management issues

* Multi-thread management issues

* Build a complex SDK become a nightmare

* Debug can be very tricky when only thing we know is the memory address where crash happen

* Not easy to maintain/evolute

* ...

The idea here is to convert **Kotlin** code to **Swift** code. The compilation of code made by each platform.

It avoids issues naturally for debugging trace, memory management and performance.

We add tools for solve any multi-thread issue

The only constraints, is to build **Kotlin** code that can match to **Swift**, a simple translation is not always enough. 
**Swift** have some constraints that **Kotlin** not have. 

To solve this issue, we decided to not managed, for now, all **Kotlin** features and add some development constraints.
That's why we will talk about **Kotlin light**. 

This documentation supposed reader know **Kotlin** language. We will explain actual choices made.

We provide some helpers code in **Kotlin light** at :
[Kotlin light tools](https://github.com/jhelpgg/KotlinLightTools)

## Menu

1) How work with **Kotlin light**
    1) Write code
    1) Write unit tests
    1) Convert to swift code/unit tests
    1) Check if code compile in swift 
    1) Launch unit tests in swift
1) **Kotlin light** language
    1) Swift reserved key-words to not use as name
    1) Reserved class/method by **Kotlin light** (Pair, GenericError, TimeCalendar, Mutex, timeSince1970InMilliseconds, fatal, fatalError)
    1) What it not exists in **Swift** (`abstract`, `sealed class`, `protected`, smart cast, `fun t() =`, `r=if()`, `k=when`, `return if`, `return when`, `data class`) 
    1) Code file contents limitation
    1) Class/interface naming restriction
    1) Method naming restriction 
    1) Class/method visibility
    1) Constructor. Decide when add `@Override` annotation. The `@Super` annotation.
    1) `this` usage
    1) Lambda/closure
    1) Exception creation
    1) Exception management : @Throws, @Try, guard, try/catch
    1) `open` usage and restriction
    1) `abstract` is managed by the compiler.
    1) List/Map management (firstOrNull)
    1) Thread and concurrence  management
    1) toString, equals and Comparable 
    1) No `data class`. Have to manually add toString/equals 
    1) `enum`
    1) `when` need a `else` clause at the end
    1) `companion object` must be anonymous
    1) No affectation in `when`/`while`
    1) Write unit tests
    1) Math operations (pow, cos, sin, tan, log, exp, Math.random, Math.PI, Math.E, sqrt) (@ImportSwift("Foundation"))
    1) String manipulations
    1) Primitives conversions
1) Samples
    1) Injection sample : Logs
    