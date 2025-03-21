# -*- coding: utf-8 -*-
"""Karger's Algorithm

Automatically generated by Colab.

Original file is located at
    https://colab.research.google.com/drive/1Cj5L03o_Yr2d7rEWz-qxD-Y_qXHpVfiU
"""

import random
import time

# Class to represent an unweighted edge in the graph
class Edge:
    def __init__(self, s, d):
        self.src = s  # Source vertex
        self.dest = d  # Destination vertex

# Class to represent a connected, undirected and unweighted graph
class Graph:
    def __init__(self, v, e):
        self.V = v  # Number of vertices
        self.E = e  # Number of edges
        self.edge = []  # List to store edges

# Class to represent a subset for union-find
class subset:
    def __init__(self, p, r):
        self.parent = p  # Parent of the subset
        self.rank = r  # Rank of the subset

# Karger's randomized algorithm for finding minimum cut
def kargerMinCut(graph):
    V = graph.V  # Get the number of vertices
    E = graph.E  # Get the number of edges
    edge = graph.edge  # Get the list of edges

    # Create subsets for union-find
    subsets = []
    for v in range(V):
        subsets.append(subset(v, 0))  # Initialize each vertex as its own subset

    vertices = V  # Start with V vertices

    # Keep contracting vertices until there are only 2 left
    while vertices > 2:
        # Pick a random edge
        i = int(random.random() * E)
        subset1 = find(subsets, edge[i].src)
        subset2 = find(subsets, edge[i].dest)

        # If they are in the same subset, skip to the next edge
        if subset1 != subset2:
            vertices -= 1
            Union(subsets, subset1, subset2)

    # Count the edges between the two components left
    cutedges = 0
    for i in range(E):
        subset1 = find(subsets, edge[i].src)
        subset2 = find(subsets, edge[i].dest)
        if subset1 != subset2:
            cutedges += 1

    return cutedges  # Return the count of edges in the minimum cut

# Function to find the set of an element i (with path compression)
def find(subsets, i):
    if subsets[i].parent != i:
        subsets[i].parent = find(subsets, subsets[i].parent)  # Path compression
    return subsets[i].parent

# Function to union two sets of x and y (with union by rank)
def Union(subsets, x, y):
    xroot = find(subsets, x)  # Find root of x
    yroot = find(subsets, y)  # Find root of y

    # Attach smaller rank tree under root of high rank tree
    if subsets[xroot].rank < subsets[yroot].rank:
        subsets[xroot].parent = yroot
    elif subsets[xroot].rank > subsets[yroot].rank:
        subsets[yroot].parent = xroot
    else:
        subsets[yroot].parent = xroot  # Make one as root and increment its rank
        subsets[xroot].rank += 1

def main():
    graph = [[0, 16, 13, 0, 0, 0],
             [0, 0, 10, 12, 0, 0],
             [0, 4, 0, 0, 14, 0],
             [0, 0, 9, 0, 0, 20],
             [0, 0, 0, 7, 0, 4],
             [0, 0, 0, 0, 0, 0]]

    V = len(graph)
    E = sum(sum(1 for weight in row if weight > 0) for row in graph) // 2
    g = Graph(V, E)

    for i in range(V):
        for j in range(V):
            if graph[i][j] > 0:
                g.edge.append(Edge(i, j))

    # Measure execution time
    start_time = time.time()  # Start time
    res = kargerMinCut(g)
    end_time = time.time()  # End time

    print("Cut found by Karger's randomized algorithm is", res)
    print("Execution time: {:.6f} seconds".format(end_time - start_time))  # Print execution time

if name == '__main__':
    main()  # Execute the main function