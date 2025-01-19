// Import necessary Three.js components
import * as THREE from 'three';

// Initialize Three.js scene
const scene = new THREE.Scene();

// Set up camera with perspective view
const camera = new THREE.PerspectiveCamera(
    75, // Field of view
    window.innerWidth / window.innerHeight, // Aspect ratio
    0.1, // Near clipping plane
    1000 // Far clipping plane
);

// Create WebGL renderer and attach it to the canvas
const renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('3d-canvas') });
renderer.setSize(window.innerWidth, window.innerHeight);

// Add a basic cube to the scene as a placeholder for the AI representation
const geometry = new THREE.BoxGeometry();
const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
const cube = new THREE.Mesh(geometry, material);
scene.add(cube);

// Position the camera
camera.position.z = 5;

// Define the animation loop
function animate() {
    requestAnimationFrame(animate);
    cube.rotation.x += 0.01; // Rotate the cube for demonstration
    cube.rotation.y += 0.01;
    renderer.render(scene, camera);
}

// Start the animation loop
animate();

// Handle window resize to adjust camera and renderer
window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
});

// Export the scene and camera for potential use in other modules
export { scene, camera };
