import sys
import random

# infile numhospitals outfile

nodes = set()
with open(sys.argv[1]) as f:
    for line in f.readlines():
        line = line.strip()

        if line[0] == "#":
            continue

        nodes.update(map(int, line.split("\t")))

num_hospitals = int(sys.argv[2])

out = random.sample(nodes, num_hospitals)

with open(sys.argv[3], "w") as f:
    f.write(f'# {len(out)}\n')
    for i in out:
        f.write(f'{i}\n')
