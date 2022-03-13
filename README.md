Welcome!
===================


Here is a simple Android Kotlin application based on the TMDP api service


Problem
-------------
This app was intially created to domonstrate two basic screens. Firstly, a list of movies on the home screen. On click 
of any item on this list of items should lead the user to the detail screen where they can see the more info.

This initial project possed bugs and failed to comply with some good practices in the sphere of android development.

I had to take work on this app, with aim to
1. Make it functional. That is runnable on real device with both screens well implemented
2. Fix bugg, rectify code smells and enforce best practices to the maximum
3. Write unit tests and UI tests
4. Use any form of Dependency Injection

Results
-------------
Below states clearly what I was able to achieve

1. The app is now runnable. Both the List and Detail screen have been correctly implemented.
2. The app is set up with a Dependency Injection library, Hilt.
3. The initial RxJava library used for networks are eliminated and replaced with Kotlin Coroutines.
4. List and Detail screen info now contain a poster image.
5. The app was reconfigured to adhere to MVVM architecture paradigms. It uses ViewModel and Repository patterns for network calls
   routing.
6. Unit test cases are included for ViewModel and Repository classes.
7. Code smells and bugs have been minimized as much as possible.
8. The xml views now consume Data objects view databinding.
9. RecyclerView adapter was used and replaced the initial ListAdapter implementation (DiifUtils is used as well). 
10. UI screens for List and Details are ameliorated.
11. App now listens to network connectivity and prevents crashing when device is not connected.
12. Included missing test and AndroidTest folders for tests



Personal Feedback
-------------
I think this was a great exercise. The project was correctly setup enough to mislead a candidate and making them believe 
a working solution could as well mean the solution was correctly implemented. Just as an example, the RxJava networks calls 
were made in the `ListActivity.kt` contrary to best practices that networks calls and such code should not be put in View classes

Also the project had broken sides that compels a Candidate who makes them out to think mostly of a quick fix before coming to a
better way to implement this solution. So, in some cases the candidate might rush into a quick fix and get satisfied.

With all these, this exercise helps think about not just a solution but also the best way to implement our solutions according to
best practices and standards. While tinkering about our solutions in this manner, we realise that although some solutions could 
be in the tip of our fingers, going further to think of the best way to implement it is also another skill worth building on aswell.


One thing I think could be helpful is to allow the user submit their solution via github or even as a pull request. 
This way they get tester somehow on that and at the same time the company could easily visit the solution.

**I enjoyed this challenge**

# Browsr
