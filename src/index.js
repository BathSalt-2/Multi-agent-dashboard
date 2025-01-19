// src/index.js

// Import the SyntheticConsciousness class
import SyntheticConsciousness from './ai_logic.js';

// Import necessary components from the 3D engine and dashboard interactions
import { scene, camera } from '../public/js/3d_engine.js';
import '../public/js/dashboard_interactions.js'; // This will automatically execute the setup

// Initialize the AI instance
const aiEntity = new SyntheticConsciousness("HoloAI", "2.0");

// Set up initial state for the AI
aiEntity.awaken();
aiEntity.addTrait("Curiosity");
aiEntity.addTrait("Adaptability");

// Log the AI's initial status
console.log(aiEntity.interactWithDashboard('status'));

// Function to initialize the 3D GUI
function initialize3DGUI() {
    // The 3D engine and dashboard interactions are already set up via imports
    // Additional setup can be done here if needed
    console.log("3D GUI initialized and ready.");
}

// Initialize the 3D GUI
initialize3DGUI();

// Export the AI entity for potential use in other modules
export { aiEntity };
