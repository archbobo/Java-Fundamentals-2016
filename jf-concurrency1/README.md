Java Fundamentals - 2016, Homework 7
===========

This homework compiles for me on Java 8. 
```
java -version
java version "1.8.0_91"
Java(TM) SE Runtime Environment (build 1.8.0_91-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.91-b14, mixed mode)
```

Build the executable jar:
``` 
mvn clean install
```

Run the jcstress test, change the pattern given as the *-t* argument to specify which tests to execute.
The command below will execute all tests that start with JFHW7. 
```
java -Xbootclasspath/a:./ext-lib/jcstress-core.jar:./ext-lib/jopt-simple.jar:./target/jf2016-hw7-0.0.1-SNAPSHOT.jar org.openjdk.jcstress.Main -t=".*JFHW7"
```

**NB. this complicated way of running is because jcstress master is set to require Java 9 minimum now. So we are using a bit older build. 


Check the results in the "results" dir. 
Copy the relevant portion of the HTML result table into the comment in the source file of the test and explain the details of why did it happen this way. 

Check the JFHW7E1.java for more details on what code is necessary for the tests.
 
Add the tests the homework requires you to, execute them, experiment with it. Have fun :) 
I mean since the harness tells you what results were achieved it's hard to make mistakes here.
 If you need inspiration check out the existing tests in the [jcstress repo](http://hg.openjdk.java.net/code-tools/jcstress/).
 

Good luck!


Consult the original readme on how to submit the homework. I just basically need your .java files and your student number. But submit it properly, 
then the probability of me missing the emails is smaller.
 
Cheers, 
Oleg!


Here is the description of the tests you have to create: 

JFHW7E1.java
-------
``` 
Shared memory: int a; int b

Thread 1: b = 1; x = a;
Thread 2: a = 1; y = b;
```

**Question**: what values of x, y can be observed at the end? 
 
JFHW7E2.java
--------
``` 
Shared memory: an instance of java.util.BitSet

Thread 1: sets 0th bit of the BitSet to true
Thread 2: sets 1st bit of the BitSet to true
```

**Question**: what values of 0th and 1st bits in the bitset can be observed at the end?  
  
JFHW7E3.java
--------
  
Come up with a description of the test that shows non-trivial executions.
If a result of the test shows interesting reorderings, the properties of the volatiles, or something you think is worth showing. 
  
Create a test and explain it. 
 
Good luck!
 
===========
The Original README is below! 
===========
The project is intended for the Java Fundamentals class as a skeleton project. It is a [Maven](http://maven.apache.org/)
project with a dependency on [Apache Commons Lang](http://commons.apache.org/lang/) and it produces a single
JAR file that can be executed via

```shell
java -jar target/jf2016-executable.jar
```

Getting Started
---------------

Start off by downloading this repository ([quick ZIP archive link](https://github.com/toomasr/jf-skeleton/zipball/master)) or cloning it via Git. Once you have the archive we will use Maven to build the project.

```shell
cd jf-skeleton
mvn install
java -jar target/jf2016-executable.jar
```

This builds and executes the project. The current project will print the number 16 (just an example). When we check your
homework we will be using the very same steps! We will use *mvn install* to build your project and then *java -jar*
to start your application.

Assignment structure
--------------------------

The project has a source folder *src/main/java/ee/ut/jf2016/*. The sample is based on the first homework and
also specifies a subfolder called *hw1*. You should **change this** depending on your assignment.

The project also has a folder for tests. See *test/java/ee/ut/jf2016*. The sample is based on the first homework
and also specifies a folder *hw1*. You should **change this** depending on your assignment.

Using Eclipse
-------------

If you are an Eclipse user then you can import the project to your workspace. *File* - *Import* - *Existing Maven Projects*.

To run the project in Eclipse you should ..... oh wait! You are already familiar with your IDE and I'm not supposed to explain this!

Using the commandline
---------------------

Once you have downloaded the project and you want to run the main class you can do the following

```shell
cd jf-skeleton
mvn install
java -jar target/jf2016-executable.jar
```

Now you can edit your homework and rerun the sample. The *pom.xml* instructs the *jf2016-executable.jar* to execute
the *ee.ut.jf2016.Main* class by default. If you want to use some other class then you also need to update the *pom.xml*.

Submitting your assignment
--------------------------

For your convenience, we have set up the Maven project to ZIP up all files in your project folder so it is easy for you to attach it to an e-mail and send it our way. All you need to do is to execute the following command in your project folder:

```
mvn clean deploy
```

It will ask you for your full name, Student Book Number (also known as *matrikli number*), homework number and a comment (optional).

Example:

```bash
mvn clean deploy

#...skipping building, testing and packaging output from Maven...

[INFO] --- maven-antrun-plugin:1.7:run (package homework ZIP) @ jf2016-hwX ---
[INFO] Executing tasks

main:
Your full name (e.g. John Smith):
Jane Smith
Your Student Book Number (matrikli number, e.g. B12345):
B12345
Homework number:
1
Comment:
Java IO
      [zip] Building zip: /Users/jane/Workspaces/JF/jf-skeleton/target/jf-howework-B12345-1.zip
   [delete] Deleting: /Users/jane/Workspaces/JF/jf-skeleton/homework.properties
[INFO] Executed tasks
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 45.028s
[INFO] Finished at: Thu Aug 28 15:36:19 EEST 2014
[INFO] Final Memory: 17M/99M
[INFO] ------------------------------------------------------------------------
```

After Maven has finished, you can find the generated ZIP file in *target* folder with name such as 
*jf-homework-B12345-1.zip* (it contains your Student Book Number/matrikli number and homework number).

Attach the ZIP to an e-mail and **send it our way**, to *jf@zeroturnaround.com*.
