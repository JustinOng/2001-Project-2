import re
import sys
import json

with open(sys.argv[1]) as f:
    correct = json.loads(f.read())

with open(sys.argv[2]) as f:
    for i, line in enumerate(f.readlines()):
        try:
            line = line.strip()
            node_id = re.search(r'Vertex id=(\d+) ', line).group(1)

            for path in re.findall(r'\[((?:\d+\-)+\d+)\][,\s]+', line):
                path = [int(i) for i in path.split("-")]

                if path[0] != int(node_id):
                    raise Exception(f'path[0] should equal node_id: {path[0]} != {node_id}')
                
                found_valid = False
                for correct_path in correct[node_id]:
                    if correct_path["path"] == path:
                        found_valid = True
                        break
                    elif correct_path["path"][-1] == path[-1]:
                        if len(correct_path["path"]) == len(path):
                            print(f'Vertex id={node_id}: alternative shortest path to hospital_id={path[-1]} used')
                            found_valid = True
                            break
                        else:
                            raise Exception(f'Correct path={correct_path["path"]} and reported path={path} to same destination has different length')
                
                if found_valid:
                    continue

                raise Exception(f'Could not validate path={path}')
        except Exception as e:
            print(f'Exception parsing line {i}: {str(e)}')
