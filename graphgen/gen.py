import sys
import random
import numpy
import copy
import json
import networkx as nx
import matplotlib.pyplot as plt

CHANCE_HOSPITAL = 0.1
ID_Y_OFFSET = 0.02
LABEL_Y_OFFSET = -0.02

# random.seed(0)
# numpy.random.seed(0)

if len(sys.argv) < 6:
    sys.exit(f'{sys.argv[0]} n degree <edges file> <hospitals file> <answer.json> [graph file]')

num_nodes = int(sys.argv[1])
degree = int(sys.argv[2])

edge_fname = sys.argv[3]
hospital_fname = sys.argv[4]
answer_fname = sys.argv[5]

num_paths = 5

print(f'Generating graph with {num_nodes=} {degree=}')

g = nx.random_regular_graph(degree, num_nodes)
n = g.nodes()

hospitals = []
for i in n:
    n[i]["hospital"] = random.uniform(0, 1) < CHANCE_HOSPITAL
    if n[i]["hospital"]:
        hospitals.append(i)

correct = {}
# compute model answer naively
paths = dict(nx.all_pairs_shortest_path(g))
hospital_labels = {}
for src in paths:
    hospital_paths = [(i, paths[src][i]) for i in hospitals]
    hospital_paths.sort(key=lambda x: len(x[1]))
    
    filtered_hospital_paths = []

    for path in hospital_paths:
        if len(filtered_hospital_paths) < num_paths:
            filtered_hospital_paths.append(path)
        elif len(filtered_hospital_paths[-1][1]) == len(path[1]):
            # if last path appended and current path has same length,
            # save it too (because the algos could report either and we want to check)
            filtered_hospital_paths.append(path)
        else:
            break

    # path includes element itself, so -1
    correct[src] = [{
        "distance": len(path) - 1,
        "path": path,
        "hospital_id": hospital_id
    } for hospital_id, path in filtered_hospital_paths]
    hospital_labels[src] = "\n".join([f'd={len(a["path"]) - 1} id={a["hospital_id"]}' for a in correct[src]])

with open(edge_fname, "w") as f:
    for edge in g.edges:
        f.write(f'{edge[0]}\t{edge[1]}\n')
        f.write(f'{edge[1]}\t{edge[0]}\n')

with open(hospital_fname, "w") as f:
    f.write(f'# {len(hospitals)}\n')
    for i in hospitals:
        f.write(f'{i}\n')

with open(answer_fname, "w") as f:
    f.write(json.dumps(correct))

with open("correct.txt", "w") as f:
    for i in range(num_nodes):
        paths = [f'[{"-".join(map(str, a["path"]))}]' for a in correct[i]]
        f.write(f'Vertex id={i} {", ".join(paths)}\n')

if len(sys.argv) > 6:
    plt.figure(figsize=(40,28))
    pos = nx.spring_layout(g, k=0.15,iterations=20)
    nx.draw_networkx_edges(g, pos=pos, alpha=0.2)
    nx.draw_networkx_nodes(g,
        pos=pos,
        node_size=[128 if n[i]["hospital"] else 32 for i in n],
        node_color=["#ff0000" if n[i]["hospital"] else "#0000ff" for i in n]
    )

    # offset id_pos by ID_Y_OFFSET
    id_pos = copy.deepcopy(pos)
    for k, p in id_pos.items():
        p[1] += ID_Y_OFFSET
        # id_pos[k] = p

    label_pos = copy.deepcopy(pos)
    for k, p in label_pos.items():
        p[1] += LABEL_Y_OFFSET
        # id_pos[k] = p

    nx.draw_networkx_labels(g,
        pos=id_pos,
        labels={
            n:n for n in pos
        }
    )

    nx.draw_networkx_labels(g,
        pos=label_pos,
        labels=hospital_labels,
        font_size=6
    )

    plt.savefig(sys.argv[6])
