This app developed using :
* Architectural Patterns - MVVM,MVP,Clean Architecture
* REST Client - Retrofit2 + OkHttp3
* LocalDb(SQL) - Room
* Dependency Injection - self implemented
* Other libraries - RxJava2, guava, gson

Assignment comments -

* What   architecture   have   you   used   and   why?
In General I used MVP,MVVP,Clean Architecture. more explanation about the MVVM & MVP in the 3rd section.
For the whole application I decided to implement the Clean Architecture because I think it's the best way to develop maintainable application with clean code that can be understood for both human and machines dou to it's very high degree of "separation of concerns", Also using this architecture makes it very easy to perform unit test on every single layer of the application and get a very good coverage for the code.

* What   was   the   most   difficult   part   of   the   assignment?
** there was a few challenging parts in this assignment, one of them was to implement the recyclerview with the "load more" footer. most of the times I've used libraries to this footer and it was always horrible experience for me so this time I decided to implement my own recyclerview which supports this footer the way I think it should.
** another challenging part was to find how to make Glide invalidate the cached images after 1 day, which is something I never did before.

* Describe   the   main   components,   layers,   architecture
For the "simpler" screens I used MVP because I think it's more straight forward when the ui is somewhat less complicated.
In the more complicated screen which was the movies list screen I used MVVM because the View is much more simple with MVVM and secondly I wanted to demonstrate both architecture as the purpose of the test is to show my knowledge.

* if   there   is   any   known   bugs?
I didn't managed to write enough unit tests( I wrote only a few for demonstration) so there's probably a few bugs that I didn't found, but no issues that I know about.
* What   would   you   change   in   the   project?
I would definitely write much more unit tests but I could't do it given the time I had to work on this project(which was around the 20 hours).
I did write a few tests to demonstrate my knowledge in testing but the coverage that I have in this project is nothing similar to the coverage I usually aspire on real project(about 90% coverage).
One more thing that I would probably change is the UI/UX. I used the most basic UI components and design and I know that real apps must be more attractive but I think it does demonstrate my ability to create rich UI screens from appropriate designs.
Also, I assume you're aware that I ain't a professional designer and I assume you're not testing me on how beauty the UI is but rather the usage of the correct components to build the ui.

