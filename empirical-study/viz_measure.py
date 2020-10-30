import sys
import json
import plotly.express as px

with open(sys.argv[1]) as f:
    data = json.loads(f.read())

data_h = []
data_k = []
data_visits = []

for h in data["data"]:
    for k in data["data"][h]:
        data_h.append(int(h))
        data_k.append(int(k))
        exec_time = [stat["execTime"] for stat in data["data"][h][k]]
        data_visits.append(sum(exec_time) // len(data["data"][h][k]) / 1000000)

fig = px.scatter_3d(x=data_h, y=data_k, z=data_visits, labels={"x": "Hospitals", "y": "Paths", "z": "Execution Time (ms)"})
fig.update_layout(title_text=f'{data["num_nodes"]} nodes, {data["num_nodes"] * data["degree"]} edges', title_x=0.5)
fig.write_html(sys.argv[2])
