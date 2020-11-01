const K = 2;

const history = [];
let workQueue = [];
let highlightId = -1;
const eleLabel = document.querySelector("#overlay #label");

let step = 0;

function init() {
  for (const node of nodes) {
    if (!node["hospital"]) continue;

    workQueue.push([node["id"], []]);
  }
  eleLabel.innerText = "Initialise: Push all hospitals into the work queue";
  updateQueueDisplay();
  updateNodeContent();
}

function doWork() {
  const work = workQueue.shift();

  step++;
  if (!work) {
    eleLabel.innerText = "Done";
    highlightNode();
    return;
  }

  const node = nodes.filter((node) => node.id === work[0])[0];
  eleLabel.innerHTML = `Working on Node ${node.id}, new path [${work[1]}]<br/>`;

  highlightNode(node.id);
  highlightId = node.id;

  if (node.path.length >= K) {
    eleLabel.innerHTML += `<div class="importance-low">Node ${node.id} already has ${K} paths, nop</div>`;
    return;
  }

  const newPath = [node.id, ...work[1]];

  for (const path of node.path) {
    if (path[path.length - 1] === newPath[newPath.length - 1]) {
      eleLabel.innerHTML += `<div class="importance-low">Duplicate path to ${
        path[path.length - 1]
      }, nop</div>`;
      return;
    }
  }

  console.log("before", node.path);
  node.path.push(newPath);
  console.log(node.path);
  eleLabel.innerHTML += `<div class="importance-high">Node ${node.id} has new shortest path: [${newPath}]</div>`;

  for (const neighborId of node.neighbors) {
    eleLabel.innerHTML += `<div class="importance-high new-work">Adding neighbor ${neighborId} to work queue</div>`;

    workQueue.push([neighborId, newPath, step]);
  }
}

function updateQueueDisplay() {
  document.querySelector("#overlay #workQueue").innerHTML =
    "[" +
    workQueue
      .map((work) => {
        if (work[2] != step) {
          return `(${work[0]}, [${work[1]}])`;
        } else {
          return `<span class="new-work">(${work[0]}, [${work[1]}])</span>`;
        }
      })
      .join(", ") +
    "]";
}

window.onload = function () {
  init();
};

function saveHistory() {
  history.push([
    eleLabel.innerHTML,
    JSON.parse(JSON.stringify(workQueue)),
    JSON.parse(JSON.stringify(nodes)),
    highlightId,
  ]);
}

function loadHistory() {
  if (history.length > 0) {
    const [oldLabelText, oldWorkQueue, oldNodes, _highlightId] = history.pop();
    eleLabel.innerHTML = oldLabelText;
    workQueue = oldWorkQueue;
    highlightId = _highlightId;

    for (const i in nodes) {
      delete oldNodes[i].x;
      delete oldNodes[i].y;
      Object.assign(nodes[i], oldNodes[i]);
    }

    return true;
  }

  return false;
}

document.addEventListener("keydown", (evt) => {
  if (evt.key === "ArrowRight") {
    saveHistory();
    doWork();

    updateQueueDisplay();
    updateNodeContent();
  } else if (evt.key === "ArrowLeft") {
    if (!loadHistory()) return;

    highlightNode(highlightId);
    updateQueueDisplay();
    updateNodeContent();
  }
});
