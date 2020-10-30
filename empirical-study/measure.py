import os
import re
import random
import subprocess
import multiprocessing
import networkx as nx

WORK_DIR = "work"

def gen_graph(num_nodes, degree, num_hospitals):
    g = nx.random_regular_graph(degree, num_nodes)
    n = g.nodes()

    hospitals = random.sample(n, num_hospitals)
    for i in n:
        n[i]["hospital"] = i in hospitals
    
    return g, hospitals

def execute(num_nodes, degree, num_hospitals, num_paths, iterations):
    g, hospitals = gen_graph(num_nodes, degree, num_hospitals)

    pid = id(multiprocessing.current_process())
    
    edge_fname = os.path.join(WORK_DIR, f'edges{pid}.txt')
    hospital_fname = os.path.join(WORK_DIR, f'hospitals{pid}.txt')

    edge_file_content = []
    for edge in g.edges:
        edge_file_content.append(f'{edge[0]}\t{edge[1]}')
        edge_file_content.append(f'{edge[1]}\t{edge[0]}')

    hospital_file_content = []
    hospital_file_content.append(f'# {len(hospitals)}')
    for i in hospitals:
        hospital_file_content.append(f'{i}')

    with open(edge_fname, "w") as f:
        f.write("\n".join(edge_file_content))

    with open(hospital_fname, "w") as f:
        f.write("\n".join(hospital_file_content))

    cmd = ["java", "-jar", "2001.jar", edge_fname, hospital_fname, num_paths, f'-i{iterations}']
    proc = subprocess.run(map(str, cmd), capture_output=True, text=True)

    output = proc.stdout

    try:
        return {
            "memoryUsage": float(re.search(r'Memory Usage: ([\d\.]+)GB', output).group(1)),
            "execTime": float(re.search(r'Took ([\d\.]+)ns', output).group(1))
        }
    except:
        print(proc.stderr)
        raise Exception(" ".join(cmd))

def work(params):
    num_nodes, degree, h, k, i = params
    return h, k, execute(num_nodes, degree, num_hospitals=h, num_paths=k, iterations=i)
