// Import necessary Three.js components
import * as THREE from 'three';

// Initialize Three.js scene
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('3d-canvas') });
renderer.setSize(window.innerWidth, window.innerHeight);

// Add a basic cube to the scene
const geometry = new THREE.BoxGeometry();
const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
const cube = new THREE.Mesh(geometry, material);
scene.add(cube);

camera.position.z = 5;

// Animation loop
function animate() {
    requestAnimationFrame(animate);
    cube.rotation.x += 0.01;
    cube.rotation.y += 0.01;
    renderer.render(scene, camera);
}
animate();

// Handle chat interface interactions
const chatInput = document.getElementById('chat-input');
const chatMessages = document.getElementById('chat-messages');
const sendButton = document.getElementById('send-button');

sendButton.addEventListener('click', () => {
    const message = chatInput.value.trim();
    if (message) {
        const messageElement = document.createElement('div');
        messageElement.textContent = message;
        chatMessages.appendChild(messageElement);
        chatInput.value = '';
    }
});

// Handle customization options
const customizationForm = document.getElementById('customization-form');
const themeSelect = document.getElementById('theme-select');

customizationForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const selectedTheme = themeSelect.value;
    document.body.className = selectedTheme; // Apply theme to body
});

// Adjust canvas size on window resize
window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
});
