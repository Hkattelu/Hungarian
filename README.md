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

# TODO
- Complete steps 3-5 of the algorithm
- Make it more user friendly
- According to wikipedia, it can be made to run in O(n^3). Mines currently runs in O(n^4).
