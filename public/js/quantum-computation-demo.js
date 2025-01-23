// Get references to the DOM elements
const quantumCircuitVisualization = document.getElementById('quantumCircuitVisualization');

// Function to simulate a quantum algorithm (e.g., Grover's search algorithm)
function simulateQuantumAlgorithm(algorithm) {
    // Clear previous visualization
    quantumCircuitVisualization.innerHTML = '';

    // Create a simple visualization for the selected quantum algorithm
    const algorithmVisualization = document.createElement('div');
    algorithmVisualization.className = 'quantum-algorithm-visualization';
    algorithmVisualization.innerHTML = `<p>Simulating ${algorithm}...</p>`;

    // Append the visualization to the quantum circuit display
    quantumCircuitVisualization.appendChild(algorithmVisualization);

    // Simulate the quantum computation process
    setTimeout(() => {
        algorithmVisualization.innerHTML += `<p>${algorithm} completed. Results are being processed...</p>`;
    }, 2000);
}

// Function to handle user interaction and select a quantum algorithm
function handleAlgorithmSelection() {
    const selectedAlgorithm = prompt("Enter the quantum algorithm to simulate (e.g., Grover's, Shor's):");
    if (selectedAlgorithm) {
        simulateQuantumAlgorithm(selectedAlgorithm);
    } else {
        quantumCircuitVisualization.innerHTML = '<p>Please enter a valid quantum algorithm to simulate.</p>';
    }
}

// Initialize the quantum computation demonstration on page load
document.addEventListener('DOMContentLoaded', () => {
    // Add a button to trigger the quantum algorithm simulation
    const simulateButton = document.createElement('button');
    simulateButton.textContent = 'Simulate Quantum Algorithm';
    simulateButton.addEventListener('click', handleAlgorithmSelection);

    // Append the button to the quantum circuit visualization area
    quantumCircuitVisualization.appendChild(simulateButton);
});

// Optional: Add styles for the visualizations
const style = document.createElement('style');
style.innerHTML = `
    .quantum-algorithm-visualization {
        margin-top: 20px;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: rgba(255, 255, 255, 0.1);
        color: #fff;
    }
    button {
        margin-top: 10px;
        padding: 10px 20px;
        font-size: 1em;
        color: #fff;
        background-color: #4b0082;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }
    button:hover {
        background-color: #1e90ff;
    }
`;
document.head.appendChild(style);
