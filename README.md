Boids Flocking Simulation
=========================

By Ellis Simmons, May 2025  
Made with Processing

What is this?
-------------
It's a simple 2D boids flocking simulation — little triangle birds that fly around and try to stay together, not bump into each other, and generally act like a flock.

How it works:
-------------
Each boid looks around within a certain distance and adjusts its movement based on three things:
- **Separation:** Avoid getting too close to neighbors.
- **Alignment:** Match the average direction of nearby flockmates.
- **Cohesion:** Move towards the average position of nearby flockmates.

When a boid goes off one edge of the screen, it just wraps around to the other side.

How to run:
-----------
- Load this in Processing.
- Hit Run.
- Watch the triangles fly around and flock together.

You can tweak:
--------------
- Number of boids in `setup()`
- `view` distance for how far they look around
- `maxSpeed` and `maxForce` to control movement smoothness and speed

Inspired by:
------------
- Craig Reynolds’ original Boids paper (1986)
- Daniel Shiffman / The Coding Train’s tutorials
- Wikipedia articles on flocking behavior

Notes:
------
It's not perfect bird behavior, but it looks cool and helps understand basic steering behaviors with vectors.

---
As per usual, if you're going to steal or use it at least credit me please. Thank you for reading and have a nice day.
