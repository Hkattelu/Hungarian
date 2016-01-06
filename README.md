# Hungarian
My implementation of the Hungarian Algorithm

The Hungarian algorithm is a an algorithm used to solve a special
type of linear programming problem known as an assignment problem.
An assignment problem involves assigning n workers to m jobs to
either minimize or maximize a cost. Typically these problems can be
solved using the simplex method on an LP (O(2^N)). However,
the Hungarian algorithm can be used to solve these problems considerably
faster ( O(N^3) ). I learned about the algorithm last semester in a deterministic
models class and have decided to try implementing it.

A more detialed description can be found here : 
http://www.math.harvard.edu/archive/20_spring_05/handouts/assignment_overheads.pdf

<h2> My Implementation </h2>

With my implementation of the algorithm, you can easily solve assignment
problems in O(N^4) time as opposed to O(2^N) time. Simply import the class
and use the static methods. The two main methods you will use are hungarian_algorithm
and hungarian_minCost, although I have also made many of the helper methods public
in case you want to use them for some reason. My implementation currently works
for matrices of size mxn where m <= n. If you have a matrix of size m > n, simply
take the transpose of the matrix before and after using the algorithm.
