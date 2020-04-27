# Puzzle-Peg-Solver-Triangle-
Recursive algorithm to solve a triangle version of the peg solitaire puzzle. Currently it is not generalized meaning that it can only solve a puzzle of size 15..


The possible moves in the triangle are stored in a 2D array (int[][]triplets). Each row contains a set of three numbers. Each set represents a valid move in the triangle. Since the triangle is stored in a 1D array not every possible move is next represented; that is why a 2D array is needed.
