import sys
import random
import collections
import networkx as nx

random.seed(4)

def gen_graph(num_nodes, degree, num_hospitals):
    g = nx.random_regular_graph(degree, num_nodes)
    n = g.nodes()

    hospitals = random.sample(n, num_hospitals)
    for i in n:
        n[i]["hospital"] = i in hospitals
    
    return g, hospitals

g, hospitals = gen_graph(num_nodes=8, degree=3, num_hospitals=2)

neighbors = collections.defaultdict(lambda: [])
for e in g.edges:
    src, dst = e
    neighbors[src].append(dst)
    neighbors[dst].append(src)

print(f'const nodes = [')
for n in g.nodes():
    print(f'  {{ id: {n}, hospital: {"true" if n in hospitals else "false"}, path: false, neighbors: {neighbors[n]} }},')
print(f'];')

print(f'const links = [')
for e in g.edges:
    src, dst = e
    print(f'  {{ source: {src}, target: {dst} }},')
    print(f'  {{ source: {dst}, target: {src} }},')
print(f'];')
