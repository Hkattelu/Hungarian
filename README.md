# Hungarian
My implementation of the Hungarian Algorithm

The Hungarian algorithm is a an algorithm used to solve a special
type of linear programming problem known as an assignment problem.
An assignment problem involves assigning n workers to m jobs to
either minimize or maximize a cost. Typically these problems can be
solved using the simplex method on an LP ( O(2^N) time? ). However,
the Hungarian algorithm can be used to solve these problems considerably
faster ( O(N^2)? ). I learned about the algorithm last semester in a deterministic
models class and have decided to try implementing it.

# TODO
- Complete steps 3 and 4 of the algorithm
- Speed up the algorithm? Currently runs in O(N^2) but with a constant
factor larger than it should be.
