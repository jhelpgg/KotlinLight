# Kotlin light

### What is Kotlin light ?

Like the name suggest, it is Kotlin with fewer features. 
The result is enough to do any program. 
It is more strict.  

### What is the aim of Kotlin light ?

The aim is to have a Kotlin version more easy to translate in Swift, with the Kotlin to Swift tool :
[Kotlin to Swift](https://github.com/jhelpgg/KotlinToSwift)

### How develop in Kotlin light ?

To develop in Kotlin light, you can use any editor/IDE that manage Kotlin language, like IntelliJ, AndroidStudio, ...

You will need to include this project to have tools : list, map, threads, annotations and extensions.

### Kotlin light reference manual

Here we suppose you know Kotlin language.

Their only concrete **class** and **interface**.

For now no :

* **data class**
* **abstract class**
* **enum class**
* **sealed class** 
* **object**

Since either not exists in Swift or not having the exact same signification.
We will provide later some equivalence.

Like Kotlin, class can be **open** or final, **internal** or public.

Functions and fields can be **private**, **internal** or public. No **protected** level.
Functions can be **open** or final.

Important : one class/interface per file, no inner/anonymous class
Global functions or fields can't put in same file as class or interface

#####  Class

The construction, like that :

```kotlin
class Person(val name:String)
```

not work for now. Must be replaced by explicit **constructor**:

```kotlin
class Person
{
   val name : String
   
   constructor(name:String)
   {
      this.name = name
   }
}
```

Example with several **constructor** :

```kotlin
class Car
{
    val model : String
    val year : Int

    constructor(model:String, age:Int)
    {
       this.model = model
       this.year = year
    }
   
    constructor(model:String)
    {
       this(model, 2020)
    }
}
```

With inheritance :

```kotlin
open class Vehicule
{
    val year : Int

    constructor(year:Int)
    {
        this.year = year
    }

    constructor()
    {
        this.year = 2020
    }
}
```

```kotlin
class Car : Vehicule
{
    val model: String

    constructor(model: String, year: Int) : super(year)
    {
        this.model = model
    }

    constructor(model: String) : super()
    {
        this.model = model
    }
}
```


If constructor is empty (does nothing) it can be omitted:

```kotlin
class SetOfFruit
{
    // ....
}
```

##### Interface

Like Kotlin :

```kotlin
interface HaveStringDescription
{
    fun stringDescription() : String
}
```

```kotlin
class Building : HaveStringDescription
{
    override fun stringDescription() : String
    {
        return "Nice building!"
    }
}
```

##### Companion object

Only anonymous **companion object** are managed :

```kotlin
class Test
{
     companion object
     {
         fun somethingStatic()
         {
         }
     }

    fun somethingInstance()
    {
    }
}
```

##### List

Array or list, are managed only by ` fr.jhelp.kotlinLight.CommonList` 

```kotlin
var namesList = CommonList<String>()
namesList.append("Hello")
namesList.append("World")
println("nameList empty : ${namesList.isEmpty()}")
println("nameList size : ${namesList.count}")
namesList.insert("all the", at=1)

for(value in namesList)
{
    println(value)
}

val k = namesList[1]
namesList[2] = "person"
namesList.remove(at=1)
val inside = namesList.contains("Hello")

for((index, value) in namesList.enumerated())
{
    println("$index : $value")
}

namesList.removeAll()
```

**Warning** 
> Only create with **var**. 
> With **val** translated in Swift, will produce an empty immutable list.
>
> **at=** is mandatory for **remove** and **insert**
>
> Use **contains** method, not **in** form. Write `"Arthur" in nameList` will not work translated in Swift.

##### Map

Only map can be use is `fr.jhelp.kotlinLight.CommonMap`.

```kotlin
var map = CommonMap<Int, String>()
map[0] = "Zero"
map[5] = "Five"
map[42] = "The answer"
map[73] = "Magic number"

// Remove 5 elements :
map[5] = null

val string1 = map[42] ?: "No value"
val string2 = map[5] ?: "No value"

println(string1)
println(string2)
println("size = ${map.count}")
println("empty = ${map.isEmpty}")

for((key, value) in map)
{
    println("$key +> $value")
}

for(key in map.keys)
{
    println("$key")
}

for(value in map.values)
{
    println("$value")
}
``` 

**Warning** 
> Only create with **var**. 
> With **val** translated in Swift, will produce an empty immutable map.

##### Generics

Generics work in simple cases. Complex cases may fail.

An **interface** with generics can be used with a **class** or with other **interface**, but not with global functions or global fields.

```kotlin
interface Transformer<S,D>
{
   fun transform(source:S) : D
}
``` 

```kotlin
class IntToString : Transformer<Int, String>
{
    override fun transform(source:Int) : String
    {
        return "$source"       
    }
}
```

###### Import from Swift

Some Swift tools need o import the module. 
For this use `fr.jhelp.kotlinLight.ImportSwift` annotation before just between imports and anything else :

```kotlin
// ...
import fr.jhelp.kotlinLight.ImportSwift
// ...

@ImportSwift("Dispatch")

class SomeClass // ....
```

Next point will use it

###### Parallel task

To do task in parallel, can use directly `fr.jhelp.kotlinLight.DispatchQueue`

Just have to know, that `@ImportSwift("Dispatch")` is mandatory and don't forget the `execute=` and `deadline=`.
Have each time to deal with `fr.jhelp.kotlinLight.DispatchTime` and `fr.jhelp.kotlinLight.DispatchTimeInterval`.
They also need `@ImportSwift("Dispatch")`.

We will publish soon some tools wrote in Kotlin light can be copy/paste in your project. 
In waiting them, we give you a file to include to your project, to simplify parallel task usage :

```kotlin
import fr.jhelp.kotlinLight.DispatchQueue
import fr.jhelp.kotlinLight.DispatchTime
import fr.jhelp.kotlinLight.DispatchTimeInterval
import fr.jhelp.kotlinLight.ImportSwift

@ImportSwift("Dispatch")

fun launchTask(task: () -> Unit)
{
    DispatchQueue.global().async(execute = task)
}

fun delayTask(delayMilliseconds: Int, task: () -> Unit)
{
    DispatchQueue.global().asyncAfter(deadline = DispatchTime.now()
                                                 + DispatchTimeInterval.milliseconds(delayMilliseconds),
                                      execute = task)
}
```

With this launch parallel task is easy :

```kotlin
launchTask { doSomthingInParallel() }

delayTask(4096) {  doSomethingFourSecondsLater()  }
``` 

###### Exceptions and error handling

Actual managed exceptions are :

* Exception
* IllegalArgumentException
* IllegalStateException
* RuntimeException
* NullPointerException

Only with a message. The stack trace is not manage.

No custom exceptions.

If a function or constructor may throw an exception, the annotation `@Throws` is mandatory.

For example, we want avoid give a negative age to a Person :

```kotlin
import  fr.jhelp.kotlinLight.guard

class Person
{
    private var age : Int
  
    @Throws constructor(age:Int)
    {
        (age>=0).guard { throw IllegalArgumentException("Age must be positive, not $age") }
        this.age = age
    }
  
    fun getAge() : Int
    {
       return this.age
    }

    @Throws fun setAge(age:Int)
    {
        (age>=0).guard { throw IllegalArgumentException("Age must be positive, not $age") }
        this.age = age
    }
}
```

Like in example, to throw exception, use `gaurd`/`throw` couple.

`guard` will call the lambda if the evaluated `Boolean` is `false`

When a method may throw, it is mandatory to `try`/`catch` the call :

```kotlin
import fr.jhelp.kotlinLight.Try

// ...

try
{
   @Try val person = Person(42)
   println("person is ${person.getAge()} years old")
   @Try person.setAge(73)
}
catch(error:Exception)
{
    println("error=$error")
}
```

Only one `catch` is possible and `finally` is not managed.

The name of catch exception must be **error**

Add `@Try` annotation on lines that may throw

##### Mutex

It is possible to create a critical session, to able protect some field:

```kotlin
import fr.jhelp.kotlinLight.Mutex

class BankAccount
{
    private val mutex : Mutex
    private var account : Int
  
    constructor()
    {
       this.mutex = Mutex()
       this.account = 0
    }

    fun getAccount() : Int
    {
        var value = 0
        mutex.safeExecute { value = this.account }
       return value
    }
    
    fun addMoney(value:Int)
    {
        mutex.safeExecute { this.account = this.account + value }
    }
}
```

Only one thread can access on the lambda of `safeExcecute` at a time for the same instance of `Mutex`.

So if two threads tries to add money on same time, and an-other try read account in same times, two will wait one finish, 
then one wakeup, then last one.

###### Lambda

Lambda works fine. Just remember one rule : the usage of `this` is mandatory inside them.


###### when

When work if they have at least one no `else` case and the `else` condition is mandatory and must be put as last case

```kotlin
    when (parameter)
    {
        0 -> println("ZERO")
        5 -> println("FIVE")
        25 -> println("Twenty five")
        42 -> println("The answer")
        73 -> println("Magic Number")
        666 ->
        {
            println("Don't play with it : ")
            println("   It is the Beast !!!")
        }
        else -> println("Nothing to say for $parameter")
    }
```

```kotlin
    when
    {
        integer == 5                       -> println("FIVE")
        integer < 5 && string.isNotEmpty() -> println("$integer => $string")
        integer > 5 && string.isEmpty()    ->
        {
            println("Best car ever : DeLoreen 1985")
        }
        else                               ->
        {
            println("I have a machine gun now! Ho! Ho! Ho!")
        }
    }
```

###### Other features :

Elvis `?:` work force not null `!!` also, `is`, `as` also

String replacement work for simple cases :
```kotlin
"key=$key, value=${function(key)}"
``` 

Consider their no smart cast

##### Next steps

Take a look of [Samples](https://github.com/jhelpgg/KotlinLightSamples) to have more inspiration and see examples of Swift generated files

Evolve [Kotlin to Swift](https://github.com/jhelpgg/KotlinToSwift) :

* To manage : `enum class`, clear the map
* Make a precompiler to detect code out of scope of Kotlin light   
* Make a trick to manage `data class`, `absract class`, `sealed class`
