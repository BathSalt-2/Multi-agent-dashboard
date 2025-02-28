// Import Three.js library
import * as THREE from 'three';

// Initialize scene, camera, and renderer
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('particleCanvas'), alpha: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

// Create a particle system
const particleCount = 1000;
const particles = new THREE.BufferGeometry();
const particlePositions = new Float32Array(particleCount * 3);

for (let i = 0; i < particleCount; i++) {
    particlePositions[i * 3] = (Math.random() - 0.5) * 10;
    particlePositions[i * 3 + 1] = (Math.random() - 0.5) * 10;
    particlePositions[i * 3 + 2] = (Math.random() - 0.5) * 10;
}

particles.setAttribute('position', new THREE.BufferAttribute(particlePositions, 3));

const particleMaterial = new THREE.PointsMaterial({
    color: 0x888888,
    size: 0.1,
    transparent: true,
    opacity: 0.7
});

const particleSystem = new THREE.Points(particles, particleMaterial);
scene.add(particleSystem);

// Create temporal waveform animation
const waveformGeometry = new THREE.RingGeometry(1, 5, 32);
const waveformMaterial = new THREE.MeshBasicMaterial({ color: 0x1e90ff, side: THREE.DoubleSide });
const waveform = new THREE.Mesh(waveformGeometry, waveformMaterial);
waveform.rotation.x = Math.PI / 2;
scene.add(waveform);

// Position the camera
camera.position.z = 5;

// Animation loop
function animate() {
    requestAnimationFrame(animate);

    // Rotate the particle system
    particleSystem.rotation.y += 0.001;

    // Animate the waveform
    waveform.scale.x = 1 + Math.sin(Date.now() * 0.001) * 0.1;
    waveform.scale.y = 1 + Math.cos(Date.now() * 0.001) * 0.1;

    renderer.render(scene, camera);
}

// Handle window resize
window.addEventListener('resize', () => {
    const width = window.innerWidth;
    const height = window.innerHeight;
    renderer.setSize(width, height);
    camera.aspect = width / height;
    camera.updateProjectionMatrix();
});

// Start the animation
animate();
