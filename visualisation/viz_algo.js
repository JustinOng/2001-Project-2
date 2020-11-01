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

  if (node.path && node.path.length > 0) {
    eleLabel.innerHTML += `<div class="importance-low">Node ${node.id} already has shortest path, nop</div>`;
    return;
  }

  node.path = [...work[1]];
  node.path.push(node.id);
  eleLabel.innerHTML += `<div class="importance-high">Node ${
    node.id
  } has new shortest path: [${node.path.reverse()}]</div>`;

  for (const neighborId of node.neighbors) {
    const neighbor = nodes.filter((node) => node.id === neighborId)[0];
    if (neighbor.path && neighbor.path.length > 0) {
      eleLabel.innerHTML += `<div class="importance-low">Not adding neighbor ${neighborId}: already has path</div>`;
      continue;
    }

    eleLabel.innerHTML += `<div class="importance-high new-work">Adding neighbor ${neighborId} to work queue</div>`;

    workQueue.push([neighborId, [...node.path], step]);
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
