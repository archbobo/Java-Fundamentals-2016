Java Fundamentals - Concurrency API Homework
===========

Description
----------

Imagine I own a web-store. 
I am a smart guy, so I do not store my passwords plaintext, rather I salt them first, then store the salt and the hash. 
However, I am not _that_ smart, and I use a very sad 16-bit hashing function. 

Tragedy strikes, and my full DB of salts and hashes leaks to you. 

Your assignment is to brute-force your own valid passwords that, using the same salt, result in the same hash as my original passwords. 

Of course you are also smart, so you will solve this using multiple processors, for faster cracking. 
To do this, you will design a system where producer threads will keep on generating random Strings to serve as inputs for the hashing function. 
Consumer threads will now continuously read these Strings, and test whether, using String + Salt, it results in the same hash that you want to break / find a collision for. 
When all hashes are broken, the system will shut down, as its work is now over. 
At the very end you will return a list of equally valid passwords (Note, passwords, not hashes, not salted passwords, just passwords). 

To score the maximum points: 
* Follow all requirements below
* Make the single test pass, it verifies if you indeed found hash collisions

Requirements
----------

1. Use a fixed nr of producer threads: 2 to be precise
2. Use a fixed nr of consumer threads: 4 of them
3. A producer's job is to keep producing, in a **Thread-Safe** way, **Random** inputs, ad infinitum, until asked to shutdown
4. A consumer's job is to keep taking in random inputs, checking if they produce a hash collision, and if so, record which input triggered the collision 
5. Do not manually manage your threads, recall Executors from previous lectures
6. Keep the concurrent collections, and the synchronisers in mind discussed this lecture, and use them appropriately
7. Absolutely do not use the following: any form of _Lock_, _synchronized_, _volatile_
8. Unless really needed, try to avoid using the _Atomic\*_ classes
9. Implement a clean shutdown mechanism. The Consumer and Producer are already supplied with a _shutdown()_ method, you **may** use this, or any other thread-safe, clean, shutdown mechanism
10. Use the _Salt_ and _Hash_ classes to find the data you need to break
11. Return a list of passwords, in the same order, so they correspond to the data in _Salt_ and _Hash_
12. Do not add any new libraries, the ones in _pom.xml_ will certainly suffice to solve this
13. Be **absolutely completely positively** Thread Safe in **all** your operations (and look up the definition in doubt)
14. Only store the very first valid password guess: if another consumer finds another valid hash, discard it
15. Make an efficient program, at least make sure your implementation is faster than a single-threaded implementation
16. Read up yourself on how to properly do consumer-producer design, and handle clean shutdowns, if needed. You are all University students, and do not need handholding for every single detail. "It was not covered in the lecture" is **not** a valid excuse. 

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
