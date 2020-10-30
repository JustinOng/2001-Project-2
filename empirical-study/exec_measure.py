import os
import sys
import json
import time
import shutil
import collections
from multiprocessing import Pool
from measure import work, WORK_DIR

num_nodes = 10000
degree = 3

hospital_max = 500
hospital_step = 50

path_max = 20
path_step = 2

iterations = 128

if len(sys.argv) < 2:
    sys.exit(f'Usage: {sys.argv[0]} outfile.json')

try:
    shutil.rmtree(WORK_DIR)
except:
    pass

try:
    os.mkdir(WORK_DIR)
except:
    pass

time.sleep(0.5)

# https://stackoverflow.com/a/42383397
if __name__ == "__main__":
    data = collections.defaultdict(lambda: collections.defaultdict(lambda: []))

    with Pool(4) as p:
        # output = p.map(work, [(num_nodes, degree, h, k) for h in range(10, 100, 10) for k in range(1, min(h, 20) + 1)])
        output = p.map(work, [(num_nodes, degree, h, k, iterations) for h in range(hospital_step, hospital_max + hospital_step, hospital_step) for k in range(path_step, path_max + path_step, path_step)])
                
        for h, k, out in output:
            data[h][k].append(out)
        
        with open(sys.argv[1], "w") as f:
            f.write(json.dumps(
                {
                    "num_nodes": num_nodes,
                    "degree": degree,
                    "hospital_max": hospital_max,
                    "hospital_step": hospital_step,
                    "path_max": path_max,
                    "path_step": path_step,
                    "iterations": iterations,
                    "data": data
                }))
