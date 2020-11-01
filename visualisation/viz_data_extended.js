const nodes = [
  { id: 1, hospital: false, path: [], neighbors: [2, 7, 3] },
  { id: 2, hospital: false, path: [], neighbors: [1, 7, 0] },
  { id: 3, hospital: false, path: [], neighbors: [1, 4, 5] },
  { id: 4, hospital: false, path: [], neighbors: [3, 6, 7] },
  { id: 7, hospital: true, path: [], neighbors: [1, 2, 4] },
  { id: 6, hospital: false, path: [], neighbors: [4, 0, 5] },
  { id: 0, hospital: true, path: [], neighbors: [2, 6, 5] },
  { id: 5, hospital: false, path: [], neighbors: [3, 6, 0] },
];
const links = [
  { source: 1, target: 2 },
  { source: 2, target: 1 },
  { source: 1, target: 7 },
  { source: 7, target: 1 },
  { source: 1, target: 3 },
  { source: 3, target: 1 },
  { source: 2, target: 7 },
  { source: 7, target: 2 },
  { source: 2, target: 0 },
  { source: 0, target: 2 },
  { source: 3, target: 4 },
  { source: 4, target: 3 },
  { source: 3, target: 5 },
  { source: 5, target: 3 },
  { source: 4, target: 6 },
  { source: 6, target: 4 },
  { source: 4, target: 7 },
  { source: 7, target: 4 },
  { source: 6, target: 0 },
  { source: 0, target: 6 },
  { source: 6, target: 5 },
  { source: 5, target: 6 },
  { source: 0, target: 5 },
  { source: 5, target: 0 },
];
